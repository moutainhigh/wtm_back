package com.weitaomi.application.service.interf;

import java.util.List;
import com.weitaomi.application.model.bean.TaskPool;
import com.weitaomi.application.model.dto.PublishReadRequestDto;
import com.weitaomi.application.model.dto.RequireFollowerParamsDto;
import com.weitaomi.application.model.dto.TaskPoolDto;
import com.weitaomi.systemconfig.util.Page;

/**
 * Created by Administrator on 2016/8/24.
 */
public interface IMemberTaskPoolService {
    /**
     * 获取求粉页相关信息
     * @param memberId
     * @return
     */
    public RequireFollowerParamsDto getRequireFollowerParamsDto(Long memberId);
    /**
     * 获取求粉页相关信息
     * @param memberId
     * @return
     */
    public RequireFollowerParamsDto getMemberArticlePublishMsg(Long memberId);

    /**
     * 获取公众号任务
     * @param officialAccountId
     * @return
     */
    public Page<TaskPoolDto> getTaskPoolDto(Long officialAccountId, Integer type, int pageSize, int pageIndex);

    /**
     * 控制任务发布与否
     * @param taskPoolId
     * @param isPublishNow
     * @return
     */
    public Boolean updateTaskPoolDto(Long memberId,Long taskPoolId, Integer isPublishNow,Integer needNumber,Double singScore,Integer limitDay);
}
