package com.example.demo1.service;

import com.example.demo1.dto.send.HideListDTOSend;
import com.example.demo1.dto.send.NormalSend;

import java.io.File;

public interface HideManageService {

    NormalSend addRecord(String content, int type);

    HideListDTOSend getAllData();

    boolean isHide(File file);

    NormalSend delete(int id);
}
