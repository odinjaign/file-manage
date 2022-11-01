package xyz.jaign.filemanage.service.impl;

import xyz.jaign.filemanage.dao.UserConfigMapper;
import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.entity.User;
import xyz.jaign.filemanage.entity.UserConfig;
import xyz.jaign.filemanage.enums.FolderPasswordType;
import xyz.jaign.filemanage.enums.Usertype;
import xyz.jaign.filemanage.service.UserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConfigServiceImpl implements UserConfigService {


    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    private UserConfigMapper userConfigMapper;

    @Override
    public UserConfig getUserConfig() {
        User nowUser = loginServiceImpl.getNowUser();
        if (nowUser == null) {
            return null;//用户未登录
        }
        return userConfigMapper.selectUserConfigByUserid(nowUser.getId());
    }

    @Override
    public FolderPasswordType folderPasswordStatus() {
        UserConfig config = getUserConfig();
        if (config == null) {
            return null;
        } else {
            switch (config.getPasswordtype()) {
                case 0:
                    return FolderPasswordType.关闭;
                case 1:
                    return FolderPasswordType.文本密码;
                case 2:
                    return FolderPasswordType.数据库密码;
                default:
                    return null;
            }
        }
    }

    @Override
    public NormalSend isLogin() {
        NormalSend normalSend = new NormalSend();
        User nowUser = loginServiceImpl.getNowUser();
        if (nowUser == null) {
            normalSend.setCode(-1);
            normalSend.setMsg("用户未登录");
        } else if (nowUser.getUsertype() == Usertype.管理用户.value()) {
            normalSend.setCode(1);
            normalSend.setMsg("管理用户[" + nowUser.getUsername() + "]已登录");
        } else {
            normalSend.setCode(0);
            normalSend.setMsg("用户[" + nowUser.getUsername() + "]已登录");
        }
        return normalSend;
    }

    @Override
    public NormalSend passwordChangeStatus(int status) {
        NormalSend normalSend = new NormalSend();
        UserConfig userConfig = getUserConfig();
        userConfig.setPasswordtype(status);
        userConfigMapper.updateUserConfig(userConfig);
        normalSend.setCode(0);
        normalSend.setMsg("修改成功！");
        return normalSend;
    }
}
