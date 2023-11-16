package com.itheima.mp.util;

public class FormatMethod {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static boolean validateEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static Double GPAFormat(Double gpa){
        String  str = String.format("%.2f",gpa);
        gpa = Double.parseDouble(str);
        return gpa;
    }
}
