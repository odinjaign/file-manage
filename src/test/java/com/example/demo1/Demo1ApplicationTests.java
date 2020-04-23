package com.example.demo1;

import com.example.demo1.component.DocumentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo1ApplicationTests {


    @Autowired
    private DocumentType documentType;

    @Test
    void contextLoads() {
        System.out.println(documentType);
    }

}
