package xyz.jaign.filemanage.dao;

import xyz.jaign.filemanage.entity.SysConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysConfigMapper {
    List<SysConfig> selectSysConfigAll();
    SysConfig selectSysConfigByKey(String key);

    void updateConfig(SysConfig sysConfig);
}
