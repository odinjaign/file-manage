package com.example.demo1.service;

import com.example.demo1.dto.send.MainListDTOSend;

import java.util.List;

public interface FileListService {
    List<MainListDTOSend.FileNode> getFileList(String folder);
}
