package com.weitaomi.application.controller;

import com.alibaba.fastjson.JSON;
import com.weitaomi.application.controller.baseController.BaseController;
import com.weitaomi.application.model.dto.AddOfficalAccountDto;
import com.weitaomi.application.model.dto.OfficialAccountMsg;
import com.weitaomi.application.model.mapper.ThirdLoginMapper;
import com.weitaomi.application.service.interf.IMemberTaskPoolService;
import com.weitaomi.application.service.interf.IOfficeAccountService;
import com.weitaomi.systemconfig.dataFormat.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/11.
 */
@Controller
@RequestMapping("/pc/admin/official")
public class OfficialAccountsPCController extends BaseController{
    @Autowired
    private IOfficeAccountService officeAccountService;
    @Autowired
    private IMemberTaskPoolService memberTaskPoolService;
    @Autowired
    private ThirdLoginMapper thirdLoginMapper;

    /**
     * 获取公众号列表
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOfficialAccountList",method = RequestMethod.POST)
    public AjaxResult getOfficialAccountList(HttpServletRequest request){
        Long memberId=super.getUserId(request);
        return AjaxResult.getOK(officeAccountService.getOfficialAccountList(memberId));
    }
    /**
     * 获取公众号任务管理列表
     * @param officialAccountId
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTaskPoolDto", method = RequestMethod.POST)
    public AjaxResult getTaskPoolDto(Long officialAccountId, Integer type, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "0")int pageIndex){
        return AjaxResult.getOK(memberTaskPoolService.getTaskPoolDto(officialAccountId,type,pageSize,pageIndex));
    }
    /**
     * 修改任务
     * @param taskPoolId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateTaskPoolDto", method = RequestMethod.POST)
    public AjaxResult updateTaskPoolDto(HttpServletRequest request,Long taskPoolId, Integer isPublishNow,
                                        @RequestParam(required = false) Integer needNumber,
                                        @RequestParam(required = false) Double singScore,
                                        @RequestParam(required = false) Integer limitDay){
        Long memberId=this.getUserId(request);
        return AjaxResult.getOK(memberTaskPoolService.updateTaskPoolDto(memberId,taskPoolId, isPublishNow, needNumber, singScore,limitDay));
    }
}
