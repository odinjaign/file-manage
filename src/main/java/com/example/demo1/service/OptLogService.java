package com.example.demo1.service;

import com.example.demo1.dto.send.OptLogsDTOSend;

import java.io.File;

public interface OptLogService {

    boolean logCopy(File src, File dst);

    boolean logMove(File src, File dst);

    boolean logRename(File src, String newname);

    boolean logDel(File file);

    OptLogsDTOSend getNowUserLogs();
}
