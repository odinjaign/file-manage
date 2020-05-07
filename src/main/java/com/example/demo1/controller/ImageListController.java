package com.example.demo1.controller;

import com.example.demo1.dto.send.ImageDTOSend;
import com.example.demo1.service.impl.ImagesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("image")
public class ImageListController {


    @Autowired
    private ImagesServiceImpl imagesServiceImpl;

    @PostMapping("list")
    ImageDTOSend list() {
        return imagesServiceImpl.getAllImages();
    }



    @GetMapping("img")
    public byte[] img(String path) {
        return imagesServiceImpl.img(path);
    }
}
