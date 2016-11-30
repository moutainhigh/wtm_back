package com.weitaomi.application.model.mapper;

import com.github.abel533.mapper.Mapper;
import com.weitaomi.application.model.bean.TaskPool;
import com.weitaomi.application.model.dto.ArticleSearch;
import com.weitaomi.application.model.dto.ArticleShowDto;
import com.weitaomi.application.model.dto.TaskPoolDto;
import com.weitaomi.application.model.dto.TaskPoolReturnBack;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface TaskPoolMapper extends IBaseMapper<TaskPool> {
    List<Map<String,String>> getAtricleList(@Param("rowBounds")RowBounds rowBounds);
    TaskPool getTaskPoolByOfficialId(@Param("officialAccountId") Long officialAccountId, @Param("isPublishNow") Integer isPublishNow);
    TaskPool getTaskPoolByArticleId(@Param("articleId") Long articleId, @Param("isPublishNow") Integer isPublishNow);
    int updateTaskPoolWithScore(@Param("score") Double score, @Param("taskId") Long taskId);
    List<TaskPoolDto> getTaskPoolArticleDto(@Param("officialAccount") Long officialAccount, @Param("rowBounds") RowBounds rowBounds);
    List<TaskPoolDto> getTaskPoolAccountDto(@Param("officialAccount") Long officialAccount, @Param("rowBounds") RowBounds rowBounds);
    int patchCheckArticle(@Param("poolIdList") List<Long> poolIdList);
    List<TaskPoolReturnBack> getReturnBackScoreToSeller();
    Integer updateReturnBackTask(@Param("idList") List<Long> idList);
}