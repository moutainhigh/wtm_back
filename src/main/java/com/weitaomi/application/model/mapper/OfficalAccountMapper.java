package com.weitaomi.application.model.mapper;

import com.weitaomi.application.model.bean.OfficialAccount;
import com.weitaomi.application.model.dto.OfficialAccountMsg;
import com.weitaomi.application.model.dto.OfficialAccountWithScore;
import com.weitaomi.application.model.dto.OfficialAccountsDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfficalAccountMapper extends IBaseMapper<OfficialAccount> {
    List<OfficialAccountsDto> getAccountsByMemberId(@Param("memberId") Long memberId);
    OfficialAccount getOfficalAccountByoriginId(@Param("originId") String originId);
    List<OfficialAccountMsg> getOfficialAccountMsg(@Param("memberId") Long memberId, @Param("unionId") String unionId, @Param("sex") Integer sex, @Param("provinceCode") String provinceCode, @Param("cityCode") String cityCode);
    OfficialAccountWithScore getOfficialAccountWithScoreById(@Param("originId") String originId);
    List<OfficialAccount> getOfficialAccountList(@Param("memberId") Long memberId);
}