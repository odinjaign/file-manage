package com.example.demo1.controller;

import com.example.demo1.dto.send.MainListDTOSend;
import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.service.impl.MainListServiceImpl;
import com.example.demo1.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("main")
public class MainListController {


    @Autowired
    private MainListServiceImpl mainListServiceImpl;
    @Autowired
    private CacheUtil cacheUtil;


    @PostMapping("list")
    public MainListDTOSend list(){

        MainListDTOSend send = new MainListDTOSend();

        if (!cacheUtil.hasKey("user")){
            send.setCode(-1);
            send.setMsg("用户未登录");
            return send;
        }

        if(cacheUtil.hasKey("main_list")){
            List<MainListDTOSend.FileNode> main_list = cacheUtil.getList("main_list", MainListDTOSend.FileNode.class);
            send.setCount(main_list.size());
            send.setMsg("成功");
            send.setCode(0);
            send.setData(main_list);
            return send;
        }else {
            mainListServiceImpl.initCacheList();
        }

        return send;
    }

    @PostMapping("updatecache")
    public boolean updateCache(){
        mainListServiceImpl.updateCache();
        return true;
    }
    /**
     * 获取当前文件夹路径
     * @return 当前文件夹路径
     */
    @PostMapping("pwd")
    public String pwd(){
        if(cacheUtil.hasKey("pwd")){
            return cacheUtil.get("pwd");
        }else {
            mainListServiceImpl.initCacheList();
            return "";
        }
    }

    /**
     * 返回上一级
     * @return 上一级
     */
    @PostMapping("returnlast")
    public NormalSend returnlast(){
        return mainListServiceImpl.returnlast();
    }

    /**
     * 进入下一级
     * @return 下一级
     */
    @PostMapping("enterfolder")
    public NormalSend enterfolder(String folder,String password){
        if(password == null){
            return mainListServiceImpl.enterfolder(folder,true);
        }else {
            return mainListServiceImpl.enterfolderByPassword(folder,password);
        }
    }
}
