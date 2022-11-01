package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.StringDTOSend;

public interface FileViewService {
    StringDTOSend viewText(String path);

    StringDTOSend viewWord(String path);

    StringDTOSend viewExcel(String path);

    StringDTOSend viewPpt(String path);

    StringDTOSend viewMd(String path);
}
