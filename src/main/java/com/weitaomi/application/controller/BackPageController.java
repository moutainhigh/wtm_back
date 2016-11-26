package com.weitaomi.application.controller;

import com.weitaomi.application.controller.baseController.BaseController;
import com.weitaomi.application.model.bean.Account;
import com.weitaomi.application.model.mapper.AccountMapper;
import com.weitaomi.application.service.interf.IAppVersionService;
import com.weitaomi.application.service.interf.IBackPageService;
import com.weitaomi.systemconfig.dataFormat.AjaxResult;
import com.weitaomi.systemconfig.util.GetMacAddress;
import com.weitaomi.systemconfig.util.IpUtils;
import com.weitaomi.systemconfig.util.PropertiesUtil;
import com.weitaomi.systemconfig.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/3.
 */
@Controller
@RequestMapping("")
public class BackPageController extends BaseController {
    @Autowired
    private IAppVersionService appVersionService;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private IBackPageService backPageService;
    @RequestMapping("/pc/download/latestVersion")
    public void getLatestVersion(HttpServletResponse response) {
        try {
            OutputStream out = response.getOutputStream();
            String version = (String) appVersionService.getCurrentVersion(4, 0);
            String fileName = "weitaomi" + version + ".apk";
            String prefix = "C:\\Users\\Administrator\\Desktop\\";
            prefix= PropertiesUtil.getValue("server.latest.version");
            File file = new File(prefix + fileName);
            InputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            response.setHeader("content-disposition", "attachment;filename=" + file.getName());
            while ((len = inputStream.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/pc/download/uploadLatestVersion")
    public void uploadLatestVersion(String file, String version) {
        try {
            String fileName = "weitaomi" + version + ".apk";
            String prefix = "C:\\Users\\Administrator\\Desktop\\";
//             prefix= PropertiesUtil.getValue("server.latest.version");
            OutputStream outputStream = new FileOutputStream(prefix + fileName);
            BASE64Decoder base64Decoder = new BASE64Decoder();
            InputStream inputStream = new ByteArrayInputStream(base64Decoder.decodeBuffer(file));
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/backDeal")
    public ModelAndView redirectToBackDeal(HttpServletRequest request, HttpServletResponse response) {
        String ip = IpUtils.getIpAddr(request);
        String ipWhite = PropertiesUtil.getValue("backDeal.ipWhites");
        boolean flag = false;
        if (ip.matches(ipWhite)) {
            flag = true;
        }
        if (!flag) {
            String mac = GetMacAddress.getMacAddress(ip);
            String[] macs = PropertiesUtil.getValue("backDeal.macWhites").split(";");
            for (String temp : macs) {
                if (mac == temp) {
                    flag = true;
                    break;
                }
            }
        }
        if (flag) {
            ModelAndView modelAndView = new ModelAndView();
            String sId = request.getHeader("accountId");
            Long accountId = 0L;
            if (!StringUtils.isEmpty(sId)) {
                accountId = Long.parseLong(sId);
                HttpSession session = request.getSession();
                if (session != null) {
                    String realName = (String) session.getAttribute("realName");
                    if (!StringUtil.isEmpty(realName)) {
                        Account account = accountMapper.getAccount(realName);
                        if (account != null) {
                            //成功进入
                            modelAndView.setViewName("");
                        } else {
                            //失败进入
                            modelAndView.setViewName("/backDeal/login");
                            return modelAndView;
                        }
                    }
                } else {
                    //失败进入
                    modelAndView.setViewName("/backDeal/login");
                    return modelAndView;
                }
            } else {
                //失败进入
                modelAndView.setViewName("/backDeal/login");
                return modelAndView;
            }
        }
        return null;
    }

    @RequestMapping("/backDeal/login")
    public ModelAndView login(HttpServletRequest request,String realName,String password,HttpServletResponse response) {
        String ip = IpUtils.getIpAddr(request);
        String ipWhite = PropertiesUtil.getValue("backDeal.ipWhites");
        boolean flag = false;
        if (ip.matches(ipWhite)) {
            flag = true;
        }
        if (!flag) {
            String mac = GetMacAddress.getMacAddress(ip);
            String[] macs = PropertiesUtil.getValue("backDeal.macWhites").split(";");
            for (String temp : macs) {
                if (mac == temp) {
                    flag = true;
                    break;
                }
            }
        }
        if (flag) {
            Account account=new Account();
            account.setRealName(realName);
            account.setPassword(password);
            Account account1=accountMapper.selectOne(account);
            if (account1!=null){
                HttpSession session=request.getSession();
                Cookie idCookie=new Cookie("accountId",account.getId().toString());
                idCookie.setMaxAge(24*60*60);
                idCookie.setPath("/frontPage/backward");
                response.addCookie(idCookie);
                session.setAttribute("realName",realName);
                ModelAndView modelAndView=new ModelAndView("/backDeal");
                return modelAndView;
            }
        }
        return  null;
    }
    @ResponseBody
    @RequestMapping(value = "/pc/backDeal/dealWithArticle",method = RequestMethod.POST)
    public AjaxResult getAllWaitApprovalArticleList(@RequestParam(name = "pageIndex",defaultValue = "0",required = false) Integer pageIndex,
                                                    @RequestParam(name = "pageSize",defaultValue = "10",required = false) Integer pageSize){
        if (pageIndex==null){
            pageIndex=0;
        }
        if (pageSize==null){
            pageSize=10;
        }
        return AjaxResult.getOK(backPageService.getAllArticle(pageIndex, pageSize));
    }
    @ResponseBody
    @RequestMapping("/pc/backDeal/patchCheckArticle")
    public AjaxResult patchCheckArticle(List<Long> poolIdList){
        return AjaxResult.getOK(backPageService.patchCheckArticle(poolIdList));
    }

    /**
     * 上传文件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/backward/uploadUpyunFiles", method = RequestMethod.POST)
    public AjaxResult uploadShowImage(HttpServletRequest request,@RequestBody(required = true) Map<String,String> params){
        Long memberId=this.getUserId(request);
        String files=params.get("files");
        String path=params.get("path");
        String suffix=params.get("suffix");
        Integer flag=Integer.parseInt(params.get("flag").toString());
        String yunPath="";
        if (!StringUtil.isEmpty(files)&&!StringUtil.isEmpty(path)&&!StringUtil.isEmpty(suffix)){
            yunPath=backPageService.uploadUpyunFiles(path,files,suffix,flag);
        }
        return AjaxResult.getOK(yunPath);
    }
}
