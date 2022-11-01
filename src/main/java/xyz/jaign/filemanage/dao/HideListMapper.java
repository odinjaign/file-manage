package xyz.jaign.filemanage.dao;

import xyz.jaign.filemanage.entity.HideList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HideListMapper {
    void insert(HideList hideList);
    List<HideList> selectAll();
    HideList selectById(int id);
    List<HideList> selectByType(int userid,int type);
    HideList selectContentAndType(HideList hideList);
    void deleteById(int id);
}
