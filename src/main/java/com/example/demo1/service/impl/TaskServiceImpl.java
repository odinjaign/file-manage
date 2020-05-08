package com.example.demo1.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReUtil;
import com.example.demo1.config.DefaultSchedulingConfigurer;
import com.example.demo1.dao.TaskMapper;
import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.entity.Task;
import com.example.demo1.enums.TaskOptType;
import com.example.demo1.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private DefaultSchedulingConfigurer defaultSchedulingConfigurer;
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public NormalSend addTask(Task task) {
        NormalSend send = new NormalSend();
        //获取id
        Integer count = taskMapper.getCount();
        String taskid = "task-" + (count + 1);
        task.setTaskid(taskid);
        taskMapper.insertTask(task);
        send.setCode(0);
        send.setMsg("任务添加成功");
        return send;
    }

    @Override
    public NormalSend startTask(String taskid) {
        NormalSend send = new NormalSend();
        Task task = taskMapper.selectTaskByID(taskid);
        if (task == null) {
            send.setCode(-1);
            send.setMsg("任务不存在");
            return send;
        }

        int opt = task.getOpt();
        if (opt == TaskOptType.删除.value()) {
            dealAddDelTask(task);
        }
        send.setCode(0);
        send.setMsg("任务启动成功");
        return send;
    }

    private void dealAddDelTask(Task task) {
        defaultSchedulingConfigurer.addTriggerTask(task.getTaskid(),
                new TriggerTask(
                        () -> {

                            //执行内容
                            File folder = new File(task.getFolder());
                            if (folder.exists()){
                                File[] files = FileUtil.ls(folder.getPath());
                                for (File file : files) {
                                    if (ReUtil.isMatch(task.getRegfile(),file.getName())) {
                                        file.delete();
                                    }
                                }
                            }

                        },
                        new CronTrigger(task.getCron())));
    }
}
