package com.example.demo1.service;

import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.dto.send.SysConfigDTOSend;
import com.example.demo1.entity.SysConfig;
import com.example.demo1.enums.SysConfigKey;

import java.util.List;

public interface SysConfigService {
    List<SysConfig> getAllConfig();

    int getConfigStatusByKey(SysConfigKey key);

    NormalSend setConfigStatus(String key, boolean value);

    SysConfigDTOSend getAllConfigByList();

    boolean get(String key);
}
