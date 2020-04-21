package com.example.demo1.controller;

import com.example.demo1.dto.send.MusicDTOSend;
import com.example.demo1.service.impl.MusicsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("music")
public class MusicListController {


    @Autowired
    private MusicsServiceImpl musicsServiceImpl;

    /**
     * 获取当前分类列所有音乐
     * @return
     */
    @PostMapping("list")
    MusicDTOSend list(){
       return musicsServiceImpl.getAllMusic();
    }

    @GetMapping("file")
    public void player(HttpServletRequest request, HttpServletResponse response, String path){
        musicsServiceImpl.playMusic(request,response,path);
}

}
