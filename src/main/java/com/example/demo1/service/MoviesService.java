package com.example.demo1.service;

import com.example.demo1.dto.send.MovieDTOSend;
import com.example.demo1.dto.send.MusicDTOSend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MoviesService {
    MovieDTOSend getAllMovie();

    void playMovie(HttpServletRequest request, HttpServletResponse response, String path) throws IOException, ServletException;
}
