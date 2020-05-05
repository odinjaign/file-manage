package com.example.demo1.service;

import com.example.demo1.dto.send.FileOptSend;
import com.example.demo1.dto.send.NormalSend;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileSuperOptService {
    FileOptSend createFileOrFolder(String filename, boolean isFolder);

    FileOptSend uploadFile(MultipartFile file);

    FileOptSend downloadFile(String filepath, HttpServletResponse response);

    NormalSend unzip(String file, String folder);

    NormalSend zip(String folder, String dst);
}
