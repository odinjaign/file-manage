package xyz.jaign.filemanage.controller;

import xyz.jaign.filemanage.dto.send.NormalSend;
import xyz.jaign.filemanage.dto.send.SysConfigDTOSend;
import xyz.jaign.filemanage.service.impl.SysConfigServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("sys/config")
public class SysConfigController {


    @Autowired
    private SysConfigServiceImpl sysConfigServiceImpl;

    @PostMapping("modify")
    public NormalSend modify(String key, boolean value) {
        return sysConfigServiceImpl.setConfigStatus(key, value);
    }

    @PostMapping("list")
    public SysConfigDTOSend list() {
        return sysConfigServiceImpl.getAllConfigByList();
    }

    @PostMapping("get")
    public boolean get(String key) {
        return sysConfigServiceImpl.get(key);
    }
}
