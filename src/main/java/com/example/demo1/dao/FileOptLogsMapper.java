package com.example.demo1.dao;

import com.example.demo1.entity.FileOptLogs;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileOptLogsMapper {
    void insertLog(FileOptLogs fileOptLogs);
    Integer selectMaxIndex(int userid);
    List<FileOptLogs> selectLogsByUserid(int userid);
}
