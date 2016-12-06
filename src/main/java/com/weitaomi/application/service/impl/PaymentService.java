package com.weitaomi.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.weitaomi.application.model.bean.*;
import com.weitaomi.application.model.enums.PayType;
import com.weitaomi.application.model.mapper.*;
import com.weitaomi.application.service.interf.*;
import com.weitaomi.systemconfig.exception.BusinessException;
import com.weitaomi.systemconfig.exception.InfoException;
import com.weitaomi.systemconfig.util.*;
import com.weitaomi.systemconfig.wechat.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by supumall on 2016/7/22.
 */
@Service
public class PaymentService implements IPaymentService,Runnable {
    private Logger logger = LoggerFactory.getLogger(PaymentService.class);
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private PaymentHistoryMapper paymentHistoryMapper;
    @Autowired
    private MemberScoreMapper memberScoreMapper;
    @Autowired
    private MemberScoreFlowMapper memberScoreFlowMapper;
    @Autowired
    private IMemberScoreService memberScoreService;
    @Autowired
    private IMemberTaskHistoryService taskHistoryService;
    @Autowired
    private PaymentApproveMapper approveMapper;
    @Autowired
    private ThirdLoginMapper thirdLoginMapper;
    @Autowired
    private MemberPayAccountsMapper memberPayAccountsMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    private Lock lock = new ReentrantLock();
    private String certFilePath0;
    private String certFilePath1;

    /**
     * 初始化参数
     */
    @PostConstruct
    public void init() {
        String classPath = this.getClass().getResource("/").getPath();
        certFilePath0 = classPath + "properties/wechat/apiclient_cert.p12";
        certFilePath1 = classPath + "properties/wechat/apiclient_certwx.p12";
    }

    @Override
    public Boolean patchWechatCustomers(List<Map<String, Object>> parameters, String ip, Long memberId) {
        Integer count = 0;
        if (!parameters.isEmpty()) {
            for (Map<String, Object> param : parameters) {
                param.put("ip", ip);
                param.put("memberId", memberId);
                amqpTemplate.convertAndSend("dealWithdrawsChange." + param.get("approveId"), JSON.toJSONString(param));
//                dealWithdrawsChange(param);
                count++;
            }
        }
        return parameters.size() == count;
    }

