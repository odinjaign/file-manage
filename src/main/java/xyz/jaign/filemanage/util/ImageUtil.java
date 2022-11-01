package xyz.jaign.filemanage.util;

import xyz.jaign.filemanage.dto.send.ImageDTOSend;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {
    public static ImageDTOSend.ImageNode toNode(File img) {

        ImageDTOSend.ImageNode imageNode = new ImageDTOSend.ImageNode();
        imageNode.setName(img.getName());
        imageNode.setPath(img.getPath());
        imageNode.setSize((int) (img.length() / 1024));
        imageNode.setExt(StringOptUtil.fileExt(img.getName()));
        try {
            BufferedImage bufferedImage = ImageIO.read(img);
            imageNode.setH(bufferedImage.getHeight());
            imageNode.setW(bufferedImage.getWidth());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageNode;
    }
}
