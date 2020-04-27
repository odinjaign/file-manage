package com.example.demo1.service;

import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.entity.UserConfig;
import com.example.demo1.enums.FolderPasswordType;

public interface UserConfigService {

    UserConfig getUserConfig();
    FolderPasswordType folderPasswordStatus();

    NormalSend isLogin();

    NormalSend passwordChangeStatus(int status);
}
