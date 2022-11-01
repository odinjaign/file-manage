package xyz.jaign.filemanage.service.impl;

import xyz.jaign.filemanage.dao.ClassListMapper;
import xyz.jaign.filemanage.dto.send.ImageDTOSend;
import xyz.jaign.filemanage.entity.ClassList;
import xyz.jaign.filemanage.enums.ClassType;
import xyz.jaign.filemanage.service.ImagesService;
import xyz.jaign.filemanage.util.CacheUtil;
import xyz.jaign.filemanage.util.ImageUtil;
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
    @Autowired
    private CacheUtil cacheUtil;


    @Override
    public ImageDTOSend getAllImages() {
        ImageDTOSend send = new ImageDTOSend();

        if (cacheUtil.hasKey("img_list")) {
            List<ImageDTOSend.ImageNode> img_list = cacheUtil.getList("img_list", ImageDTOSend.ImageNode.class);
            send.setCode(0);
            send.setMsg("图片列表获取成功[Cache]");
            send.setCount(img_list.size());
            send.setData(img_list);
            return send;
        }

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
        send.setMsg("图片列表获取成功");
        send.setCount(nodes.size());
        send.setData(nodes);
        cacheUtil.setList("img_list",nodes);
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
