package com.example.URLShortener.URL.Shortener.Util;

import java.util.*;

import java.util.concurrent.ThreadLocalRandom;
//this package helps to generate random number in multithreaded apps, such as web apps

public class Base62Encoder {

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = ALPHABET.length();

    public static String encode(long num){
        StringBuilder sb = new StringBuilder();
        while(num > 0){
            sb.append(ALPHABET.charAt((int) (num % BASE)));
            num /= BASE;
        }
        return sb.reverse().toString();
    }

    public static String generateShortCode(){
        long randomNum = ThreadLocalRandom.current().nextLong(100000, 999999);
        return encode(randomNum);
    }
}
