package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.HideListDTOSend;
import xyz.jaign.filemanage.dto.send.NormalSend;

import java.io.File;

public interface HideManageService {

    NormalSend addRecord(String content, int type);

    HideListDTOSend getAllData();

    boolean isHide(File file);

    NormalSend delete(int id);
}
