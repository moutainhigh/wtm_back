package com.weitaomi.application.service.impl;

import com.weitaomi.systemconfig.exception.InfoException;
import com.weitaomi.systemconfig.fileManage.UpYun;
import com.weitaomi.systemconfig.util.StreamUtils;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/16.
 */
public class BaseService {
    public boolean uploadImage(String imageUrl,String imageFiles){
        BASE64Decoder base64Decoder=new BASE64Decoder();
        UpYun upYun=new UpYun("weitaomi","weitaomi","Weitaomi@Woyun");
        if (imageFiles==null){
            throw new InfoException("上传图片为空，请重新上传");
        }
        boolean flag=false;
        byte[] imageFile = new byte[0];
        try {
            imageFile=  base64Decoder.decodeBuffer(imageFiles);
            for (int i = 0; i < imageFile.length; ++i) {
                //调整异常数据
                if (imageFile[i] < 0) {
                    imageFile[i] += 256;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        flag= upYun.writeFile(imageUrl,imageFile);
        return flag;
    }

    public boolean uploadImage(byte[] imageFile,String imageUrl){
        BASE64Decoder base64Decoder=new BASE64Decoder();
        UpYun upYun=new UpYun("weitaomi","weitaomi","Weitaomi@Woyun");
        boolean flag=false;
            for (int i = 0; i < imageFile.length; ++i) {
                //调整异常数据
                if (imageFile[i] < 0) {
                    imageFile[i] += 256;
                }
            }
        flag= upYun.writeFile(imageUrl,imageFile);
        return flag;
    }

    public boolean uploadYunFiles(File file,String filePath){
        UpYun upYun = new UpYun("weitaomi", "weitaomi", "Weitaomi@Woyun");
        try {
            InputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = StreamUtils.InputStreamTOByte(fileInputStream);
            Map map=new HashMap();
            map.put("x-gmkerl-exif-switch",true);
            map.put("x-gmkerl-exif-switch",true);
            boolean flag = upYun.writeFile(filePath, bytes);
            return flag;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  false;
    }
}
