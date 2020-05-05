package com.example.demo1;

import com.example.demo1.component.DocumentType;
import com.example.demo1.dao.FileOptLogsMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo1ApplicationTests {


    @Autowired
    private DocumentType documentType;
    @Autowired
    private FileOptLogsMapper fileOptLogsMapper;

    @Test
    void contextLoads() {

        System.out.println(fileOptLogsMapper.selectMaxIndex(2));

    }

}
