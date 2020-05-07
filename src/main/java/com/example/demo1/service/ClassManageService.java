package com.example.demo1.service;

import com.example.demo1.dto.send.NormalSend;
import com.example.demo1.entity.ClassList;

import java.io.File;
import java.util.List;


public interface ClassManageService {

    NormalSend add(String path, String type, int num, String exts);

    NormalSend modify(String path, int type, int num, String exts);

    NormalSend delete(String path, int type);

    List<File> getFile(ClassList list);

    List<ClassList> getUserItems();

    NormalSend delCache();
}
