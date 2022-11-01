package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.FileOptSend;

import java.io.File;

public interface FileOptService {
    FileOptSend copyto(File src, File folder);

    FileOptSend moveto(File src, File folder);

    FileOptSend renameto(File src, String newname);

    FileOptSend delete(File file);
}
