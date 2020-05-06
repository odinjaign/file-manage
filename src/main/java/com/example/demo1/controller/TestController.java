package com.example.demo1.controller;

import cn.hutool.core.io.FileUtil;
import com.example.demo1.dto.send.StringDTOSend;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("view/word")
    public StringDTOSend viewWord(HttpServletResponse response) {
        StringDTOSend send = new StringDTOSend();
        send.setMsg("");
        send.setCode(1);
        send.setExt("html");
        String s = FileUtil.readString(new File("C:\\Users\\jaign\\test\\html\\毕业论文-蒋加济.v2.html"), "utf-8");
        send.setData(s);
        return send;
    }

}
