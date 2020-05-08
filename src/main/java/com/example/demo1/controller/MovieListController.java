package com.example.demo1.controller;

import com.example.demo1.component.NonStaticResourceHttpRequestHandler;
import com.example.demo1.dto.send.MovieDTOSend;
import com.example.demo1.service.impl.MoviesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("movie")
public class MovieListController {


    @Autowired
    private MoviesServiceImpl moviesServiceImpl;
    @Autowired
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;


    /**
     * 获取当前分类列所有视频
     * @return
     */
    @PostMapping("list")
    MovieDTOSend list(){
       return moviesServiceImpl.getAllMovie();
    }

    /**
     * 预览视频文件, 支持 byte-range 请求
     */
    @GetMapping("file")
    public void player(HttpServletRequest request, HttpServletResponse response, String path){
        try {
            moviesServiceImpl.playMovie(request,response,path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
