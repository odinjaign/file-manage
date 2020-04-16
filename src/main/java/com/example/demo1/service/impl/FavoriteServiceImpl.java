package com.example.demo1.service.impl;

import com.example.demo1.dao.FavoriteMapper;
import com.example.demo1.dto.send.FavoriteListDTOSend;
import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.entity.Favorite;
import com.example.demo1.entity.User;
import com.example.demo1.enums.Favoritetype;
import com.example.demo1.service.FavoriteService;
import com.example.demo1.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {


    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private MainListServiceImpl mainListServiceImpl;
    @Autowired
    private CacheUtil cacheUtil;


    @Override
    public NormalSend addFavorite(File file) {
        NormalSend send = new NormalSend();
        Favorite favorite = new Favorite();
        User nowUser = loginServiceImpl.getNowUser();
        if (nowUser == null){
            send.setCode(-1);
            send.setMsg("用户未登录");
            return send;
        }

        if (!file.exists()){
            send.setCode(-2);
            send.setMsg("需要收藏的文件不存在");
            return send;
        }

        int userid = nowUser.getId();
        String filepath = file.getPath();
        favorite.setUserid(userid);
        favorite.setFavoritepath(filepath);
        if (file.isFile()){
            favorite.setFavoritetype(Favoritetype.文件.value());
        }else {
            favorite.setFavoritetype(Favoritetype.文件夹.value());
        }
        favoriteMapper.insertFavorite(favorite);
        send.setCode(0);
        send.setMsg("添加收藏成功！");
        mainListServiceImpl.updateCache();
        updateCacheList();
        return send;
    }

    @Override
    public FavoriteListDTOSend getFavoriteList() {
        FavoriteListDTOSend send = new FavoriteListDTOSend();
        if (!cacheUtil.hasKey("favorite_list")){
            updateCacheList();
        }
        List<FavoriteListDTOSend.FavoriteNode> favoriteList = cacheUtil.getList("favorite_list", FavoriteListDTOSend.FavoriteNode.class);
        send.setCode(0);
        send.setMsg("获取成功");
        send.setCount(favoriteList.size());
        send.setData(favoriteList);
        return send;
    }

    @Override
    public void updateCacheList() {
        if (cacheUtil.hasKey("favorite_list")){
            cacheUtil.delete("favorite_list");
        }
        //从数据库里更新
        List<Favorite> favorites = favoriteMapper.selectAll();
        ArrayList<FavoriteListDTOSend.FavoriteNode> favoriteNodes = new ArrayList<>();
        for (Favorite favorite : favorites) {
            //转换
            //todo:检查文件不存在要通知取消收藏
            String path =favorite.getFavoritepath();
            FavoriteListDTOSend.FavoriteNode favoriteNode = new FavoriteListDTOSend.FavoriteNode();
            if (favorite.getFavoritetype() == Favoritetype.文件夹.value()){
                favoriteNode.setFolder(true);
            }else {
                favoriteNode.setFolder(false);
            }
            favoriteNode.setPath(path);
            favoriteNode.setName(new File(path).getName());
            favoriteNode.setTime(favorite.getFavoritetime());
            favoriteNodes.add(favoriteNode);
        }
        cacheUtil.setList("favorite_list",favoriteNodes);
    }

    @Override
    public NormalSend removeFavorite(File file) {
        NormalSend send = new NormalSend();
        String path = file.getPath();
        int userid = loginServiceImpl.getNowUser().getId();
        favoriteMapper.deleteFavorite(userid,path);
        send.setCode(0);
        send.setMsg("移除收藏成功！");
        updateCacheList();
        return send;
    }
}
