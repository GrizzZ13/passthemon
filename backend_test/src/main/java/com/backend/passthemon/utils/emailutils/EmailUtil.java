package com.backend.passthemon.utils.emailutils;

import java.util.Random;
import java.util.regex.Pattern;

public class EmailUtil {
    public static final String FROM = "passthemon@163.com";
    public static final String GREETING =
            "Dear PTO-er:\n    Thank you for using our service. " +
            "The following is your verification code that you need" +
            " to activate your the account: " +
            "(case sensitive, valid in 15 minutes) : \n    ";
    public static final String SUBJECT = "Hello! PassThemOn Here!";

    public static final String REGEX_SJTU_EMAIL = "^([a-z0-9A-Z]+[-|\\\\.]?)+[a-z0-9A-Z]@(sjtu.edu.cn)";

    public static final Integer FIXED_LENGTH = 6;

    public static String makeText(String code){
        return GREETING + code + "\n    Have fun with it! ";
    }

    public static Boolean isSJTUEmail(String email) {
        return Pattern.matches(REGEX_SJTU_EMAIL, email);
    }

    public static String generateVerificationCode(Integer length){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
