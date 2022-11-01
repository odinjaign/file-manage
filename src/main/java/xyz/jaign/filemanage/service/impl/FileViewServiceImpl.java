package xyz.jaign.filemanage.service.impl;

import cn.hutool.core.io.FileUtil;
import xyz.jaign.filemanage.dto.send.StringDTOSend;
import xyz.jaign.filemanage.poi.Excel2Html;
import xyz.jaign.filemanage.poi.Md2Html;
import xyz.jaign.filemanage.poi.PptToHtml;
import xyz.jaign.filemanage.poi.Word2Html;
import xyz.jaign.filemanage.service.FileViewService;
import xyz.jaign.filemanage.util.StringOptUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class FileViewServiceImpl implements FileViewService {

    @Value("${jaign.config.html-tmp}")
    private String htmlTmp;

    @Override
    public StringDTOSend viewText(String path) {
        StringDTOSend send = new StringDTOSend();

        File file = new File(path);
        String s = FileUtil.readString(file, StandardCharsets.UTF_8);
        send.setCode(0);
        send.setData(s);
        send.setExt(StringOptUtil.fileExt(file.getName()));
        send.setMsg("获取成功");
        return send;
    }

    @Override
    public StringDTOSend viewWord(String path) {
        StringDTOSend send = new StringDTOSend();
        File tempFile = new File(htmlTmp);
        String fileExt = StringOptUtil.fileExt(path);
        try {
            if ("doc".equals(fileExt)) {
                Word2Html.doc2Html(path, tempFile.getPath());
            } else if ("docx".equals(fileExt)) {
                Word2Html.docx2Html(path, tempFile.getPath());
            } else {
                send.setCode(-1);
                send.setMsg("文件不是Word档案");
                return send;
            }

        } catch (TransformerException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
            send.setCode(-2);
            send.setMsg("文档转换异常，预览失败！");
            return send;
        }
        send.setMsg("Word转Html完成。");
        send.setCode(1);
        send.setExt("html");
        String s = FileUtil.readString(tempFile, "utf-8");
        send.setData(s);
        return send;
    }

    @Override
    public StringDTOSend viewExcel(String path) {
        StringDTOSend send = new StringDTOSend();
        File tempFile = new File(htmlTmp);
        Excel2Html.readExcelToHtml(path, htmlTmp, true, 2);
        send.setMsg("Excel转Html完成。[未携带图片]");
        send.setCode(1);
        send.setExt("html");
        String s = FileUtil.readString(tempFile, "utf-8");
        send.setData(s);
        return send;
    }

    @Override
    public StringDTOSend viewPpt(String path) {
        StringDTOSend send = new StringDTOSend();
        File tempFile = new File(htmlTmp);
        PptToHtml.ppt2Html(path,htmlTmp);
        send.setMsg("PPT转Html完成。");
        send.setCode(1);
        send.setExt("html");
        String s = FileUtil.readString(tempFile, "utf-8");
        send.setData(s);
        return send;

    }

    @Override
    public StringDTOSend viewMd(String path) {
        StringDTOSend send = new StringDTOSend();
        File file = new File(path);
        if (!file.exists()) {
            send.setCode(-1);
            send.setMsg("文件不存在");
            return send;
        }
        String html = Md2Html.mdToHtmlByContent(FileUtil.readUtf8String(file));
        send.setCode(1);
        send.setMsg("Md转Html完成");
        send.setData(html);
        send.setExt("html");
        return send;

    }
}
