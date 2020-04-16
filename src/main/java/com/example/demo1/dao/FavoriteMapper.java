package com.example.demo1.dao;

import com.example.demo1.entity.Favorite;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteMapper {
    int insertFavorite(Favorite favorite);
    List<Favorite> selectAll();

    void deleteFavorite(int userid, String path);
}
