package com.example.demo1.service.impl;

import com.example.demo1.dao.UserConfigMapper;
import com.example.demo1.dto.send.MainListDTOSend;
import com.example.demo1.entity.User;
import com.example.demo1.entity.UserConfig;
import com.example.demo1.service.MainListService;
import com.example.demo1.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class MainListServiceImpl implements MainListService {

    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private UserConfigMapper userConfigMapper;
    @Autowired
    private FileListServiceImpl fileListServiceImpl;


    @Override
    public void initCacheList() {
        //获取当前主目录
        //得到当前用户
        String mainfolder = getNowUserRootFolder();
        //调用文件服务，生成需要的文件列表
        List<MainListDTOSend.FileNode> fileList = fileListServiceImpl.getFileList(mainfolder);
        cacheUtil.set("pwd", mainfolder);
        cacheUtil.setList("main_list", fileList);
    }

    @Override
    public String getNowUserRootFolder() {
        //得到当前用户
        User user = cacheUtil.getCacheO("user", User.class);
        int userid = user.getId();
        //得到当前用户主目录
        UserConfig userConfig = userConfigMapper.selectUserConfigByUserid(userid);
        String mainfolder = userConfig.getMainfolder();
        return mainfolder;
    }

    @Override
    public boolean enterfolder(String folder) {
        //进入文件夹
        cacheUtil.getAndSet("pwd", folder);
        List<MainListDTOSend.FileNode> fileList = fileListServiceImpl.getFileList(folder);
        cacheUtil.delete("main_list");
        cacheUtil.setList("main_list", fileList);
        return true;
    }

    @Override
    public boolean returnlast() {
        //得到用户根目录
        File rootFolder = new File(getNowUserRootFolder());
        File pwd = new File(cacheUtil.get("pwd"));
        if (rootFolder.equals(pwd)) {
            //当前目录为根目录
            return false;
        } else {
            return enterfolder(pwd.getParent());
        }
    }

    @Override
    public void updateCache(){
        enterfolder(cacheUtil.get("pwd"));
    }

}
