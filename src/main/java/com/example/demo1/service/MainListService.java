package com.example.demo1.service;

public interface MainListService {
    void initCacheList();

    String getNowUserRootFolder();

    boolean enterfolder(String folder);

    boolean returnlast();

    void updateCache();
}
