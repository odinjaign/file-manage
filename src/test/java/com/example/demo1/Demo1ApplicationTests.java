package com.example.demo1;

import com.example.demo1.component.DocumentType;
import com.example.demo1.dao.FileOptLogsMapper;
import com.example.demo1.dao.TaskMapper;
import com.example.demo1.util.CacheUtil;
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
