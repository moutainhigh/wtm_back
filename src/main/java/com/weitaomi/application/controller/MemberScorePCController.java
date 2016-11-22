package com.weitaomi.application.controller;

import com.weitaomi.application.controller.baseController.BaseController;
import com.weitaomi.application.service.interf.IMemberScoreService;
import com.weitaomi.systemconfig.dataFormat.AjaxResult;
import com.weitaomi.systemconfig.exception.BusinessException;
import com.weitaomi.systemconfig.util.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    /**
     * 增加的积分类型
     * @param typeId
     * @param score
     * @param sessionId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMemberScore",method = RequestMethod.POST)
    AjaxResult addMemberScore(HttpServletRequest request, Long typeId, Integer isFinished, Double score, String sessionId){
        Long memberId=this.getUserId(request);
        if (memberId==null){
            throw new BusinessException("用户ID为空");
        }
        return AjaxResult.getOK(memberScoreService.addMemberScore(memberId, typeId,isFinished, score, sessionId));
    }
    @ResponseBody
    @RequestMapping(value = "/justForTest",method = RequestMethod.POST)
    AjaxResult justForTest(HttpServletRequest request){
        for (long i=1;i<=5;i++) {
            final long finalI = i;
            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    memberScoreService.test(finalI);
                }
            });
        }
        return AjaxResult.getOK();
    }


}
