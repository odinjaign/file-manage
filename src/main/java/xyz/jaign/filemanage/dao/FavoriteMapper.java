package xyz.jaign.filemanage.dao;

import xyz.jaign.filemanage.entity.Favorite;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteMapper {
    int insertFavorite(Favorite favorite);
    List<Favorite> selectAll();

    void deleteFavorite(int userid, String path);
    Favorite selectFavoriteByPath(int userid, String path);
}
