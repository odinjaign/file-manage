package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.dto.send.SysConfigDTOSend;
import xyz.jaign.filemanage.entity.SysConfig;
import xyz.jaign.filemanage.enums.SysConfigKey;

import java.util.List;

public interface SysConfigService {
    List<SysConfig> getAllConfig();

    int getConfigStatusByKey(SysConfigKey key);

    NormalSend setConfigStatus(String key, boolean value);

    SysConfigDTOSend getAllConfigByList();

    boolean get(String key);
}
