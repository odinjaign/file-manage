package xyz.jaign.filemanage.dao;

import xyz.jaign.filemanage.entity.ClassList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassListMapper {
    List<ClassList> selectAll(int userid);
    List<ClassList> selectByTypeAndUser(int type,int userid);
    int insertClassList(ClassList classList);

    int updateClassList(ClassList classList);

    int deleteClassList(ClassList classList);
}
