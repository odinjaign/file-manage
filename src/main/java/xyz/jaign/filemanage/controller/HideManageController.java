package xyz.jaign.filemanage.controller;

import xyz.jaign.filemanage.dto.send.HideListDTOSend;
import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.service.impl.HideManageServiceImpl;
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
