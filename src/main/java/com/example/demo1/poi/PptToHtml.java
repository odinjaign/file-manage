package com.example.demo1.poi;

import cn.hutool.core.util.URLUtil;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class PptToHtml {
    private final static String  tempPath = "C:\\Users\\jaign\\test\\img\\ppt";
//    private final static String imgurlPre = "http://localhost:8080/image/img?path=";
    private final static String imgurlPre = "/image/img?path=";

    private static List<String> pptToPicture(String tempContextUrl, String outPath) {
        //文件夹名
        List<String> imgList = new ArrayList<String>();
        File file = new File(tempContextUrl);
        File folder = new File(outPath + File.separator + "temp");
        try {
            folder.mkdirs();
            FileInputStream is = new FileInputStream(file);
            SlideShow ppt = new SlideShow(is);
            is.close();
            Dimension pgsize = ppt.getPageSize();
            org.apache.poi.hslf.model.Slide[] slide = ppt.getSlides();
            for (int i = 0; i < slide.length; i++) {
                TextRun[] truns = slide[i].getTextRuns();
                for (int k = 0; k < truns.length; k++) {
                    RichTextRun[] rtruns = truns[k].getRichTextRuns();
                    for (int l = 0; l < rtruns.length; l++) {
                        rtruns[l].setFontIndex(1);
                        rtruns[l].setFontName("宋体");
                    }
                }
                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();
                graphics.setPaint(Color.BLUE);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                slide[i].draw(graphics);
                String imgName = File.separator + "temp" + File.separator + "pict_" + (i + 1) + ".jpeg";
                FileOutputStream out = new FileOutputStream(outPath + imgName);
                javax.imageio.ImageIO.write(img, "jpeg", out);
                out.close();
                String imgEncodePath = URLUtil.encode(outPath + imgName);
                imgList.add(imgurlPre+imgEncodePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgList;
    }

    /**
     * 自己创建的html代码
     * 原理上就是，把上一步ppt转的图片
     * 以html的方式呈现出来
     */
    private static void createPPTHtml(List<String> imgList, String htmlPath) {
        StringBuilder sb = new StringBuilder("<!doctype html><html><head><meta charset='utf-8'><title>无标题文档</title></head><body><div align=\"center\">");
        if (imgList != null && !imgList.isEmpty()) {
            for (String img : imgList) {
                sb.append("<img src='" + img + "' /><br>");
            }
        }
        sb.append("</div></body></html>");
        try {
            File file = new File(htmlPath);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            bufferedWriter.write(sb.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ppt2Html(String fileName, String outPutFile){
        List<String> strings = pptToPicture(fileName, tempPath);
        createPPTHtml(strings,outPutFile);
    }

}