package com.example.demo1.service;

import com.example.demo1.dto.send.NormalSend;


public interface ClassManageService {

    NormalSend add(String path, String type, int num, String exts);

    NormalSend modify(String path, int type, int num, String exts);

    NormalSend delete(String path, int type);
}
