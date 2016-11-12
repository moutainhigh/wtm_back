package com.weitaomi.application.controller;

import com.weitaomi.application.controller.baseController.BaseController;
import com.weitaomi.application.service.interf.IMemberTaskHistoryService;
import com.weitaomi.systemconfig.dataFormat.AjaxResult;
import com.weitaomi.systemconfig.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/10/13.
 */
@Controller
@RequestMapping("/pc/admin/memberTask")
public class MemberTaskHistoryPCController extends BaseController{
    @Autowired
    private IMemberTaskHistoryService memberTaskHistoryService;
    /**
     * 获取用户任务记录
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMemberTaskInfo",method = RequestMethod.POST)
    public AjaxResult getMemberTaskInfo(HttpServletRequest httpServletRequest, Integer type, @RequestParam(defaultValue ="10") Integer pageSize, Integer pageIndex){
        Long memberId=super.getUserId(httpServletRequest);
        return AjaxResult.getOK(memberTaskHistoryService.getMemberTaskInfo(memberId,type,pageSize,pageIndex));
    }
}
