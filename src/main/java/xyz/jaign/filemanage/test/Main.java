package xyz.jaign.filemanage.test;

import xyz.jaign.filemanage.poi.Md2Html;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InvalidDataException, IOException, UnsupportedTagException {
//        System.out.println(PasswordUtils.passwords("c"));
//        ZipUtil.unzip("C:\\Users\\jaign\\test\\jaign\\imgs.zip","C:\\Users\\jaign\\Desktop");
//        ZipUtil.zip("C:\\Users\\jaign\\test\\jaign\\layui2");
//        try {
//            Word2Html.docx2Html("C:\\Users\\jaign\\Desktop\\毕业论文-蒋加济.v2.docx",
//                    "C:\\Users\\jaign\\test\\html\\毕业论文-蒋加济.v2.html");
//        } catch (TransformerException e) {
//            e.printStackTrace();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        }

        String s = Md2Html.mdToHtmlByFile("C:\\Users\\jaign\\test\\jaign\\a\\README.md");
//        System.out.println(new File("file/huimarkdown.css").getAbsolutePath());
        System.out.println(s);

    }
}
