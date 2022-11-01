package xyz.jaign.filemanage;

import xyz.jaign.filemanage.component.DocumentType;
import xyz.jaign.filemanage.dao.FileOptLogsMapper;
import xyz.jaign.filemanage.dao.TaskMapper;
import xyz.jaign.filemanage.util.CacheUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo1ApplicationTests {


    @Autowired
    private DocumentType documentType;
    @Autowired
    private FileOptLogsMapper fileOptLogsMapper;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private TaskMapper taskMapper;


    @Test
    void contextLoads() {

        Integer count = taskMapper.getCount();
        System.out.println(count);
    }

}
