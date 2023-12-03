package com.itheima.mp.util;

public class FormatMethod {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static boolean validateEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static Double GPAFormat(Double gpa) {
        String str = String.format("%.2f", gpa);
        gpa = Double.parseDouble(str);
        return gpa;
    }

    public static Double DurationFormat(Double gpa) {
        String str = String.format("%.1f", gpa);
        gpa = Double.parseDouble(str);
        return gpa;
    }

    public static boolean containsLetterOrDigit(String str) {
        // 使用正则表达式匹配字母或数字
        String regex = ".*[a-zA-Z0-9].*";
        return str.matches(regex);
    }
}
