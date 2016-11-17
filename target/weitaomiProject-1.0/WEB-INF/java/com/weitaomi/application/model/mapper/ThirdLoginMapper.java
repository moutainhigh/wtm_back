package com.weitaomi.application.model.mapper;
import com.weitaomi.application.model.bean.ThirdLogin;
import com.weitaomi.application.model.dto.ThirdLoginDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ThirdLoginMapper extends IBaseMapper<ThirdLogin> {
    public List<ThirdLogin> getThirdLoginInfo(@Param("unionId") String unionId, @Param("sourceType") Integer sourceType);
    public ThirdLogin getThirdLoginByOpenId(@Param("openId") String openId);
    public ThirdLoginDto getThirdlogInDtoMemberId(@Param("memberId") Long memberId, @Param("sourceType") Integer sourceType);
    List<Long> getMemberIdByUnionId(@Param("unionId") String unionId, @Param("sourceType") Integer sourceType);
    ThirdLogin getUnionIdByMemberId(@Param("memberId") Long memberId, @Param("sourceType") Integer sourceType);
    List<ThirdLogin> getThirdLoginByMemberId(@Param("memberId") Long memberId);
    Map getNickNameAndSex(@Param("memberId") Long memberId);
}