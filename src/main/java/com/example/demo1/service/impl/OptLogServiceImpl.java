package com.example.demo1.service.impl;

import com.example.demo1.dao.FileOptLogsMapper;
import com.example.demo1.dto.send.OptLogsDTOSend;
import com.example.demo1.entity.FileOptLogs;
import com.example.demo1.entity.User;
import com.example.demo1.service.OptLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OptLogServiceImpl implements OptLogService {


    @Autowired
    private FileOptLogsMapper fileOptLogsMapper;
    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    private SysConfigServiceImpl sysConfigServiceImpl;


    @Override
    public boolean logCopy(File src, File dst) {
        FileOptLogs logs = new FileOptLogs();
        User nowUser = loginServiceImpl.getNowUser();
        if (nowUser == null) {
            return false;
        }
        Integer maxIndex = fileOptLogsMapper.selectMaxIndex(nowUser.getId());
        int logindex = maxIndex == null ? 1 : maxIndex + 1;
        logs.setLogindex(logindex);
        logs.setUserid(nowUser.getId());
        logs.setLognote("文件" + src.getName() + "复制到" + dst.getPath());
        logs.setLogpath(src.getPath());
        logs.setLogpathext(dst.getPath());
        logs.setLogtype("复制");
        logs.setLogtime(new Date());
        fileOptLogsMapper.insertLog(logs);
        return true;
    }

    @Override
    public boolean logMove(File src, File dst) {
        FileOptLogs logs = new FileOptLogs();
        User nowUser = loginServiceImpl.getNowUser();
        if (nowUser == null) {
            return false;
        }
        Integer maxIndex = fileOptLogsMapper.selectMaxIndex(nowUser.getId());
        int logindex = maxIndex == null ? 1 : maxIndex + 1;
        logs.setLogindex(logindex);
        logs.setUserid(nowUser.getId());
        logs.setLognote("文件" + src.getName() + "移动到" + dst.getPath());
        logs.setLogpath(src.getPath());
        logs.setLogpathext(dst.getPath());
        logs.setLogtype("移动");
        logs.setLogtime(new Date());
        fileOptLogsMapper.insertLog(logs);
        return true;
    }

    @Override
    public boolean logRename(File src, String newname) {
        FileOptLogs logs = new FileOptLogs();
        User nowUser = loginServiceImpl.getNowUser();
        if (nowUser == null) {
            return false;
        }
        Integer maxIndex = fileOptLogsMapper.selectMaxIndex(nowUser.getId());
        int logindex = maxIndex == null ? 1 : maxIndex + 1;
        logs.setLogindex(logindex);
        logs.setUserid(nowUser.getId());
        logs.setLognote("文件" + src.getName() + "重命名为" + newname);
        logs.setLogpath(src.getPath());
        logs.setLogpathext(new File(src.getParent(), newname).getPath());
        logs.setLogtype("重命名");
        logs.setLogtime(new Date());
        fileOptLogsMapper.insertLog(logs);
        return true;
    }

    @Override
    public boolean logDel(File file) {
        FileOptLogs logs = new FileOptLogs();
        User nowUser = loginServiceImpl.getNowUser();
        if (nowUser == null) {
            return false;
        }
        Integer maxIndex = fileOptLogsMapper.selectMaxIndex(nowUser.getId());
        int logindex = maxIndex == null ? 1 : maxIndex + 1;
        logs.setLogindex(logindex);
        logs.setUserid(nowUser.getId());
        logs.setLognote("文件" + file.getName() + "被删除");
        logs.setLogpath(file.getPath());
        logs.setLogpathext("");
        logs.setLogtype("删除");
        logs.setLogtime(new Date());
        fileOptLogsMapper.insertLog(logs);
        return false;
    }

    @Override
    public OptLogsDTOSend getNowUserLogs() {
        OptLogsDTOSend send = new OptLogsDTOSend();
        ArrayList<OptLogsDTOSend.LogNode> data = new ArrayList<>();
        User nowUser = loginServiceImpl.getNowUser();
        if (!sysConfigServiceImpl.getAllConfigByList().getData().isOptlogs()) {
            //日志功能未开启
            send.setCode(-1);
            send.setMsg("日志功能未启用");
            return send;
        }
        if (nowUser == null) {
            send.setCode(-2);
            send.setMsg("用户未登录");
            return send;
        }
        List<FileOptLogs> optLogs = fileOptLogsMapper.selectLogsByUserid(nowUser.getId());
        for (FileOptLogs log : optLogs) {
            OptLogsDTOSend.LogNode logNode = new OptLogsDTOSend.LogNode();
            logNode.setIndex(log.getLogindex());
            logNode.setDst(log.getLogpathext());
            logNode.setSrc(log.getLogpath());
            logNode.setTime(log.getLogtime());
            logNode.setNote(log.getLognote());
            logNode.setType(log.getLogtype());
            data.add(logNode);
        }
        send.setCode(0);
        send.setCount(data.size());
        send.setMsg("获取日志用户列表成功");
        send.setData(data);
        return send;
    }
}
