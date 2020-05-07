package com.example.demo1.service;

import com.example.demo1.dto.send.ImageDTOSend;

public interface ImagesService {
    ImageDTOSend getAllImages();

    byte[] img(String path);

}
