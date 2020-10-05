package com.github.kongpf8848.pageadapter.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getCurrentDateTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String strDate = simpleDateFormat.format(new Date());
        return strDate;
    }
}
