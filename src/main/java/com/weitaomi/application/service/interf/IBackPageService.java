package com.weitaomi.application.service.interf;

import com.weitaomi.application.model.dto.ArticleShowDto;
import com.weitaomi.application.model.dto.MemberSearch;
import com.weitaomi.application.model.dto.MemberSearchInformation;
import com.weitaomi.systemconfig.util.Page;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public interface IBackPageService {
    Page<ArticleShowDto> getAllArticle(Integer pageIndex, Integer pageSize);

    String sendIndentifyCode(String mobile, Integer type);

    int patchCheckArticle(List<Long> poolIdList);
    /**
     * 上传文件
     * @param path
     * @param files
     * @return
     */
    String uploadUpyunFiles(String path, String files,String suffix,Integer temp);

    /**
     * 查询用户详细的信息 查询内容  1.米币总数查询  2.米币流水查询  3.任务记录  4.公众号信息  5.邀请记录
     * @param memberId
     * @param flag
     * @return
     */
    Object getMemberInformationDetail(Long memberId,Integer flag,Integer pageIndex,Integer pageSize);

    /**
     * 查询用户信息
     * @param memberSearch
     * @return
     */
    Page<MemberSearchInformation> getMemberInformation(MemberSearch memberSearch);
}
