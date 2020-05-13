package com.example.demo1.service;

import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.dto.send.TaskListDTOSend;
import com.example.demo1.entity.Task;

public interface TaskService {
    NormalSend addTask(Task task);

    NormalSend startTask(String taskid);

    TaskListDTOSend getAllTasks();

    NormalSend stopTask(String taskid);

    NormalSend deleteTask(String taskid);
}