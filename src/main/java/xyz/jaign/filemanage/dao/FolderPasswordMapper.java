package xyz.jaign.filemanage.dao;

import xyz.jaign.filemanage.entity.FolderPassword;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderPasswordMapper {
    void insert(FolderPassword folderPassword);

    FolderPassword selectByFolder(int userid, String folder);

    List<FolderPassword> selectAll(int userid);

    void update(FolderPassword folderPassword);

    void delete(int userid, String folder);
}
