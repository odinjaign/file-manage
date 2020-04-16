package com.example.demo1.service.impl;

import com.example.demo1.dao.SysConfigMapper;
import com.example.demo1.entity.SysConfig;
import com.example.demo1.enums.SysConfigKey;
import com.example.demo1.service.SysConfigService;
import com.example.demo1.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysConfigServiceImpl implements SysConfigService {


    @Autowired
    private SysConfigMapper sysConfigMapper;
    @Autowired
    private CacheUtil cacheUtil;


    @Override
    public List<SysConfig> getAllConfig() {
        //判断缓存是否有
        if (cacheUtil.hasKey("sys_config")) {
            List<SysConfig> configs = cacheUtil.getList("sys_config", SysConfig.class);
            return configs;
        }else {
            List<SysConfig> sysConfigs = sysConfigMapper.selectSysConfigAll();
            cacheUtil.setList("sys_config",sysConfigs);
            return sysConfigs;
        }
    }

    @Override
    public int getConfigStatusByKey(SysConfigKey key){
        List<SysConfig> allConfig = getAllConfig();
        for (SysConfig sysConfig : allConfig) {
            if (sysConfig.getConfigkey().equals(key.value())){
                return sysConfig.getConfigvalue();
            }
        }
        return 0;
    }
}
