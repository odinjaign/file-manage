package com.example.demo1.util;

import com.example.demo1.component.DocumentType;
import com.example.demo1.dto.send.DocumentDTOSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;

@Component
public class DocumentOptUtil {


    @Autowired
    private DocumentType documentType;

    public  DocumentDTOSend.DocumentNode toNode(File document) {
        DocumentDTOSend.DocumentNode node = new DocumentDTOSend.DocumentNode();

        node.setName(document.getName());
        node.setPath(document.getPath());
        node.setSize((int) (document.length() / 1024));
        node.setExt(StringOptUtil.fileExt(document.getName()));
        node.setType(documentType.typeOf(node.getExt()));

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        node.setUpdateTime(format.format(document.lastModified()));

        return node;
    }
}
