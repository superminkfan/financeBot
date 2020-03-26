package com.telegramBot.myUtil;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {

    public static boolean checkWithRegExp(String inputString){
        Pattern p = Pattern.compile("^\\d{1,10}([,\\s])+[a-zA-Z \\u0400-\\u04FF]{1,15}");
        Pattern p1 = Pattern.compile("^[a-zA-Z \\u0400-\\u04FF]{1,15}([,\\s])+\\d{1,10}");
        return p.matcher(inputString).matches() || p1.matcher(inputString).matches();

    }

    public static boolean yoloRegExp(String inputString){
        Pattern p1 = Pattern.compile("^[/][a-zA-Z]{1,4}([,\\s])+\\d{1,10}");

        return p1.matcher(inputString).matches();

    }
    public static boolean yaSkazalYolo(String inputString){
        Pattern p1 = Pattern.compile("^[/][a-zA-Z]{1,4}");

        return p1.matcher(inputString).matches();

    }

    public static HashMap  delimNaPopalam(String inputString){
        float cost = 0;
        String action = "";

        HashMap hashMap = new HashMap();
        Pattern p = Pattern.compile("\\d{1,10}+");
        Matcher m = p.matcher(inputString);
        if (m.find())
            cost = Float.parseFloat(m.group());

        Pattern p1 = Pattern.compile("[a-zA-Z \\u0400-\\u04FF]{1,20}+");
        Matcher m1 = p1.matcher(inputString);
        if (m1.find())
            action = m1.group();

        hashMap.put("amount",cost);
        hashMap.put("action",action);

        return hashMap;
    }


}
