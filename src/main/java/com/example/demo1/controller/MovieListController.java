package com.example.demo1.controller;

import com.example.demo1.dto.send.MovieDTOSend;
import com.example.demo1.dto.send.MusicDTOSend;
import com.example.demo1.service.impl.MoviesServiceImpl;
import com.example.demo1.service.impl.MusicsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("movie")
public class MovieListController {


    @Autowired
    private MoviesServiceImpl moviesServiceImpl;

    /**
     * 获取当前分类列所有音乐
     * @return
     */
    @PostMapping("list")
    MovieDTOSend list(){
       return moviesServiceImpl.getAllMovie();
    }

    @GetMapping("file")
    public void player(HttpServletRequest request, HttpServletResponse response, String path){
            moviesServiceImpl.playMovie(request,response,path);

    }

}
