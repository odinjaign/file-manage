package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.MainListDTOSend;

import java.util.List;

public interface FileListService {
    List<MainListDTOSend.FileNode> getFileList(String folder);
}
