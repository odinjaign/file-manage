package xyz.jaign.filemanage.service.impl;

import xyz.jaign.filemanage.dao.FolderPasswordMapper;
import xyz.jaign.filemanage.dto.send.FolderPasswordDTOSend;
import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.entity.FolderPassword;
import xyz.jaign.filemanage.entity.User;
import xyz.jaign.filemanage.service.FolderPasswordDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class FolderPasswordDBServiceImpl implements FolderPasswordDBService {

    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    private FolderPasswordMapper folderPasswordMapper;


    @Override
    public NormalSend addRecord(String fodler, String password) {
        //todo: 校验是否存在
        String path = new File(fodler).getPath();

        NormalSend normalSend = new NormalSend();
        User nowUser = loginServiceImpl.getNowUser();
        int userId = nowUser.getId();

        FolderPassword selectRel = folderPasswordMapper.selectByFolder(userId, path);
        if (selectRel != null){
            normalSend.setCode(-1);
            normalSend.setMsg("路径已存在，添加失败");
            return normalSend;
        }
        //添加
        FolderPassword folderPassword = new FolderPassword();
        folderPassword.setUserid(userId);
        folderPassword.setFolderpath(path);
        folderPassword.setFolderpassword(password);
        folderPasswordMapper.insert(folderPassword);

        normalSend.setCode(0);
        normalSend.setMsg("添加成功");
        return normalSend;
    }

    @Override
    public FolderPasswordDTOSend getAll() {
        FolderPasswordDTOSend send = new FolderPasswordDTOSend();

        User nowUser = loginServiceImpl.getNowUser();
        int userId = nowUser.getId();

        List<FolderPassword> folderPasswords = folderPasswordMapper.selectAll(userId);
        send.setCode(0);
        send.setMsg("查询成功");
        ArrayList<FolderPasswordDTOSend.FolderPasswordNode> folderPasswordNodes = new ArrayList<>();
        for (FolderPassword folderPassword : folderPasswords) {
            FolderPasswordDTOSend.FolderPasswordNode node = new FolderPasswordDTOSend.FolderPasswordNode();
            node.setId(folderPasswordNodes.size());
            node.setPath(folderPassword.getFolderpath());
            node.setPassword(folderPassword.getFolderpassword());
            node.setStatus(folderPassword.getStatus());
            folderPasswordNodes.add(node);
        }
        send.setData(folderPasswordNodes);
        send.setCount(folderPasswordNodes.size());
        return send;
    }

    @Override
    public NormalSend changeStatus(String folder, int code) {
        NormalSend send = new NormalSend();
        String path = new File(folder).getPath();

        User nowUser = loginServiceImpl.getNowUser();
        int userId = nowUser.getId();

        FolderPassword selectRel = folderPasswordMapper.selectByFolder(userId, path);
        if (selectRel != null){
            //开始修改
            selectRel.setStatus(code);
            folderPasswordMapper.update(selectRel);
            send.setCode(0);
            send.setMsg("修改成功！");
            return send;
        }
        send.setCode(-1);
        send.setMsg("修改失败");
        return send;
    }

    @Override
    public String getPassword(String folder) {
        String path = new File(folder).getPath();
        User nowUser = loginServiceImpl.getNowUser();
        int userId = nowUser.getId();
        FolderPassword selectRel = folderPasswordMapper.selectByFolder(userId, path);
        if (selectRel == null || selectRel.getStatus() == 2){
            //密码不存在或者未启用
            return null;
        }
        return selectRel.getFolderpassword();
    }

    @Override
    public NormalSend modifyPassword(String folder, String password) {
        NormalSend send = new NormalSend();
        String path = new File(folder).getPath();

        User nowUser = loginServiceImpl.getNowUser();
        int userId = nowUser.getId();

        FolderPassword selectRel = folderPasswordMapper.selectByFolder(userId, path);
        if (selectRel != null){
            //开始修改
            selectRel.setFolderpassword(password);
            folderPasswordMapper.update(selectRel);
            send.setCode(0);
            send.setMsg("修改成功！");
            return send;
        }
        send.setCode(-1);
        send.setMsg("修改失败");
        return send;
    }

    @Override
    public NormalSend delete(String folder) {
        NormalSend normalSend = new NormalSend();
        //todo
        String path = new File(folder).getPath();
        User nowUser = loginServiceImpl.getNowUser();
        int userId = nowUser.getId();
        folderPasswordMapper.delete(userId,path);
        normalSend.setCode(0);
        normalSend.setMsg("删除成功！");
        return normalSend;
    }
}
