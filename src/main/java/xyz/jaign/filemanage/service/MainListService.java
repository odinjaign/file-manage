package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.NormalSend;

public interface MainListService {
    void initCacheList();

    String getNowUserRootFolder();

    NormalSend enterfolder(String folder,boolean isEnter);

    NormalSend returnlast();

    void updateCache();

    NormalSend enterfolderByPassword(String folder, String password);
}
