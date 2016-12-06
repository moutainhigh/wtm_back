package com.weitaomi.application.controller;

import com.weitaomi.application.controller.baseController.BaseController;
import com.weitaomi.application.service.interf.IBackPageService;
import com.weitaomi.application.service.interf.ICacheService;
import com.weitaomi.application.service.interf.IKeyValueService;
import com.weitaomi.application.service.interf.IMemberScoreService;
import com.weitaomi.systemconfig.dataFormat.AjaxResult;
import com.weitaomi.systemconfig.exception.BusinessException;
import com.weitaomi.systemconfig.exception.InfoException;
import com.weitaomi.systemconfig.util.IpUtils;
import com.weitaomi.systemconfig.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/11/1.
 */
@Controller
@RequestMapping("/pc/admin/memberScore")
public class MemberScorePCController extends BaseController{
    @Autowired
    private IMemberScoreService memberScoreService;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private IKeyValueService keyValueService;
    @Autowired
    private IBackPageService backPageService;
    @Autowired
    private ICacheService cacheService;
    /**
     * 增加的积分类型
     * @param typeId
     * @param score
     * @param sessionId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMemberScore",method = RequestMethod.POST)
    AjaxResult addMemberScore(Long memberId, Long typeId, Double score, double rate,String sessionId,String telephone, String identifyCode){
        if (memberId==null){
            throw new BusinessException("用户ID为空");
        }
        double realScore=score*(1+rate)*100;
        String table = "member:charge:underLine";
        String key = "member:indentifyCode:" + telephone;
        if (!keyValueService.valueIsExist(table,telephone)){
            throw new InfoException("操作人员不合法");
        }
        String identifyCodeBack=cacheService.getCacheByKey(key,String.class);
        if (StringUtil.isEmpty(identifyCodeBack)||!identifyCodeBack.equals(identifyCode)){
            throw new InfoException("验证码不正确");
        }
        return AjaxResult.getOK(memberScoreService.addMemberScore(memberId, typeId,1,realScore,sessionId));
    }
    @ResponseBody
    @RequestMapping(value = "/sendIdentifyCode",method = RequestMethod.POST)
    AjaxResult justForTest(@RequestParam("mobile") String mobile, @RequestParam(value = "type", defaultValue ="0",required = false) Integer type){
        backPageService.sendIndentifyCode(mobile, type);
        return AjaxResult.getOK();
    }


}
