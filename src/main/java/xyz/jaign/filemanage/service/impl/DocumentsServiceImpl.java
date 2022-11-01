package xyz.jaign.filemanage.service.impl;

import xyz.jaign.filemanage.dao.ClassListMapper;
import xyz.jaign.filemanage.dto.send.DocumentDTOSend;
import xyz.jaign.filemanage.entity.ClassList;
import xyz.jaign.filemanage.enums.ClassType;
import xyz.jaign.filemanage.service.DocumentsService;
import xyz.jaign.filemanage.util.CacheUtil;
import xyz.jaign.filemanage.util.DocumentOptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentsServiceImpl implements DocumentsService {


    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    private ClassManageServiceImpl classManageServiceImpl;
    @Autowired
    private ClassListMapper classListMapper;
    @Autowired
    private DocumentOptUtil documentOptUtil;
    @Autowired
    private CacheUtil cacheUtil;


    @Override
    public DocumentDTOSend getAllDocument() {
        DocumentDTOSend send = new DocumentDTOSend();

        if (cacheUtil.hasKey("document_list")) {
            List<DocumentDTOSend.DocumentNode> list = cacheUtil.getList("document_list", DocumentDTOSend.DocumentNode.class);
            send.setCode(0);
            send.setMsg("文档获取成功[Cache]");
            send.setCount(list.size());
            send.setData(list);
            return send;
        }

        //得到当前用户id
        int userid = loginServiceImpl.getNowUser().getId();
        List<ClassList> documentClassList = classListMapper.selectByTypeAndUser(ClassType.文档.value(), userid);
        List<DocumentDTOSend.DocumentNode> nodes = new ArrayList<>();
        for (ClassList classList : documentClassList) {
            List<File> files = classManageServiceImpl.getFile(classList);
            for (File document : files) {
                DocumentDTOSend.DocumentNode documentNode = documentOptUtil.toNode(document);
                documentNode.setId(nodes.size());
                nodes.add(documentNode);
            }
        }
        send.setCode(0);
        send.setMsg("文档获取成功");
        send.setCount(nodes.size());
        send.setData(nodes);
        cacheUtil.setList("document_list",nodes);
        return send;
    }

    @Override
    public void viewPdf(HttpServletResponse response, String path) {
        File file = new File(path);
        response.setContentType("application/pdf");
        if (file.exists()){
            byte[] data = null;
            FileInputStream input = null;
            try {
                input = new FileInputStream(file);data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
                input.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
