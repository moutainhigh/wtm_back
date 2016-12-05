package com.weitaomi.application.service.interf;

import com.weitaomi.application.model.bean.OfficialAccount;
import com.weitaomi.application.model.dto.AddOfficalAccountDto;
import com.weitaomi.application.model.dto.MemberAccountLabel;
import com.weitaomi.application.model.dto.OfficialAccountMsg;
import com.weitaomi.application.model.dto.OfficialAccountsDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by supumall on 2016/8/9.
 */
public interface IOfficeAccountService {

    /**
     * {"originId":""," nickname ":"昵称，如果unionId一致则换成unionId","time":"关注时间"}
     */
    public Boolean pushAddFinished(Map<String,Object> params);

    void taskFailPushToWechat();

    void sendRequestToWechatDialog(String unionId, List<Long> idList);

    void taskFailToAckAddRequest();
}
