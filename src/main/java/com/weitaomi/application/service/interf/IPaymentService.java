package com.weitaomi.application.service.interf;

import com.weitaomi.application.model.bean.MemberPayAccounts;
import com.weitaomi.application.model.bean.MemberScore;
import com.weitaomi.application.model.bean.PaymentApprove;
import com.weitaomi.application.model.dto.MemberScoreFlowDto;
import com.weitaomi.application.model.dto.MyWalletDto;
import com.weitaomi.application.model.dto.RequireFollowerParamsDto;
import com.weitaomi.systemconfig.util.Page;
import com.weitaomi.systemconfig.wechat.WechatNotifyParams;

import java.util.List;
import java.util.Map;

/**
 * Created by supumall on 2016/7/22.
 */
public interface IPaymentService {

    /**
     * 将审核通过的订单放入队列当中
     * @param parameters
     * @param ip
     * @return
     */
    Boolean patchWechatCustomers(List<Map<String, Object>> parameters, String ip,Long memberId);

    /**
     * 微信提现付款
     * @param param
     * @return
     */
    boolean dealWithdrawsChange(Map<String, Object> param);

    /**
     * 获取用户提现列表
     * @return
     */
    Page<PaymentApprove> getPaymentApproveList(Integer pageIndex,Integer pageSize);
}
