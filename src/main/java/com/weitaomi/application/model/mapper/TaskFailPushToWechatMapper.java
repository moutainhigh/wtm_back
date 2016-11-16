package com.weitaomi.application.model.mapper;

import com.weitaomi.application.model.bean.TaskFailPushToWechat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskFailPushToWechatMapper extends IBaseMapper<TaskFailPushToWechat> {
    List<TaskFailPushToWechat> getAllTaskFailPushToWechat(@Param("idList") List<Long> idList);
}