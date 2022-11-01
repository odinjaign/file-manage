package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.entity.UserConfig;
import xyz.jaign.filemanage.enums.FolderPasswordType;

public interface UserConfigService {

    UserConfig getUserConfig();
    FolderPasswordType folderPasswordStatus();

    NormalSend isLogin();

    NormalSend passwordChangeStatus(int status);
}
