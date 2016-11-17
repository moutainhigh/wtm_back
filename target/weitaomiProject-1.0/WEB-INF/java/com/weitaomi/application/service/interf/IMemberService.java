package com.weitaomi.application.service.interf;

import com.weitaomi.application.model.bean.Member;
import com.weitaomi.application.model.bean.TaskPool;
import com.weitaomi.application.model.bean.ThirdLogin;
import com.weitaomi.application.model.dto.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/27.
 */
public interface IMemberService {
    /**
     * 获取验证码
     * @param mobile
     * @param type
     * @return
     */
    public String sendIndentifyCode(String mobile,Integer type);

    /**
     * 绑定第三方平台
     * @param thirdLogin
     * @return
     */
    public Boolean bindThirdPlat(Long memberId,ThirdLogin thirdLogin,Integer sourceType);

    /**
     * 会员登陆
     * @param mobileOrName
     * @param password
     * @return
     */
    public MemberInfoDto login(String mobileOrName, String password,Integer sourceType);

    /**
     * 第三方登陆
     * @param openId
     * @param type
     * @return
     */
    public MemberInfoDto thirdPlatLogin(String openId,String openid,Integer type,Integer sourceType);

    /**
     * 获取用户信息
     * @param memberId
     * @return
     */
    public Member getMemberDetailById(Long memberId);

   /**
    * 更换密码
    * @param memberId
    * @param modifyPasswordDto
    * @return
       */
     String modifyPassWord(Long memberId, ModifyPasswordDto modifyPasswordDto);
    /**
    * 更换密码
    * @param memberId
    * @param birth
    * @return
       */
     boolean modifyBirth(Long memberId,Long birth);

    /**
     * 更新用户地址
     * @param memberId
     * @param memberAddress
     * @return
     */
     boolean modifyMemberAddress(Long memberId, String  memberAddress);
 /**
     * 更新用户名
     * @param memberId
     * @param memberName
     * @return
     */
     boolean modifyMemberName(Long memberId, String  memberName);

     /**
      * 上传用户头像
      * @param memberId
      * @return
         */
    public String uploadShowImage(Long memberId,String imageFiles,String imageType);

    Boolean validateIndetifyCode(String mobile, String indentifyCode);


}
