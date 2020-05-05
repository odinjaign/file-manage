package com.example.demo1.service.impl;

import cn.hutool.core.util.ReUtil;
import com.example.demo1.dao.HideListMapper;
import com.example.demo1.dto.send.HideListDTOSend;
import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.entity.HideList;
import com.example.demo1.entity.User;
import com.example.demo1.service.HideManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HideManageServiceImpl implements HideManageService {


    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    private HideListMapper hideListMapper;

    @Override
    public NormalSend addRecord(String content, int type) {
        NormalSend send = new NormalSend();
        // 类型为路径要判断，todo
        String finalContent;
        if (type == 1){
            //路径
            File file = new File(content);
            if (!file.exists()) {
                send.setCode(-1);
                send.setMsg("路径不存在");
                return send;
            }
            finalContent = file.getPath();
        }else if(type == 2){
            //正则
            finalContent = content;
        }else {
            send.setCode(-2);
            send.setMsg("路径/正则类型不正确");
            return send;
        }
        //
        User nowUser = loginServiceImpl.getNowUser();
        int userId = nowUser.getId();
        HideList hideList = new HideList();

        hideList.setUserid(userId);
        hideList.setHidetime(new Date());
        hideList.setHidecontent(finalContent);
        hideList.setHidetype(type);

        // 判断添加的记录重复
        if (hideListMapper.selectContentAndType(hideList) != null){
            // 记录已经存在
            send.setCode(-3);
            send.setMsg("文件记录已存在");
            return send;
        }

        hideListMapper.insert(hideList);
        send.setCode(0);
        send.setMsg("添加记录成功");
        return send;
    }

    @Override
    public HideListDTOSend getAllData() {
        HideListDTOSend send = new HideListDTOSend();
        User nowUser = loginServiceImpl.getNowUser();
        int userId = nowUser.getId();
        // 查询路径记录
        List<HideList> hidePathList = hideListMapper.selectByType(userId, 1);
        //包装Path
        List<HideListDTOSend.HidePathNode> pathNodes = new ArrayList<>();
        for (HideList hideList : hidePathList) {
            HideListDTOSend.HidePathNode node = new HideListDTOSend.HidePathNode();
            node.setId(hideList.getId());
            node.setPath(new File(hideList.getHidecontent()).getPath());
            node.setType(new File(hideList.getHidecontent()).isFile()?"文件":"文件夹");
            pathNodes.add(node);
        }
        List<HideList> hideRegList = hideListMapper.selectByType(userId, 2);
        List<HideListDTOSend.HideRegNode> regNodes = new ArrayList<>();
        for (HideList hideList : hideRegList) {
            HideListDTOSend.HideRegNode node = new HideListDTOSend.HideRegNode();
            node.setId(hideList.getId());
            node.setReg(hideList.getHidecontent());
            node.setType("文件夹");//todo:修改正则判断索引类型
            regNodes.add(node);
        }
        //包装reg
        send.setCode(0);
        send.setMsg("查询成功");
        send.setCount(pathNodes.size()+regNodes.size());
        send.setPathdata(pathNodes);
        send.setRegdata(regNodes);

        return send;
    }

    @Override
    public boolean isHide(File file) {
        HideListDTOSend allData = getAllData();
        List<HideListDTOSend.HidePathNode> pathdata = allData.getPathdata();
        for (HideListDTOSend.HidePathNode pathNode : pathdata) {
            if (file.getPath().equals(pathNode.getPath())) {
                return true;
            }
        }//路径隐藏列
        List<HideListDTOSend.HideRegNode> regdata = allData.getRegdata();
        for (HideListDTOSend.HideRegNode regNode : regdata) {
            if (ReUtil.isMatch(regNode.getReg(),file.getPath())){
                return true;
            }
        }//正则隐藏列
        return false;
    }

    @Override
    public NormalSend delete(int id) {
        NormalSend send = new NormalSend();
        hideListMapper.deleteById(id);
        send.setCode(0);
        send.setMsg("删除成功");
        return send;
    }


}
