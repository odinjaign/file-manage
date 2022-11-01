package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.OptLogsDTOSend;

import java.io.File;

public interface OptLogService {

    boolean logCopy(File src, File dst);

    boolean logMove(File src, File dst);

    boolean logRename(File src, String newname);

    boolean logDel(File file);

    OptLogsDTOSend getNowUserLogs();
}
