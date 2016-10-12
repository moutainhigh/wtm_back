package com.weitaomi.application.controller;

import com.weitaomi.application.controller.baseController.BaseController;
import com.weitaomi.application.model.dto.ArticleSearch;
import com.weitaomi.application.service.interf.IArticleService;
import com.weitaomi.systemconfig.dataFormat.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * Created by Administrator on 2016/10/11.
 */
@Controller
@RequestMapping("/pc/admin/article")
public class ArticlePcController extends BaseController {
    @Autowired
    private IArticleService articleService;
    /**
     * 根据条件获取文章列表
     * @param articleSearch
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllArticle",method = RequestMethod.POST)
    public AjaxResult getAllArticle(HttpServletRequest request, @RequestBody(required = false) ArticleSearch articleSearch){
        Long memberId=this.getUserId(request);
        return AjaxResult.getOK(articleService.getAllArticle(memberId,articleSearch));
    }
    /**
     * 阅读文章
     * @throws ParseException    the parse exception
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/pcreadArticleRequest", method = RequestMethod.POST)
    public AjaxResult getArticleList(HttpServletRequest request,@RequestParam(required = false) Long time, Long articleId){
        long memberId=this.getUserId(request);
        return AjaxResult.getOK(articleService.pcreadArticleRequest(memberId,articleId));
    }

}
