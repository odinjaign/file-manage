package xyz.jaign.filemanage.dao;

import xyz.jaign.filemanage.entity.UserConfig;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConfigMapper {
    void insertUserConfig(UserConfig userConfig);
    UserConfig selectUserConfigByUserid(int userid);
    void updateUserConfig(UserConfig userConfig);
}
