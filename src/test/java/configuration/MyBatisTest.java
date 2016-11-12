package configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.deploy.net.URLEncoder;
import com.thoughtworks.xstream.XStream;
import com.weitaomi.application.model.bean.Member;
import com.weitaomi.application.model.bean.MemberScoreFlow;
import com.weitaomi.application.model.bean.ThirdLogin;
import com.weitaomi.application.model.mapper.*;
import com.weitaomi.application.service.interf.IMemberScoreService;
import com.weitaomi.application.service.interf.IMemberTaskHistoryService;
import com.weitaomi.application.service.interf.IPaymentService;
import com.weitaomi.systemconfig.util.*;
import com.weitaomi.systemconfig.wechat.WechatConfig;
import com.weitaomi.systemconfig.wechat.WechatPayParams;
import common.BaseContextCase;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by supumall on 2016/7/6.
 */
public class MyBatisTest extends BaseContextCase {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    ThirdLoginMapper thirdLoginMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    MemberTaskHistoryMapper memberTaskHistoryMapper;
    @Autowired
    private IPaymentService paymentService;
    @Autowired
    private IMemberTaskHistoryService memberTaskHistoryService;
    @Autowired
    private MemberScoreFlowMapper memberScoreFlowMapper;
    @Autowired
    private IMemberScoreService memberScoreService;
    //    @Autowired
//    IMemberTaskHistoryService memberTaskHistoryService;
    @Test
    public void testAddMember() {
        Member member = new Member();
        member.setBirth(100000000L);
        member.setCreateTime(100000000L);
        member.setEmail("weitaomi@test.com");
        member.setImageUrl("/admin/image/10039689.jpg");
        member.setInvitedCode("100086");
        member.setMemberName("guest");
        member.setNickName("游客凭证");
        member.setPassword(new Sha256Hash("guest_123456", "qwerty").toString());
        member.setSalt("qwerty");
        member.setSex(0);
        member.setTelephone("13131313131");
        System.out.println(memberMapper.insertSelective(member));
        System.out.println(member.getId());
    }

    @Test
    public void testSelectMember() {
        Member member = memberMapper.selectByPrimaryKey(1L);
        System.out.println(JSON.toJSONString(member));
    }

    @Test
    public void testSelectSelf() {
        Member member = memberMapper.getMemberByTelephone("13105187050",0);
        System.out.println(JSON.toJSONString(member));
    }

    @Test
    public void testThridLoginMapper() {
        System.out.println("========================================>");
        System.out.println(UUIDGenerator.generate());
        System.out.println(JSON.toJSONString(thirdLoginMapper.getThirdLoginInfo("123456",0)));
        System.out.println("========================================>");
//        DigestUtils
    }

    @Test
    public void testPushAddAccounts() {
        String url = "http://www.yuyinggzs.com/index.php/home/js/index";
        Map<String,Object>  map=new HashMap<>();
        map.put("unionId","oaPViwV22OBKL-FcvcNBiZykLm_A");
        List<Long> idList=new ArrayList<>();
        idList.add(1L);
        idList.add(2L);
        map.put("officialAccountIdList",idList);
        map.put("flag","1");
        map.put("accountAdsId","1");
        try {
            String message = HttpRequestUtils.postStringEntity(url, JSON.toJSONString(map));
            System.out.println("=====================>"+message);
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        }
    }
    @Test
    public void testGetMemberInfo(){
//        List<MemberScoreFlow> flow=memberScoreFlowMapper.getMemberScoreFlowListByMemberId(7L);
        MemberScoreFlow memberScoreFlow= memberScoreFlowMapper.getMemberScoreFlow(7L,-100.00D,1473392373L,2L,0);
        System.out.println(JSON.toJSONString(memberScoreFlow));
    }
    @Test
    public void testJson(){
        List<Long> longs=new ArrayList<>();
        longs.add(1L);
        longs.add(2L);
        longs.add(3L);
        longs.add(4L);
        longs.add(5L);
        System.out.println(JSON.toJSONString(longs));

    }
    @Test
    public void testApprove(){

//        PaymentApprove approve=new PaymentApprove();
//        approve.setAccountNumber("oKfbJvp1_Bb-Xy1aqw6o4L5H55Y0");
//        approve.setAccountName("宁凡荣");
//        approve.setAmount(BigDecimal.valueOf(8.00));
//        approve.setCreateTime(1473393865L);
//        approve.setIsPaid(1);
//        approve.setMemberId(3L);
//        List<PaymentApprove> approves=new ArrayList<>();
//        approves.add(approve);
//        paymentService.patchWechatCustomers(1L,1,"","192.168.0.77");


    }

