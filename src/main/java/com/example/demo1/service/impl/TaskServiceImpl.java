package com.example.demo1.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReUtil;
import com.example.demo1.config.DefaultSchedulingConfigurer;
import com.example.demo1.dao.TaskMapper;
import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.dto.send.TaskListDTOSend;
import com.example.demo1.entity.Task;
import com.example.demo1.enums.TaskOptType;
import com.example.demo1.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private DefaultSchedulingConfigurer defaultSchedulingConfigurer;
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public NormalSend addTask(Task task) {
        NormalSend send = new NormalSend();
        if (!FileUtil.exist(task.getFolder())) {
            send.setCode(-1);
            send.setMsg("目录不存在，请检查目录是否填写正确。");
            return send;
        }
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
        if (defaultSchedulingConfigurer.hasTask(taskid)) {
            send.setCode(-2);
            send.setMsg("任务正在运行");
            return send;
        }

        int opt = task.getOpt();
        if (opt == TaskOptType.删除.value()) {
            startDelTask(task);
        } else if (opt == TaskOptType.复制.value()) {
            startCopyTask(task);
        } else if (opt == TaskOptType.移动.value()) {
            startMoveTask(task);
        } else if (opt == TaskOptType.重命名.value()) {
            startRenameTask(task);
        }
        send.setCode(0);
        send.setMsg("任务启动成功");
        return send;
    }


    @Override
    public TaskListDTOSend getAllTasks() {

        TaskListDTOSend send = new TaskListDTOSend();

        ArrayList<TaskListDTOSend.TaskNode> nodes = new ArrayList<>();

        List<Task> tasks = taskMapper.selectAll();
        for (Task task : tasks) {
            TaskListDTOSend.TaskNode node = new TaskListDTOSend.TaskNode();
            node.setEnabled(defaultSchedulingConfigurer.hasTask(task.getTaskid()));
            node.setFolder(task.getFolder());
            node.setRegfile(task.getRegfile());
            node.setTaskid(task.getTaskid());
            node.setTarget(task.getTargetpath());
            node.setTaskname(task.getTaskname());
            node.setTasktype(task.getOpt());
            node.setCron(task.getCron());
            nodes.add(node);
        }

        send.setCode(0);
        send.setMsg("获取任务列表成功");
        send.setCount(nodes.size());
        send.setData(nodes);
        return send;

    }

    @Override
    public NormalSend stopTask(String taskid) {
        NormalSend send = new NormalSend();
        if (!defaultSchedulingConfigurer.hasTask(taskid)) {
            send.setCode(-1);
            send.setMsg("该任务没有运行");
        }
        defaultSchedulingConfigurer.cancelTriggerTask(taskid);
        send.setCode(0);
        send.setMsg("任务已停止");
        return send;
    }

    @Override
    public NormalSend deleteTask(String taskid) {
        NormalSend send = new NormalSend();
        //从数据库中删除
        if (defaultSchedulingConfigurer.hasTask(taskid)) {
            defaultSchedulingConfigurer.cancelTriggerTask(taskid);
        }
        taskMapper.deleteTaskByID(taskid);
        send.setCode(0);
        send.setMsg("任务已完全删除");
        return send;
    }

    private void startDelTask(Task task) {
        defaultSchedulingConfigurer.addTriggerTask(task.getTaskid(),
                new TriggerTask(
                        () -> {

                            //执行内容
                            File folder = new File(task.getFolder());
                            if (folder.exists()) {
                                File[] files = FileUtil.ls(folder.getPath());
                                for (File file : files) {
                                    if (ReUtil.isMatch(task.getRegfile(), file.getName())) {
                                        FileUtil.del(file);
                                    }
                                }
                            }

                        },
                        new CronTrigger(task.getCron())));
    }

    private void startCopyTask(Task task) {
        defaultSchedulingConfigurer.addTriggerTask(task.getTaskid(),
                new TriggerTask(
                        () -> {

                            //执行内容
                            File folder = new File(task.getFolder());
                            if (folder.exists()) {
                                File[] files = FileUtil.ls(folder.getPath());
                                for (File file : files) {
                                    if (ReUtil.isMatch(task.getRegfile(), file.getName())) {
                                        FileUtil.copy(file.getPath(), task.getTargetpath(), false);
                                    }
                                }
                            }

                        },
                        new CronTrigger(task.getCron())));
    }

    private void startMoveTask(Task task) {
        defaultSchedulingConfigurer.addTriggerTask(task.getTaskid(),
                new TriggerTask(
                        () -> {

                            //执行内容
                            File folder = new File(task.getFolder());
                            File dest = new File(task.getTargetpath());
                            FileUtil.mkdir(dest);
                            if (folder.exists()) {
                                File[] files = FileUtil.ls(folder.getPath());
                                for (File file : files) {
                                    if (ReUtil.isMatch(task.getRegfile(), file.getName())) {
                                        FileUtil.move(file, dest, false);
                                    }
                                }
                            }

                        },
                        new CronTrigger(task.getCron())));
    }

    private void startRenameTask(Task task) {
        //目标路径为新名字
        //文件正则直接为旧名字！！！！
        defaultSchedulingConfigurer.addTriggerTask(task.getTaskid(),
                new TriggerTask(
                        () -> {

                            //执行内容
                            File oldfile = new File(task.getFolder(), task.getRegfile());
                            String newname = task.getTargetpath();
                            if (oldfile.exists()) {
                                FileUtil.rename(oldfile, newname, false, true);
                            }

                        },
                        new CronTrigger(task.getCron())));
    }
}
