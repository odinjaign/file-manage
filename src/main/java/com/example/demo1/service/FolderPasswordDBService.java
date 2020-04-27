package com.example.demo1.service;

import com.example.demo1.dto.send.FolderPasswordDTOSend;
import com.example.demo1.dto.send.NormalSend;

public interface FolderPasswordDBService {

    NormalSend addRecord(String fodler, String password);

    FolderPasswordDTOSend getAll();

    NormalSend changeStatus(String folder, int code);

    String getPassword(String folder);

    NormalSend modifyPassword(String folder, String password);

    NormalSend delete(String folder);
}
