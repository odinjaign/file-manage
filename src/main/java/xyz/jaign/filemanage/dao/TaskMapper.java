package xyz.jaign.filemanage.dao;

import xyz.jaign.filemanage.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMapper {
    List<Task> selectAll();
    void insertTask(Task task);
    Integer getCount();
    Task selectTaskByID(String taskid);

    void deleteTaskByID(String taskid);
}
