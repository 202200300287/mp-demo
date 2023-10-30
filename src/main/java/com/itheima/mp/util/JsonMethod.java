package com.itheima.mp.util;

public class JsonMethod {

    public static String removeBackslash(String s){
        return s.replaceAll("\\\\","");
    }
}
