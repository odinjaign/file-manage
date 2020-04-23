package com.example.demo1.test;

import cn.hutool.json.JSONUtil;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InvalidDataException, IOException, UnsupportedTagException {
//
//        Mp3File mp3file = new Mp3File("C:\\Users\\jaign\\test\\jaign\\music1\\test\\阿婆说   陈一发儿.mp3");
//        System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
//        System.out.println("Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
//        System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
//        System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
//        System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
//        System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));
//        if (mp3file.hasId3v1Tag()) {
//            ID3v1 id3v1Tag = mp3file.getId3v1Tag();
//            System.out.println("Track: " + id3v1Tag.getTrack());
//            System.out.println("Artist: " + id3v1Tag.getArtist());
//            String tttt = new String(id3v1Tag.getAlbum().getBytes("ISO-8859-1"), "GB2312");
//            System.out.println("Title: " + id3v1Tag.getTitle());
//            System.out.println("Album: " + id3v1Tag.getAlbum() + tttt);
//            System.out.println("Year: " + id3v1Tag.getYear());
//            System.out.println("Genre: " + id3v1Tag.getGenre() + " (" + id3v1Tag.getGenreDescription() + ")");
//            System.out.println("Comment: " + id3v1Tag.getComment());
//        }
//        if (mp3file.hasId3v2Tag()) {
//            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
//            System.out.println("Track: " + id3v2Tag.getTrack());
//            System.out.println("Artist: " + id3v2Tag.getArtist());
//            System.out.println("Title: " + id3v2Tag.getTitle());
//            System.out.println("Album: " + id3v2Tag.getAlbum());
//            System.out.println("Year: " + id3v2Tag.getYear());
//            System.out.println("Genre: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")");
//            System.out.println("Comment: " + id3v2Tag.getComment());
//            System.out.println("Lyrics: " + id3v2Tag.getLyrics());
//            System.out.println("Composer: " + id3v2Tag.getComposer());
//            System.out.println("Publisher: " + id3v2Tag.getPublisher());
//            System.out.println("Original artist: " + id3v2Tag.getOriginalArtist());
//            System.out.println("Album artist: " + id3v2Tag.getAlbumArtist());
//            System.out.println("Copyright: " + id3v2Tag.getCopyright());
//            System.out.println("URL: " + id3v2Tag.getUrl());
//            System.out.println("Encoder: " + id3v2Tag.getEncoder());
//            byte[] albumImageData = id3v2Tag.getAlbumImage();
//            if (albumImageData != null) {
//                System.out.println("Have album image data, length: " + albumImageData.length + " bytes");
//                System.out.println("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());
//            }
//        }


        List<Pair<Integer, Integer>> list = new ArrayList<>();
        list.add(new Pair<>(5,6));
        list.add(new Pair<>(7,8));
        list.add(new Pair<>(7,0));
        list.add(new Pair<>(5,2));
        System.out.println(JSONUtil.parse(list).toStringPretty());
    }
}
