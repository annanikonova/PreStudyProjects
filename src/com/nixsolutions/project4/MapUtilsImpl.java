package com.nixsolutions.project4;

import interfaces.task4.MapUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapUtilsImpl implements MapUtils{
    public static final int WORD_LENGTH = 3;

    public Map<String, Integer> findThrees(String input) {
        if(input==null) {
            throw new NullPointerException("Null string given");
        }
        Map<String,Integer> result = new HashMap<String,Integer>();
        if (input.length()<WORD_LENGTH) {
            //size of returned dictionary will be 0
           return result;
        }

        for (int i=0; i<=input.length()-WORD_LENGTH; i++){
            String substring = input.substring(i, i+WORD_LENGTH);
            if(isAllowed(substring)) {
                int count = result.getOrDefault(substring, 0);
                result.put(substring, count+1);
            }

        }
        return result;

    }

    public boolean isAllowed (String substring) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{"+WORD_LENGTH+"}$");
        Matcher matcher = pattern.matcher(substring);
        return matcher.matches();

    }

    public static void main(String[] args) {
        MapUtilsImpl utils = new MapUtilsImpl();
        String test1 = "1234 56_789_789";
        String test2 = "1234 345_23";
        String test3 = "bla Bla bla String_ing";
        Map<String,Integer> map1 = utils.findThrees(test1);
        Map<String,Integer> map2 = utils.findThrees(test2);
        Map<String,Integer> map3 = utils.findThrees(test3);
        System.out.println("Dictionary for string "
                +test1+" : "+map1);
        System.out.println("Dictionary for string "
                +test2+" : "+map2);
        System.out.println("Dictionary for string "
                +test3+" : "+map3);
        System.out.print("Dictionary for null: ");
        try {
            Map<String,Integer> map4 = utils.findThrees(null);
            System.out.println(map4);
        }
        catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        String shortString = "ab";
        System.out.println("Dictionary for short string "
                + shortString +" : " + utils.findThrees(shortString));
        String emptyString = "";
        System.out.println("Dictionary for empty string "
                + " : " + utils.findThrees(emptyString));

    }

}