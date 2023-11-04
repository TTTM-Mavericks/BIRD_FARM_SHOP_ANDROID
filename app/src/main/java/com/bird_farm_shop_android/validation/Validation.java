package com.bird_farm_shop_android.validation;

import java.text.SimpleDateFormat;

public class Validation {
    public static boolean checkValidNumber(String input)
    {
        for(Character ch : input.toCharArray())
        {
            if(Character.isAlphabetic(ch)) return false;
        }
        return true;
    }

    public static boolean checkValidAlphabet(String input)
    {
        for(Character ch : input.toCharArray())
        {
            if(Character.isDigit(ch)) return false;
        }
        return true;
    }

    public static boolean checkValidDOB(String input)
    {
        try
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(input);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
