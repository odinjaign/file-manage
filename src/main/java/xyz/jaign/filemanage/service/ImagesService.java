package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.ImageDTOSend;

public interface ImagesService {
    ImageDTOSend getAllImages();

    byte[] img(String path);

}
