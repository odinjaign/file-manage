package com.example.demo1.service;

import com.example.demo1.dto.send.StringDTOSend;

public interface FileViewService {
    StringDTOSend viewText(String path);

    StringDTOSend viewWord(String path);

    StringDTOSend viewExcel(String path);

    StringDTOSend viewPpt(String path);

    StringDTOSend viewMd(String path);
}
