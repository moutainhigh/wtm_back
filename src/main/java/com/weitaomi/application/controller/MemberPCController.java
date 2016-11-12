package com.weitaomi.application.controller;

import com.weitaomi.application.controller.baseController.BaseController;
import com.weitaomi.application.service.interf.*;
import com.weitaomi.systemconfig.dataFormat.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;

/**
 * Created by Administrator on 2016/8/23.
 */
@Controller
@RequestMapping("/pc/admin/member")
public class MemberPCController extends BaseController {
    @Autowired
    private IMemberTaskPoolService memberTaskPoolService;

    @Autowired
    private IAppVersionService appVersionService;
    /**
     * 获取用户信息
     * @throws ParseException    the parse exception
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/getMemberAccountPublishMsg", method = RequestMethod.POST)
    public AjaxResult getMemberAccountPublishMsg(Long memberId){
        return AjaxResult.getOK(memberTaskPoolService.getRequireFollowerParamsDto(memberId));
    }
    /**
     * 获取用户信息
     * @throws ParseException    the parse exception
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/getMemberArticlePublishMsg", method = RequestMethod.POST)
    public AjaxResult getMemberArticlePublishMsg(Long memberId){
        return AjaxResult.getOK(memberTaskPoolService.getMemberArticlePublishMsg(memberId));
    }

    /**
     * 更新版本号
     * @throws ParseException    the parse exception
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/updateAppVersion", method = RequestMethod.POST)
    public AjaxResult updateAppVersion(Integer platFlag,String version,@RequestParam(required = false) String link){
        return AjaxResult.getOK(appVersionService.updateAppVersion(platFlag, version,link));
    }
    /**
     * 获取版本号
     * @throws ParseException    the parse exception
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/getAppVersion", method = RequestMethod.GET)
    public AjaxResult getAppVersion(Integer platFlag,@RequestParam(required = false,defaultValue = "0") Integer flag){
        return AjaxResult.getOK(appVersionService.getCurrentVersion(platFlag,flag));
    }
}
