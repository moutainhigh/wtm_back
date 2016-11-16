package com.weitaomi.application.service.interf;

import com.weitaomi.application.model.bean.MemberScore;
import com.weitaomi.application.model.bean.MemberTaskHistoryDetail;
import com.weitaomi.application.model.dto.MemberTaskDto;
import com.weitaomi.application.model.dto.MemberTaskWithDetail;
import com.weitaomi.systemconfig.util.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/16.
 */
public interface IMemberTaskHistoryService {
    /**
     * 获取用户的任务列表
     *
     * type :0 未完成，1已完成
     *
     * @param memberId
     * @return
     */
    public Page<MemberTaskWithDetail> getMemberTaskInfo(Long memberId, Integer type, Integer pageSize, Integer pageIndex);


    /**
     * 增加任务记录
     * @param
     * @return
     */
    public boolean addMemberTaskToHistory(Long memberId, Long taskId, Double score, Integer flag,String detail,List<MemberTaskHistoryDetail> detailList,String taskFlag);

    /**
     * 定时清除未完成的任务
     */
    public void deleteUnFinishedTask();

    void threeOclockScheduledJob();

    void twoOclockScheduledJob();
}
