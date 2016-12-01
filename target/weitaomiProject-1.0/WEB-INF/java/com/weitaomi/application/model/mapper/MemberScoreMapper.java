package com.weitaomi.application.model.mapper;

import com.weitaomi.application.model.bean.MemberScore;
import com.weitaomi.application.model.dto.MemberScoreFlowDto;
import com.weitaomi.application.model.dto.MemberScoreInformation;
import com.weitaomi.application.model.dto.MyWalletDto;
import com.weitaomi.application.model.dto.OfficialAddAvaliableScore;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface MemberScoreMapper extends IBaseMapper<MemberScore> {
    MemberScore getMemberScoreByMemberId(@Param("memberId")Long memberId);
    Integer getAvaliableMemberScore(@Param("memberIdList")List<Long> memberIdList, @Param("time") Long time);
    Map getAvaliableScoreAndWxInfo(@Param("memberId")Long memberId);
    Integer updateOneAvaliableMemberScore(@Param("avaliableScore") Double avaliableScore, @Param("memberId") Long memberId);
    Double getOneAvaliableMemberScore(@Param("memberId")Long memberId, @Param("time") Long time);
    List<MemberScoreFlowDto> getMyWalletDtoByMemberId(@Param("memberId") Long memberId, @Param("rowBounds") RowBounds rowBounds);
    int updateMemberAvaliableScore(@Param("officialAddAvaliableScoreList") List<OfficialAddAvaliableScore> officialAddAvaliableScoreList);
    MemberScoreInformation getMemberScoreInformation(@Param("memberId") Long memberId);
}