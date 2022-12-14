package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.FolderPasswordDTOSend;
import xyz.jaign.filemanage.dto.send.NormalSend;

public interface FolderPasswordDBService {

    NormalSend addRecord(String fodler, String password);

    FolderPasswordDTOSend getAll();

    NormalSend changeStatus(String folder, int code);

    String getPassword(String folder);

    NormalSend modifyPassword(String folder, String password);

    NormalSend delete(String folder);
}
