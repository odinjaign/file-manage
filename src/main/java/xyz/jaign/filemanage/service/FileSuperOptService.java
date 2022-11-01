package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.FileOptSend;
import xyz.jaign.filemanage.dto.send.NormalSend;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileSuperOptService {
    FileOptSend createFileOrFolder(String filename, boolean isFolder);

    FileOptSend uploadFile(MultipartFile file);

    FileOptSend downloadFile(String filepath, HttpServletResponse response);

    NormalSend unzip(String file, String folder);

    NormalSend zip(String folder, String dst);
}
