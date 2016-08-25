package com.weitaomi.application.controller;

import com.alibaba.fastjson.JSON;
import com.weitaomi.application.controller.baseController.BaseController;
import com.weitaomi.application.model.bean.ThirdLogin;
import com.weitaomi.application.model.dto.AddOfficalAccountDto;
import com.weitaomi.application.model.mapper.MemberMapper;
import com.weitaomi.application.model.mapper.ThirdLoginMapper;
import com.weitaomi.application.service.interf.IMemberTaskHistoryService;
import com.weitaomi.application.service.interf.IOfficeAccountService;
import com.weitaomi.systemconfig.dataFormat.AjaxResult;
import com.weitaomi.systemconfig.util.JpushUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/16.
 */
@Controller
@RequestMapping("/app/admin/official")
public class OfficialAccountController extends BaseController{
    @Autowired
    private IOfficeAccountService officeAccountService;
    @Autowired
    private ThirdLoginMapper thirdLoginMapper;

    /**
     * 获取用户每日任务
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getFollowOfficialAccountList",method = RequestMethod.POST)
    public AjaxResult getFollowOfficialAccountList(HttpServletRequest httpServletRequest,String unionId){
        Long memberId=super.getUserId(httpServletRequest);
        return AjaxResult.getOK(officeAccountService.getOfficialAccountMsg(memberId,unionId));
    }

    /**
     * 加入公众号任务关注列表
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pushAddRequest",method = RequestMethod.POST)
    public AjaxResult pushAddRequest(HttpServletRequest request,@RequestBody AddOfficalAccountDto addOfficalAccountDto){
        Long memberId=super.getUserId(request);
        officeAccountService.pushAddRequest(memberId,addOfficalAccountDto);
        return AjaxResult.getOK();
    }
    /**
     * 加入公众号任务关注列表
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pushAddFinished",method = RequestMethod.POST)
    public AjaxResult pushAddFinished(@RequestBody Map<String,String> params){
        System.out.println(params.get("originId")+"======="+params.get("unionId"));
        return AjaxResult.getOK();
    }
    /**
     * 加入公众号任务关注列表
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/notifyMemberTask",method = RequestMethod.POST)
    public AjaxResult notifyMemberTask(Map<String,String> params){
        String unionId=params.get("unionId");
        String status=params.get("status");
        if (status.equals("0")) {
            Long memberId = thirdLoginMapper.getMemberIdByUnionId(unionId);
            Map<String, String> param = new ManagedMap<>();
            if (memberId != null) {
                param.put("memberId", memberId.toString());
                param.put("message", "任务五分钟之后即将失效，请尽快到服务号完成");
            }
            if (param != null) {
                JpushUtils.buildRequest(JSON.toJSONString(param));
            }
        }
        return AjaxResult.getOK();
    }
}