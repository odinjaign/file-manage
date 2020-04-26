package com.example.demo1.service;

import com.example.demo1.dto.send.NormalSend;

public interface MainListService {
    void initCacheList();

    String getNowUserRootFolder();

    NormalSend enterfolder(String folder,boolean isEnter);

    NormalSend returnlast();

    void updateCache();

    NormalSend enterfolderByPassword(String folder, String password);
}
