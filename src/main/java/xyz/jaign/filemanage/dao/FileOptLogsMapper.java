package xyz.jaign.filemanage.dao;

import xyz.jaign.filemanage.entity.FileOptLogs;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileOptLogsMapper {
    void insertLog(FileOptLogs fileOptLogs);
    Integer selectMaxIndex(int userid);
    List<FileOptLogs> selectLogsByUserid(int userid);
}
