package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.dto.send.TaskListDTOSend;
import xyz.jaign.filemanage.entity.Task;

public interface TaskService {
    NormalSend addTask(Task task);

    NormalSend startTask(String taskid);

    TaskListDTOSend getAllTasks();

    NormalSend stopTask(String taskid);

    NormalSend deleteTask(String taskid);
}
