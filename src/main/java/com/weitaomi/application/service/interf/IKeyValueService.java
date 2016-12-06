package com.weitaomi.application.service.interf;

import com.weitaomi.application.model.dto.KeyValueDto;

import java.util.List;

/**
 * Created by Administrator on 2016/11/5.
 */
public interface IKeyValueService {
    public List<KeyValueDto> getKeyValueDtoList(String mapKey,String id);
    boolean keyIsExist(String tableName, String idKey);

    boolean valueIsExist(String tableName, String idKey);
}
