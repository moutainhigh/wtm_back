package com.weitaomi.application.model.mapper;

import com.weitaomi.application.model.bean.MemberTaskHistory;
import com.weitaomi.application.model.dto.MemberTaskWithDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberTaskHistoryMapper extends IBaseMapper<MemberTaskHistory> {
    List<MemberTaskWithDetail> getMemberTaskHistoryList(@Param("memberId") Long memberId, @Param("type") Integer type, @Param("rowBounds") RowBounds rowBounds);
    Integer updateMemberTaskUnfinished(@Param("memberId") Long memberId, @Param("type") Integer type, @Param("originId") String originId);
    int deleteUnfinishedTask(@Param("time") Integer time);
    int deleteUnfinishedTaskDetail(@Param("time") Integer time);
}