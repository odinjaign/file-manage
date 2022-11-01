package xyz.jaign.filemanage.controller;

import xyz.jaign.filemanage.dao.ClassListMapper;
import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.entity.ClassList;
import xyz.jaign.filemanage.service.impl.ClassManageServiceImpl;
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
