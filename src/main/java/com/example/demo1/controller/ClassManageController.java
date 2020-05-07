package com.example.demo1.controller;

import com.example.demo1.dao.ClassListMapper;
import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.entity.ClassList;
import com.example.demo1.service.impl.ClassManageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("class/manage")
public class ClassManageController {

    @Autowired
    private ClassListMapper classListMapper;
    @Autowired
    private ClassManageServiceImpl classManageServiceImpl;

    @PostMapping("items")
    public List<ClassList> items(){
        return classManageServiceImpl.getUserItems();
    }

    @PostMapping("del/cache")
    NormalSend delCache(){
        return classManageServiceImpl.delCache();
    }

    @PostMapping("add")
    public NormalSend add(String path,String type,int num,String exts){
        return classManageServiceImpl.add(path,type,num,exts);
    }

    @PostMapping("modify")
    public NormalSend modify(String path,int type,int num,String exts){
        return classManageServiceImpl.modify(path,type,num,exts);
    }

    @PostMapping("delete")
    public NormalSend delete(String path,int type){
        return classManageServiceImpl.delete(path,type);
    }
}
