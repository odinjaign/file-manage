package com.example.demo1.controller;

import com.example.demo1.dto.send.FavoriteListDTOSend;
import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.service.impl.FavoriteServiceImpl;
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
