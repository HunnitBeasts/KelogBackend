package com.hunnit_beasts.kelog.common.dto.converter.util;

public class LinkUtil {

    private LinkUtil(){}

    public static String createLink(String...str) {
        StringBuilder link = new StringBuilder();
        for(String element : str){
            if(link.isEmpty()) link = new StringBuilder(element);
            else link.append("/").append(element);
        }
        return link.toString();
    }
}
