package xyz.jaign.filemanage.dao;

import xyz.jaign.filemanage.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    void insertUser(User user);
    User selectByUsername(String username);
    void updateById(User user);
    List<User> selectByType(int type);
    void deleteByUsername(String username);
}
