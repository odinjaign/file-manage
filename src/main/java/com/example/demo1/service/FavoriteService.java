package com.example.demo1.service;

import com.example.demo1.dto.send.FavoriteListDTOSend;
import com.example.demo1.dto.send.NormalSend;

import java.io.File;

public interface FavoriteService {
    NormalSend addFavorite(File file);

    FavoriteListDTOSend getFavoriteList();

    void updateCacheList();

    NormalSend removeFavorite(File file);

    boolean isFavorite(File file);
}
