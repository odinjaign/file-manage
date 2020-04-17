package com.example.demo1.dao;

import com.example.demo1.entity.ClassList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassListMapper {
    List<ClassList> selectAll();
    int insertClassList(ClassList classList);

    int updateClassList(ClassList classList);

    int deleteClassList(ClassList classList);
}
