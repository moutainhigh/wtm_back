package com.weitaomi.application.service.interf;

import com.weitaomi.application.model.bean.Article;
import com.weitaomi.application.model.dto.ArticleDto;
import com.weitaomi.application.model.dto.ArticleReadRecordDto;
import com.weitaomi.application.model.dto.ArticleSearch;
import com.weitaomi.application.model.dto.ArticleShowDto;
import com.weitaomi.systemconfig.util.Page;

import java.util.List;

/**
 * Created by supumall on 2016/7/7.
 */
public interface IArticleService {
    /**
     * 根据条件获取文章列表
     * @param articleSearch
     * @return
     */
    public Page<ArticleShowDto> getAllArticle(Long memberId,ArticleSearch articleSearch,Integer sourceType);

}
