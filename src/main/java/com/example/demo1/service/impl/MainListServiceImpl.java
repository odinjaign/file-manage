package com.example.demo1.service.impl;

import com.example.demo1.dao.UserConfigMapper;
import com.example.demo1.dto.send.MainListDTOSend;
import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.entity.User;
import com.example.demo1.entity.UserConfig;
import com.example.demo1.enums.FolderPasswordType;
import com.example.demo1.service.MainListService;
import com.example.demo1.util.CacheUtil;
import com.example.demo1.util.FileOptUtil;
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
    @Autowired
    private UserConfigServiceImpl userConfigServiceImpl;


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
        return userConfig.getMainfolder();
    }

    @Override
    public NormalSend enterfolder(String folder,boolean isEnter) {
        NormalSend send = new NormalSend();
        // 判断文件夹加密
        // 文本密码
        FolderPasswordType passwordStatus = userConfigServiceImpl.folderPasswordStatus();
        switch (passwordStatus) {
            case 关闭:
                break;
            case 文本密码:
                String password = FileOptUtil.getFolderTextPassword(folder);
                if (password != null && isEnter){
                    //进入文件夹操作才判断密码
                    //文件夹被加密
                    send.setCode(-1);
                    send.setMsg("进入文件夹失败，文件夹被加密");
                    return send;
                }
                break;
            case 数据库密码:
                //todo 数据库密码
        }

        //进入文件夹
        cacheUtil.getAndSet("pwd", folder);
        List<MainListDTOSend.FileNode> fileList = fileListServiceImpl.getFileList(folder);
        cacheUtil.delete("main_list");
        cacheUtil.setList("main_list", fileList);
        send.setCode(0);
        send.setMsg("进入文件夹成功");
        return send;
    }

    @Override
    public NormalSend returnlast() {
        NormalSend send = new NormalSend();
        //得到用户根目录
        File rootFolder = new File(getNowUserRootFolder());
        File pwd = new File(cacheUtil.get("pwd"));
        if (rootFolder.equals(pwd)) {
            //当前目录为根目录
            send.setCode(-1);
            send.setMsg("当前目录为用户跟目录");
            return send;
        } else {
            NormalSend enterfolder = enterfolder(pwd.getParent(),false);
            return enterfolder;
        }
    }

    @Override
    public void updateCache(){
        enterfolder(cacheUtil.get("pwd"),false);
    }

    @Override
    public NormalSend enterfolderByPassword(String folder, String password) {
        NormalSend send = new NormalSend();
        String realPassword = FileOptUtil.getFolderTextPassword(folder);
        if (password.equals(realPassword)){
            //密码正确
            //进入文件夹
            cacheUtil.getAndSet("pwd", folder);
            List<MainListDTOSend.FileNode> fileList = fileListServiceImpl.getFileList(folder);
            cacheUtil.delete("main_list");
            cacheUtil.setList("main_list", fileList);
            send.setCode(0);
            send.setMsg("进入文件夹成功");
            return send;
        }
        send.setCode(-2);
        send.setMsg("密码错误");
        return send;
    }

}
