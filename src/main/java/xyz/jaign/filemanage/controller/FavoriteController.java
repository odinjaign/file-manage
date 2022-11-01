package xyz.jaign.filemanage.controller;

import xyz.jaign.filemanage.dto.send.FavoriteListDTOSend;
import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.service.impl.FavoriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * 收藏模块
 */
@RestController
@RequestMapping("favorite")
public class FavoriteController {


    @Autowired
    private FavoriteServiceImpl favoriteServiceImpl;

    @PostMapping("add")
    public NormalSend addFavorite(String path){
        return favoriteServiceImpl.addFavorite(new File(path));
    }

    @PostMapping("list")
    public FavoriteListDTOSend list(){
        return favoriteServiceImpl.getFavoriteList();
    }

    @PostMapping("remove")
    public NormalSend remove(String path){
        return favoriteServiceImpl.removeFavorite(new File(path));
    }

}
