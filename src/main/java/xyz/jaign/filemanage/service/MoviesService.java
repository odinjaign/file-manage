package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.MovieDTOSend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MoviesService {
    MovieDTOSend getAllMovie();

    void playMovie(HttpServletRequest request, HttpServletResponse response, String path) throws IOException, ServletException, Exception;
}
