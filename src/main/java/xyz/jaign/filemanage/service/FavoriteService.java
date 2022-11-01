package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.FavoriteListDTOSend;
import xyz.jaign.filemanage.dto.send.NormalSend;

import java.io.File;

public interface FavoriteService {
    NormalSend addFavorite(File file);

    FavoriteListDTOSend getFavoriteList();

    void updateCacheList();

    NormalSend removeFavorite(File file);

    boolean isFavorite(File file);
}
