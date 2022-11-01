package xyz.jaign.filemanage.service.impl;

import xyz.jaign.filemanage.dao.UserConfigMapper;
import xyz.jaign.filemanage.dto.send.MainListDTOSend;
import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.entity.User;
import xyz.jaign.filemanage.entity.UserConfig;
import xyz.jaign.filemanage.enums.FolderPasswordType;
import xyz.jaign.filemanage.service.MainListService;
import xyz.jaign.filemanage.util.CacheUtil;
import xyz.jaign.filemanage.util.FileOptUtil;
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
    @Autowired
    private FolderPasswordDBServiceImpl folderPasswordDBServiceImpl;


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
                    send.setMsg("进入文件夹失败，文件夹被加密[TXT]");
                    return send;
                }
                break;
            case 数据库密码:
                //todo 数据库密码
                String passwordDB = folderPasswordDBServiceImpl.getPassword(folder);
                if (passwordDB != null && isEnter){
                    //进入文件夹操作才判断密码
                    //文件夹被加密
                    send.setCode(-1);
                    send.setMsg("进入文件夹失败，文件夹被加密[DB]");
                    return send;
                }
                break;
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
            send.setMsg("当前目录为用户根目录");
            return send;
        } else if(pwd.getParent() == null){
            //当前目录为根目录
            send.setCode(-2);
            send.setMsg("当前目录为根目录");
            return send;
        }else {
            return enterfolder(pwd.getParent(),false);
        }
    }

    @Override
    public void updateCache(){
        enterfolder(cacheUtil.get("pwd"),false);
    }

    @Override
    public NormalSend enterfolderByPassword(String folder, String password) {
        NormalSend send = new NormalSend();
        // 判断文件夹加密
        // 文本密码
        FolderPasswordType passwordStatus = userConfigServiceImpl.folderPasswordStatus();
        String realPassword = null;
        switch (passwordStatus) {
            case 关闭:
                break;
            case 文本密码:
                realPassword= FileOptUtil.getFolderTextPassword(folder);
                break;
            case 数据库密码:
                //todo 数据库密码
                realPassword = folderPasswordDBServiceImpl.getPassword(folder);
                break;
        }


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
