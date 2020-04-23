package com.example.demo1.service;

import com.example.demo1.dto.send.DocumentDTOSend;

import javax.servlet.http.HttpServletResponse;

public interface DocumentsService {
    DocumentDTOSend getAllDocument();

    void viewPdf(HttpServletResponse response, String path);
}
