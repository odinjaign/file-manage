package xyz.jaign.filemanage.util;

import xyz.jaign.filemanage.dto.send.MusicDTOSend;
import com.mpatric.mp3agic.*;

import java.io.File;
import java.io.IOException;

public class MusicOptUtil {

    public static MusicDTOSend.MusicNode toNode(File file){
        MusicDTOSend.MusicNode musicNode = new MusicDTOSend.MusicNode();
        try {
            Mp3File mp3file = new Mp3File(file);
            musicNode.setLength(mp3file.getLengthInSeconds());
            if (mp3file.hasId3v1Tag()) {
                ID3v1 id3v1Tag = mp3file.getId3v1Tag();
                musicNode.setSinger(new String(id3v1Tag.getArtist().getBytes("ISO-8859-1"), "GB2312"));
                musicNode.setTitle(new String(id3v1Tag.getTitle().getBytes("ISO-8859-1"), "GB2312"));
                musicNode.setSheet(new String(id3v1Tag.getAlbum().getBytes("ISO-8859-1"), "GB2312"));
            }
            if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                String title = id3v2Tag.getTitle();
                String artist = id3v2Tag.getArtist();
                String album = id3v2Tag.getAlbum();
                if (title != null) {
                    musicNode.setTitle(title);
                }
                if (artist != null) {
                    musicNode.setSinger(artist);
                }
                if (album != null) {
                    musicNode.setSheet(album);
                }
            }
            musicNode.setName(file.getName());
            musicNode.setPath(file.getPath());
            musicNode.setExt(StringOptUtil.fileExt(file.getName()));
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            e.printStackTrace();
        }
        return musicNode;
    }
}
