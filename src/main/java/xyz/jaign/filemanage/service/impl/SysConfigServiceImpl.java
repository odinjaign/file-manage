package xyz.jaign.filemanage.service.impl;

import xyz.jaign.filemanage.dao.SysConfigMapper;
import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.dto.send.SysConfigDTOSend;
import xyz.jaign.filemanage.entity.SysConfig;
import xyz.jaign.filemanage.enums.SysConfigKey;
import xyz.jaign.filemanage.service.SysConfigService;
import xyz.jaign.filemanage.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysConfigServiceImpl implements SysConfigService {


    @Autowired
    private SysConfigMapper sysConfigMapper;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private LoginServiceImpl loginServiceImpl;


    private void updateSysConfig(){
        cacheUtil.delete("sys_config");
        List<SysConfig> sysConfigs = sysConfigMapper.selectSysConfigAll();
        cacheUtil.setList("sys_config",sysConfigs);
    }

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
        return getConfigStatusByKey(key.value());
    }

    public int getConfigStatusByKey(String key){
        List<SysConfig> allConfig = getAllConfig();
        for (SysConfig sysConfig : allConfig) {
            if (sysConfig.getConfigkey().equals(key)){
                return sysConfig.getConfigvalue();
            }
        }
        return 0;
    }

    @Override
    public NormalSend setConfigStatus(String key, boolean value) {
        //true 1/ false 2
        NormalSend send = new NormalSend();
        if(loginServiceImpl.getNowUser()==null|| !"admin".equals(loginServiceImpl.getNowUser().getUsername())){
            send.setCode(-1);
            send.setMsg("未登录管理用户，无法修改配置");
            return send;
        }
        SysConfig sysConfig = sysConfigMapper.selectSysConfigByKey(key);
        if (sysConfig == null){
            send.setCode(-1);
            send.setMsg("没有此设置项");
            return send;
        }
        sysConfig.setConfigvalue(value?1:2);
        sysConfigMapper.updateConfig(sysConfig);
        updateSysConfig();
        send.setCode(0);
        send.setMsg("修改"+sysConfig.getConfigkey()+"成功");
        return send;
    }

    @Override
    public SysConfigDTOSend getAllConfigByList() {
        SysConfigDTOSend send = new SysConfigDTOSend();
        List<SysConfig> allConfig = getAllConfig();
        SysConfigDTOSend.SysConfigData configData = new SysConfigDTOSend.SysConfigData();
        for (SysConfig config : allConfig) {
            switch (config.getConfigkey()) {
                case  "filehide":
                    configData.setFilehide(config.getConfigvalue()==1);
                    break;
                case  "typeview":
                    configData.setTypeview(config.getConfigvalue()==1);
                    break;
                case  "favorite":
                    configData.setFavorite(config.getConfigvalue()==1);
                    break;
                case  "optlogs":
                    configData.setOptlogs(config.getConfigvalue()==1);
                    break;
                case  "upload":
                    configData.setUpload(config.getConfigvalue()==1);
                    break;
                case  "syshide":
                    configData.setSyshide(config.getConfigvalue()==1);
                    break;
                default:
                    break;
            }

        }
        send.setCode(0);
        send.setMsg("获取信息成功");
        send.setData(configData);
        return send;
    }

    @Override
    public boolean get(String key) {
        int status = getConfigStatusByKey(key);
        return status == 1;
    }


}
