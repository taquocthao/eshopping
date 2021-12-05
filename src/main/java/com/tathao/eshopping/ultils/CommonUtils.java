package com.tathao.eshopping.ultils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CommonUtils {

    public static String generateCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyhhmmssSSSS");
        return sdf.format(new Date());
    }
}
