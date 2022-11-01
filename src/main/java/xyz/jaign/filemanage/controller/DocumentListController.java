package xyz.jaign.filemanage.controller;

import xyz.jaign.filemanage.dto.send.DocumentDTOSend;
import xyz.jaign.filemanage.service.impl.DocumentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("document")
public class DocumentListController {


    @Autowired
    private DocumentsServiceImpl documentsServiceImpl;

    @PostMapping("list")
    public DocumentDTOSend list(){
        return documentsServiceImpl.getAllDocument();
    }

    @GetMapping("pdf/view")
    public void viewPdf(HttpServletResponse response,String path){
        documentsServiceImpl.viewPdf(response,path);
    }
}
