package com.example.demo1.service;

import com.example.demo1.dto.send.FileOptSend;

import java.io.File;

public interface FileOptService {
    FileOptSend copyto(File src, File folder);

    FileOptSend moveto(File src, File folder);

    FileOptSend renameto(File src, String newname);

    FileOptSend delete(File file);
}
