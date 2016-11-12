package com.weitaomi.application.controller;


import com.weitaomi.application.controller.baseController.BaseController;
import com.weitaomi.application.service.interf.IPaymentService;
import com.weitaomi.systemconfig.dataFormat.AjaxResult;
import com.weitaomi.systemconfig.util.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/31.
 */
@Controller
@RequestMapping("/pc/admin/paymemberCallBack")
public class PaymemberCallBackController extends BaseController{
    private Logger logger= LoggerFactory.getLogger(PaymemberCallBackController.class);
    @Autowired
    private IPaymentService paymentService;
    @ResponseBody
    @RequestMapping(value = "/patchWechatCustomers", method = RequestMethod.POST)
    public AjaxResult  patchWechatCustomers(@RequestBody List<Map<String, Object>> parameters,HttpServletRequest request){
        Long memberId= this.getUserId(request);
        return AjaxResult.getOK(paymentService.patchWechatCustomers(parameters,IpUtils.getIpAddr(request),memberId));
    }
    @ResponseBody
    @RequestMapping(value = "getApproveList",method = RequestMethod.GET)
    public AjaxResult getApproveList(Integer pageIndex,Integer pageSize){
        return AjaxResult.getOK(paymentService.getPaymentApproveList(pageIndex, pageSize));
    }

}
