package com.example.demo1.dao;

import com.example.demo1.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMapper {
    List<Task> selectAll();
    void insertTask(Task task);
    Integer getCount();
    Task selectTaskByID(String taskid);
}
