package com.weitaomi.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weitaomi.application.model.bean.KeyValue;
import com.weitaomi.application.model.dto.KeyValueDto;
import com.weitaomi.application.model.mapper.KeyValueMapper;
import com.weitaomi.application.service.interf.IKeyValueService;
import com.weitaomi.systemconfig.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/5.
 */
@Service
public class KeyValueService implements IKeyValueService{
    @Autowired
    private KeyValueMapper keyValueMapper;

    @Override
    public List<KeyValueDto> getKeyValueDtoList(String mapKey,String id) {
        String mapValue=keyValueMapper.getValueBykey(mapKey);
        List<KeyValueDto> keyValueDtoList=JSONObject.parseArray(mapValue,KeyValueDto.class);
        if (!keyValueDtoList.isEmpty()){
            if (StringUtil.isEmpty(id)){
                return keyValueDtoList;
            }else {
                List<KeyValueDto> keyValueDtos=new ArrayList<>();
                for (KeyValueDto keyValueDto:keyValueDtoList){
                    if (keyValueDto.getId().equals(id)){
                        keyValueDtos.add(keyValueDto);
                    }
                }
                return keyValueDtos;
            }
        }
        return null;
    }
    @Override
    public boolean keyIsExist(String tableName, String idKey){
        String mapValue=keyValueMapper.getValueBykey(tableName);
        List<KeyValueDto> keyValueDtoList=JSONObject.parseArray(mapValue,KeyValueDto.class);
        if (!keyValueDtoList.isEmpty()){
            for (KeyValueDto keyValueDto:keyValueDtoList){
                if (keyValueDto.getId().equals(idKey)){
                    return true;
                }
            }

        }
        return false;
    }
}
