package com.example.demo1.service.impl;

import com.example.demo1.dao.ClassListMapper;
import com.example.demo1.dto.send.ImageDTOSend;
import com.example.demo1.entity.ClassList;
import com.example.demo1.enums.ClassType;
import com.example.demo1.service.ImagesService;
import com.example.demo1.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    private ClassManageServiceImpl classManageServiceImpl;
    @Autowired
    private ClassListMapper classListMapper;

    @Override
    public ImageDTOSend getAllImages() {
        ImageDTOSend send = new ImageDTOSend();

        //得到当前用户id
        int userid = loginServiceImpl.getNowUser().getId();
        List<ClassList> imageClassList = classListMapper.selectByTypeAndUser(ClassType.图片.value(), userid);
        List<ImageDTOSend.ImageNode> nodes = new ArrayList<>();
        for (ClassList classList : imageClassList) {
            List<File> file = classManageServiceImpl.getFile(classList);
            for (File img : file) {
                ImageDTOSend.ImageNode imageNode = ImageUtil.toNode(img);
                imageNode.setId(nodes.size());
                nodes.add(imageNode);
            }
        }
        send.setCode(0);
        send.setMsg("视频列表获取成功");
        send.setCount(nodes.size());
        send.setData(nodes);

        return send;
    }

    @Override
    public byte[] img(String path) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(path));
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
