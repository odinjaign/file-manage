package com.example.demo1.test;

import com.example.demo1.util.PasswordUtils;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InvalidDataException, IOException, UnsupportedTagException {
        System.out.println(PasswordUtils.passwords("admin"));
    }
}
