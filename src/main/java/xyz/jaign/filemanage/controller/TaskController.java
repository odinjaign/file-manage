package xyz.jaign.filemanage.controller;

import xyz.jaign.filemanage.config.DefaultSchedulingConfigurer;
import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.dto.send.TaskListDTOSend;
import xyz.jaign.filemanage.entity.Task;
import xyz.jaign.filemanage.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("task")
public class TaskController {

    @Resource
    private DefaultSchedulingConfigurer defaultSchedulingConfigurer;
    @Autowired
    private TaskServiceImpl taskServiceImpl;


    @PostMapping("addTask")
    public NormalSend addTask(Task task){
        return taskServiceImpl.addTask(task);
    }

    @PostMapping("startTask")
    public NormalSend startTask(String taskid){
        return taskServiceImpl.startTask(taskid);
    }

    @GetMapping("taskStatus")
    public boolean exist(String taskid){
        return defaultSchedulingConfigurer.hasTask(taskid);
    }

    @PostMapping("list")
    public TaskListDTOSend list(){
        return taskServiceImpl.getAllTasks();
    }

    @PostMapping("delTask")
    public NormalSend deleteTask(String taskid){
        return taskServiceImpl.deleteTask(taskid);
    }

    @PostMapping("stopTask")
    public NormalSend stopTask(String taskid){
        return taskServiceImpl.stopTask(taskid);
    }
}