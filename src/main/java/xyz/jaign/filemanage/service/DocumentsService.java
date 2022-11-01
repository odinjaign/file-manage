package xyz.jaign.filemanage.service;

import xyz.jaign.filemanage.dto.send.DocumentDTOSend;

import javax.servlet.http.HttpServletResponse;

public interface DocumentsService {
    DocumentDTOSend getAllDocument();

    void viewPdf(HttpServletResponse response, String path);
}
