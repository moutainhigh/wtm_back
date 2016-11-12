package com.weitaomi.application.controller;

import com.weitaomi.application.controller.baseController.BaseController;
import com.weitaomi.application.model.bean.Member;
import com.weitaomi.application.model.dto.RegisterMsg;
import com.weitaomi.application.service.interf.IMemberInvitedRecordService;
import com.weitaomi.application.service.interf.IMemberService;
import com.weitaomi.systemconfig.dataFormat.AjaxResult;
import com.weitaomi.systemconfig.exception.BusinessException;
import com.weitaomi.systemconfig.exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * Created by Administrator on 2016/8/25.
 */
@Controller
@RequestMapping("/pc/admin/memberInvited")
public class MemberInvitedPCController extends BaseController {
    private static Logger logger= LoggerFactory.getLogger(MemberInvitedPCController.class);
    @Autowired
    private IMemberInvitedRecordService memberInvitedRecordService;

    /**
     * 获取用户邀请信息
     * @param request
     * @return
     * @throws BusinessException
     * @throws DBException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value = "/getInvitedParamsDto", method = RequestMethod.POST)
    public AjaxResult registerAction(HttpServletRequest request)
            throws BusinessException, DBException, ParseException {
        Long memberId=this.getUserId(request);
        return AjaxResult.getOK(memberInvitedRecordService.getInvitedParamsDto(memberId));
    }
    /**
     * 获取用户邀请
     * @param request
     * @return
     * @throws BusinessException
     * @throws DBException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value = "/getInvitedRecordList", method = RequestMethod.POST)
    public AjaxResult getInvitedRecordList(HttpServletRequest request,@RequestParam(required = false) Integer pageIndex,@RequestParam(required = false) Integer pageSize,@RequestParam(required = false) Integer type){
        Long memberId=this.getUserId(request);
        if (pageIndex==null){
            pageIndex=0;
        }
        if (pageSize==null){
            pageSize=0;
        }
        if (type!=null&&type==1){
            return AjaxResult.getOK(memberInvitedRecordService.getInvitedRecordList(memberId,pageIndex,pageSize));
        }else {
            return AjaxResult.getOK(memberInvitedRecordService.getInvitedRecordListTemp(memberId));
        }
    }
    /**
     * 获取分享达人
     * @return
     * @throws BusinessException
     * @throws DBException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value = "/getTotalSharedMsg", method = RequestMethod.POST)
    public AjaxResult getTotalSharedMsg(){
        return AjaxResult.getOK(memberInvitedRecordService.getTotalSharedMsg());
    }
}
