package com.example.demo1.controller;

import com.example.demo1.dto.send.HideListDTOSend;
import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.service.impl.HideManageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hide/manage")
public class HideManageController {


    @Autowired
    private HideManageServiceImpl hideManageServiceImpl;

    @PostMapping("add")
    public NormalSend addRecord(String content,int type){
        return hideManageServiceImpl.addRecord(content,type);
    }

    @PostMapping("list")
    public HideListDTOSend list(){
        return hideManageServiceImpl.getAllData();
    }

    @PostMapping("del")
    public NormalSend del(int id){
        return hideManageServiceImpl.delete(id);
    }

}