    @Override
    @Transactional
    public boolean dealWithdrawsChange(Map<String, Object> param) {
        if (!param.isEmpty()) {
            String ip = param.get("ip").toString();
            Long approveId = Long.valueOf(param.get("approveId").toString());
            Integer isApprove = Integer.valueOf(param.get("isApprove").toString());
            String remark = (String) param.get("remark");
            List<PaymentHistory> paymentHistoryList = new ArrayList<PaymentHistory>();
            Integer number = 0;
            PaymentApprove approve = approveMapper.selectByPrimaryKey(approveId);
            if (remark != null) {
                approve.setRemark(remark);
            }
            if (isApprove != null) {
                if (isApprove == 1) {
                    Long amount = approve.getAmount().multiply(BigDecimal.valueOf(100)).longValue();
                    MemberScoreFlow memberScoreFlow = memberScoreFlowMapper.getMemberScoreFlow(approve.getMemberId(), -approve.getAmount().add(approve.getExtraAmount()).multiply(BigDecimal.valueOf(100)).doubleValue(), approve.getCreateTime(), 2L, 0);
                    if (memberScoreFlow == null) {
                        throw new InfoException("获取待提现记录失败");
                    }
                    MemberScore memberScore = memberScoreMapper.selectByPrimaryKey(memberScoreFlow.getMemberScoreId());
                    if (memberScore == null) {
                        throw new InfoException("获取用户积分总额失败");
                    }
                    String hash = "member:desposit:forOne:" + approve.getId();
                    Integer flag = cacheService.getCacheByKey(hash.toString(), Integer.class);
                    if (flag != null && flag == 1) {
                        throw new InfoException("该条提现已经审核过");
                    }
                    if (amount>0) {
                        String randomkey = UUIDGenerator.generate();
                        String code = this.getTradeNo();
                        Map<String, String> params = new HashMap<>();
                        ThirdLogin thirdLogin = thirdLoginMapper.getThirdLoginByOpenId(approve.getAccountNumber());
                        String key = "";
                        if (thirdLogin.getSourceType() == 0) {
                            params.put("mch_appid", WechatConfig.APP_ID);
                            params.put("mchid", WechatConfig.MCHID);
                            key = WechatConfig.API_KEY;
                        }
                        if (thirdLogin.getSourceType() == 1) {
                            params.put("mch_appid", WechatConfig.MCH_APPID);
                            params.put("mchid", WechatConfig.MCHID_OFFICIAL);
                            key = WechatConfig.OFFICIAL_API_KEY;
                        }
                        params.put("nonce_str", randomkey);
                        params.put("partner_trade_no", code);
                        params.put("openid", approve.getAccountNumber());
                        params.put("check_name", "NO_CHECK");
                        params.put("amount", amount.toString());
                        params.put("desc", "付款到" + thirdLogin.getNickname() + "的账户");
                        params.put("spbill_create_ip", ip);
                        String pre_sign = StringUtil.formatParaMap(params);
                        pre_sign = pre_sign + "&key=" + key;
                        String sign = DigestUtils.md5Hex(pre_sign).toUpperCase();

                        WechatBatchPayParams wechatBatchPayParams = new WechatBatchPayParams();
                        wechatBatchPayParams.setAmount(amount.toString());
                        wechatBatchPayParams.setCheck_name("NO_CHECK");
                        wechatBatchPayParams.setDesc("付款到" + thirdLogin.getNickname() + "的账户");

                        if (thirdLogin.getSourceType() == 0) {
                            wechatBatchPayParams.setMch_appid(WechatConfig.APP_ID);
                            wechatBatchPayParams.setMchid(WechatConfig.MCHID);
                        }
                        if (thirdLogin.getSourceType() == 1) {
                            wechatBatchPayParams.setMch_appid(WechatConfig.MCH_APPID);
                            wechatBatchPayParams.setMchid(WechatConfig.MCHID_OFFICIAL);
                        }

                        wechatBatchPayParams.setNonce_str(randomkey);
                        wechatBatchPayParams.setOpenid(approve.getAccountNumber());
                        wechatBatchPayParams.setPartner_trade_no(code);
                        wechatBatchPayParams.setSign(sign);
                        wechatBatchPayParams.setSpbill_create_ip(ip);
                        XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
                        xStream.autodetectAnnotations(true);
                        String xml = xStream.toXML(wechatBatchPayParams);
                        try {
                            String path = "";
                            if (thirdLogin.getSourceType() == 0) {
                                path = certFilePath0;
                            }
                            if (thirdLogin.getSourceType() == 1) {
                                path = certFilePath1;
                            }
                            String result = ClientCustomSSL.connectKeyStore(WechatConfig.BATCH_PAY_URL, xml, path, thirdLogin.getSourceType());
                            logger.info("审核结果为{}", result);
                            WechatBatchPayResult wechat = StreamUtils.toBean(result, WechatBatchPayResult.class);
                            if (wechat != null) {
                                if (wechat.getResult_code().equals(WechatConfig.SUCCESS) && wechat.getReturn_code().equals(WechatConfig.SUCCESS)) {
                                    String hashCode = "member:desposit:forOne:" + approve.getId();
                                    Long time = DateUtils.getTodayEndSeconds() - DateUtils.getUnixTimestamp();
                                    cacheService.setCacheByKey(hashCode, 1, time.intValue());
                                    approve.setIsPaid(1);
                                    memberScoreFlow.setIsFinished(1);
                                    memberScore.setInValidScore(memberScore.getInValidScore().add(memberScoreFlow.getFlowScore()));
                                    memberScoreMapper.updateByPrimaryKeySelective(memberScore);
                                    memberScoreFlowMapper.updateByPrimaryKeySelective(memberScoreFlow);
                                    approve.setCreateTime(DateUtils.getUnixTimestamp());
                                    approveMapper.updateByPrimaryKeySelective(approve);
                                    PaymentHistory paymentHistory = new PaymentHistory();
                                    paymentHistory.setIsPaySuccess(1);
                                    paymentHistory.setPayType(1);
                                    paymentHistory.setParams(JSON.toJSONString(wechatBatchPayParams));
                                    paymentHistory.setMemberId(approve.getMemberId());
                                    paymentHistory.setPlatform(PayType.WECHAT_APP.getDesc());
                                    paymentHistory.setPayCode(code);
                                    paymentHistory.setCreateTime(DateUtils.getUnixTimestamp());
                                    paymentHistory.setBatchPayNo(wechat.getPayment_no());
                                    paymentHistory.setMemberId(approve.getMemberId());
                                    paymentHistoryList.add(paymentHistory);
                                    number++;
                                } else {
                                    String reason = WechatResutCode.getValue(wechat.getErr_code()).getValue();
                                    Long memberId = 215L;
                                    if (memberId != null) {
                                        Member member = memberMapper.selectByPrimaryKey(memberId);
                                        memberId=member.getId();
                                    }
                                    JpushUtils.buildRequest(MessageFormat.format(new String(PropertiesUtil.getValue("withdraws.fail.msg")), approveId, reason),memberId);
//                                    SendMCUtils.sendMessage(mobile, MessageFormat.format(new String(PropertiesUtil.getValue("withdraws.fail.msg")), approveId, reason));
                                    return false;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                        if (number == 1) {
                            int num = paymentHistoryMapper.batchInsertPayHistory(paymentHistoryList);
                            if (num == number) {
                                try {
//                                JpushUtils.buildRequest("提现申请审核通过，请到微信查看零钱明细", approve.getMemberId());
                                } catch (Exception e) {
                                    logger.info("发送审核结果通知失败");
                                }
                                return true;
                            } else throw new InfoException("提现审核失败");
                        } else {
                            Long memberId = 215L;
                            if (memberId != null) {
                                Member member = memberMapper.selectByPrimaryKey(memberId);
                                memberId=member.getId();
                            }
                            JpushUtils.buildRequest(MessageFormat.format(new String(PropertiesUtil.getValue("withdraws.fail.msg")), approveId, "获取用户记录失败"),memberId);
                            return false;
                        }
                    }else if (amount==0){
                        String hashCode = "member:desposit:forOne:" + approve.getId();
                        Long time = DateUtils.getTodayEndSeconds() - DateUtils.getUnixTimestamp();
                        cacheService.setCacheByKey(hashCode, 1, time.intValue());
                        approve.setIsPaid(1);
                        memberScoreFlow.setIsFinished(1);
                        memberScore.setInValidScore(memberScore.getInValidScore().add(memberScoreFlow.getFlowScore()));
                        memberScoreMapper.updateByPrimaryKeySelective(memberScore);
                        memberScoreFlowMapper.updateByPrimaryKeySelective(memberScoreFlow);
                        approve.setCreateTime(DateUtils.getUnixTimestamp());
                        approveMapper.updateByPrimaryKeySelective(approve);
                    }
                } else if (isApprove == 0) {
                    Double returnBackScore = approve.getAmount().add(approve.getExtraAmount()).multiply(BigDecimal.valueOf(100)).doubleValue();
                    MemberScoreFlow memberScoreFlow = memberScoreFlowMapper.getMemberScoreFlow(approve.getMemberId(), -returnBackScore, approve.getCreateTime(), 2L, 0);
                    memberScoreFlow.setTypeId(8L);
                    memberScoreFlow.setIsFinished(-1);
                    memberScoreFlowMapper.updateByPrimaryKeySelective(memberScoreFlow);
                    memberScoreService.addMemberScore(approve.getMemberId(), 8L, 1, returnBackScore, UUIDGenerator.generate());
                    taskHistoryService.addMemberTaskToHistory(approve.getMemberId(), 13L, returnBackScore, 1, null, null, null);
                    approve.setIsPaid(1);
                    approveMapper.updateByPrimaryKeySelective(approve);
                    try {
                        JpushUtils.buildRequest(remark, approve.getMemberId());
                    } catch (Exception e) {
                        logger.info("发送审核结果通知失败");
                    }
                    return true;
                } else {
                    throw new InfoException("审批状态错误");
                }
            }
        }
        return false;
    }

    @Override
    public Page<PaymentApprove> getPaymentApproveList(Integer pageIndex, Integer pageSize) {
        List<PaymentApprove> paymentApproveList = approveMapper.getPaymentApproveList(new RowBounds(pageIndex, pageSize));
        PageInfo<PaymentApprove> pageInfo = new PageInfo<PaymentApprove>(paymentApproveList);
        return Page.trans(pageInfo);
    }

    private String getTradeNo() {
        try {
            lock.lock();
            String key = "wtm:orderCode:max";
            String payCode = cacheService.getCacheByKey(key, String.class);
            if (StringUtils.isEmpty(payCode)) {
                payCode = paymentHistoryMapper.getMaxPayCode();
                if (StringUtils.isEmpty(payCode)) {
                    payCode = "100000000000";
                    Long orderNumer = Long.valueOf(payCode) + 1;
                    cacheService.setCacheByKey(key, orderNumer.toString(), null);
                } else {
                    Long orderNumer = Long.valueOf(payCode) + 1;
                    cacheService.setCacheByKey(key, orderNumer.toString(), null);
                }
            } else {
                Long orderNumer = Long.valueOf(payCode) + 1;
                cacheService.setCacheByKey(key, orderNumer.toString(), null);
            }
            return payCode;
        } catch (Exception e) {
            throw new BusinessException("业务错误");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {

    }
}