    @Test
    public void  test2(){
        Map<String,String> params=new HashMap<>();
        params.put("appid", WechatConfig.APP_ID);
        params.put("mch_id",WechatConfig.MCH_APPID);
        params.put("nonce_str", UUIDGenerator.generate());
        params.put("out_trade_no", UUIDGenerator.generate());
        params.put("body","微淘米会员充值");
        params.put("notify_url","123456");
        params.put("trade_type","APP");
        params.put("total_fee","10");
        params.put("spbill_create_ip","10");
//        params=this.paraFilter(params);
        String pre_sign= StringUtil.formatParaMap(params);
        pre_sign=pre_sign+"key="+WechatConfig.API_KEY;
        String sign= DigestUtils.md5Hex(pre_sign).toUpperCase();
        params.put("sign",sign);
        String value=JSON.toJSONString(params);
        System.out.println(value);
        WechatPayParams wechatPayParams=JSONObject.parseObject(value,WechatPayParams.class);
        XStream xStream=new XStream();
        xStream.autodetectAnnotations(true);
        String requestParams=xStream.toXML(wechatPayParams);

        System.out.println(requestParams);
    }
    @Test
    public void testQuartz(){
        memberScoreService.updateExtraRewardTimer();
    }
    private XStream getXStream(){
        XStream xStream = new XStream();
//        xStream.autodetectAnnotations(true);
        //设置xml节点属性信息
        xStream.useAttributeFor(WechatPayParams.class,"appid");
        xStream.useAttributeFor(WechatPayParams.class,"mch_id");
        xStream.useAttributeFor(WechatPayParams.class,"nonce_str");
        xStream.useAttributeFor(WechatPayParams.class,"body");
        xStream.useAttributeFor(WechatPayParams.class,"notify_url");
        xStream.useAttributeFor(WechatPayParams.class,"trade_type");
        xStream.useAttributeFor(WechatPayParams.class,"total_fee");
        xStream.useAttributeFor(WechatPayParams.class,"spbill_create_ip");
        xStream.useAttributeFor(WechatPayParams.class,"sign");
        return xStream;
    }
    @Test
    public  void testRequestGet(){
        NameValuePair[] nameValue = new NameValuePair[3];
        nameValue[0]=new BasicNameValuePair("access_token","wG1UmatSeWR4xBNLYxFuMuUMkLDhoy2c815X9ns6tOjJYYnBvh7Am7OH33mQF_fyAQKacu09i3kb4H8JYd7tqgqsuFQ2nN_K23W_WXlRv0A");
        nameValue[1]=new BasicNameValuePair("openid","oIaNTwVfVcGGJWJfEv4i4xC8FLU4");
        nameValue[2]=new BasicNameValuePair("lang", "zh_CN");
        try {
            String params=HttpRequestUtils.get("https://api.weixin.qq.com/sns/userinfo",nameValue);
            System.out.println(params);
            Map<String,String> hashMap= (Map<String, String>) JSONObject.parse(params);
            System.out.println(hashMap.get("nickname"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testPassword(){
        System.out.println(Base64Utils.encodeToString("天道酬勤".getBytes()));
    }
    @Test
    public void testGetNickName(){
        ThirdLogin login = thirdLoginMapper.selectByPrimaryKey(8L);
        String nickname = Base64Utils.encodeToString(login.getNickname().getBytes());
        System.out.println(nickname);
        String urlencode="8J+QgPCfmr7kuozotKc=";
        if (nickname.equals(urlencode)){
            System.out.println("chengle");
        }else {
            System.out.println("bucheng");
        }
        System.out.println(Base64Utils.encodeToUrlSafeString(urlencode.getBytes()));
        System.out.println(EmojiUtil.resolveToByteFromEmoji(login.getNickname()));
    }
    @Test
    public void testMemberScoreMapper(){
        Double value=memberScoreFlowMapper.getToalFlowScore(7L);
        System.out.println(value);
    }
}

