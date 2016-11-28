package com.weitaomi.application.model.mapper;

import com.weitaomi.application.model.bean.Member;
import com.weitaomi.application.model.bean.ThirdLogin;
import com.weitaomi.application.model.dto.MemberInfoDto;
import com.weitaomi.application.model.dto.MemberSearch;
import com.weitaomi.application.model.dto.MemberSearchInformation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface MemberMapper extends IBaseMapper<Member> {
    MemberInfoDto getMemberByTelephone(@Param("telephone")String telephone, @Param("sourceType") Integer sourceType);
    MemberInfoDto getMemberInfoById(@Param("memberId")Long memberId,@Param("sourceType") Integer sourceType);
    MemberInfoDto getMemberByMemberName(@Param("memberName")String memberName,@Param("sourceType") Integer sourceType);
    Member getMemberByInviteCode(@Param("inviteCode")String inviteCode);
    int getIsBindWxAccountsByMobile(@Param("mobile") String mobile, @Param("sourceType") Integer sourceType);
    int getIsBindWxAccountsByMemberName(@Param("memberName") String memberName, @Param("sourceType") Integer sourceType);
    public Member getByCellphoneAndPassword(@Param("cellphone")String cellphone,@Param("password")String password);
    int upLoadMemberShowImage(@Param("memberId") Long memberId, @Param("imageUrl") String imageUrl);
    List<Long> getAllMemberId();
    List<Map<String,Long>> getIsFollowWtmAccount(@Param("memberId") Long memberId, @Param("sourceType") Integer sourceType);
    int updateMemberPhoneType(@Param("memberId") Long memberId, @Param("phoneType") String phoneType);
    List<MemberSearchInformation> getMemberSearchInformation(@Param("memberSearch") MemberSearch memberSearch, @Param("rowBounds") RowBounds rowBounds);
}