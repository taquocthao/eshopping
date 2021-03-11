package com.tathao.eshopping.ultils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    public static String generateCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyhhmmss");
        return sdf.format(new Date());
    }
}
