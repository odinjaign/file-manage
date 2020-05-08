package com.example.demo1.controller;

import com.example.demo1.config.DefaultSchedulingConfigurer;
import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.entity.Task;
import com.example.demo1.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
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


    //添加定时器
    @RequestMapping("add")
    public NormalSend add() {
        NormalSend send = new NormalSend();
        defaultSchedulingConfigurer.addTriggerTask("task",
                new TriggerTask(
                        () -> System.out.println("hello"),
                        new CronTrigger("0/5 * * * * ? ")));//5秒执行一次
        send.setCode(0);
        send.setMsg("任务[task]创建完成");
        return send;
    }
    //删除定时器
    @RequestMapping("del")
    public NormalSend del() {
        NormalSend send = new NormalSend();
        defaultSchedulingConfigurer.cancelTriggerTask("task");
        send.setCode(0);
        send.setMsg("任务[task]已删除");
        return send;
    }

    @PostMapping("addTask")
    public NormalSend test(Task task){
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
}