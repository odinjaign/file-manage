package xyz.jaign.filemanage.service.impl;

import cn.hutool.core.io.FileUtil;
import xyz.jaign.filemanage.dao.ClassListMapper;
import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.entity.ClassList;
import xyz.jaign.filemanage.entity.User;
import xyz.jaign.filemanage.enums.ClassType;
import xyz.jaign.filemanage.service.ClassManageService;
import xyz.jaign.filemanage.util.CacheUtil;
import xyz.jaign.filemanage.util.FileOptUtil;
import xyz.jaign.filemanage.util.StringOptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ClassManageServiceImpl implements ClassManageService {


    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    private MainListServiceImpl mainListServiceImpl;
    @Autowired
    private ClassListMapper classListMapper;
    @Autowired
    private CacheUtil cacheUtil;


    @Override
    public NormalSend add(String path, String type, int num, String exts) {
        NormalSend send = new NormalSend();
        User nowUser = loginServiceImpl.getNowUser();
        int userid = nowUser.getId();
        ClassType classType = typeOf(type);
        if (classType == null) {
            //出错
            send.setCode(-1);
            send.setMsg("类型错误");
            return send;
        }
        File file = new File(path);
        if (!file.exists()) {
            send.setCode(-2);
            send.setMsg("路径不存在");
            return send;
        }
        if (file.isFile()) {
            send.setCode(-3);
            send.setMsg("路径不是目录");
            return send;
        }
        if (!FileOptUtil.fileInFolder(file, mainListServiceImpl.getNowUserRootFolder())) {
            send.setCode(-4);
            send.setMsg("路径不再您的权限范围内");
            return send;
        }

        String checkFolder = file.getPath();
        int checkLength = num;
        ClassList classList = new ClassList();
        classList.setUserid(userid);
        classList.setClasstype(classType.value());
        classList.setCheckfolder(checkFolder);
        classList.setChecklength(checkLength);
        classList.setCheckexts(exts);
        classListMapper.insertClassList(classList);
        send.setCode(0);
        send.setMsg("索引" + type + "类型路径成功");
        return send;
    }

    @Override
    public NormalSend modify(String path, int type, int num, String exts) {
        NormalSend send = new NormalSend();
        User nowUser = loginServiceImpl.getNowUser();
        int userid = nowUser.getId();
        File file = new File(path);
        if (!file.exists()) {
            send.setCode(-2);
            send.setMsg("路径不存在");
            return send;
        }
        if (file.isFile()) {
            send.setCode(-3);
            send.setMsg("路径不是目录");
            return send;
        }
        if (!FileOptUtil.fileInFolder(file, mainListServiceImpl.getNowUserRootFolder())) {
            send.setCode(-4);
            send.setMsg("路径不再您的权限范围内");
            return send;
        }
        String folder = file.getPath();
        ClassList classList = new ClassList();
        classList.setUserid(userid);
        classList.setCheckfolder(folder);
        classList.setClasstype(type);

        classList.setChecklength(num);
        classList.setCheckexts(exts);

        classListMapper.updateClassList(classList);
        send.setCode(0);
        send.setMsg("修改成功");
        return send;
    }

    @Override
    public NormalSend delete(String path, int type) {
        NormalSend send = new NormalSend();
        User nowUser = loginServiceImpl.getNowUser();
        int userid = nowUser.getId();
        File file = new File(path);

        // 移出索引，不校验
//        if (!file.exists()) {
//            send.setCode(-2);
//            send.setMsg("路径不存在");
//            return send;
//        }
//        if (file.isFile()) {
//            send.setCode(-3);
//            send.setMsg("路径不是目录");
//            return send;
//        }
//        if (!FileOptUtil.fileInFolder(file, mainListServiceImpl.getNowUserRootFolder())) {
//            send.setCode(-4);
//            send.setMsg("路径不再您的权限范围内");
//            return send;
//        }
        ClassList classList = new ClassList();
        classList.setCheckfolder(file.getPath());
        classList.setClasstype(type);
        classList.setUserid(userid);

        classListMapper.deleteClassList(classList);
        send.setCode(0);
        send.setMsg("删除成功");
        return send;
    }

    @Override
    public List<File> getFile(ClassList list) {
        List<File> files = new ArrayList<>();
        File folder = new File(list.getCheckfolder());
        //索引目录被删除，不存在
        if (!FileUtil.exist(folder)){
            return files;
        }
        int num = list.getChecklength();
        String exts = list.getCheckexts();
        List<File> allFiles = FileOptUtil.lsFile2Num(new ArrayList<File>(), folder, num);
        Iterator<File> iterator = allFiles.iterator();
        while (iterator.hasNext()) {
            File file = iterator.next();
            String ext = StringOptUtil.fileExt(file.getName());
            if (!StringOptUtil.extInExts(ext, exts)) {
                iterator.remove();
            }
        }
        return allFiles;
    }

    @Override
    public List<ClassList> getUserItems() {
        User nowUser = loginServiceImpl.getNowUser();
        int userid = nowUser.getId();
        return classListMapper.selectAll(userid);
    }

    @Override
    public NormalSend delCache() {
        NormalSend send = new NormalSend();

        cacheUtil.delete("img_list");
        cacheUtil.delete("music_list");
        cacheUtil.delete("movie_list");
        cacheUtil.delete("document_list");

        send.setCode(0);
        send.setMsg("清除缓存完成");
        return send;
    }

    private ClassType typeOf(String type) {
        if ("音乐".equals(type)) {
            return ClassType.音乐;
        } else if ("视频".equals(type)) {
            return ClassType.视频;
        } else if ("文档".equals(type)) {
            return ClassType.文档;
        } else if ("图片".equals(type)) {
            return ClassType.图片;
        } else {
            return null;
        }

    }
}
