package com.example.com.example.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by woodw on 10/11/2017.
 */

public class DateUtil {
    public static final String DATEFORMAT = "mm/dd/yyyy";
    public static Date convertTextToDate(String stringDate){
        DateFormat df = new SimpleDateFormat(DATEFORMAT);

        Date newDate = null;
        try {
            newDate = df.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newDate;
    }
    public static String convertDateToText(Date date){
        DateFormat df = new SimpleDateFormat(DATEFORMAT);
        String newDate = df.format(date);
        return newDate;
    }
}
