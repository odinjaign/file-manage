package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.MusicDTOSend;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MusicsService {
    MusicDTOSend getAllMusic();

    void playMusic(HttpServletRequest request, HttpServletResponse response, String path);
}
