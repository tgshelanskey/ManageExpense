package com.example.com.example.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by woodw on 10/11/2017.
 */

//Shelanskey US9 - Created class to handle date manipulation
public class DateUtil {
    private static final String DATEFORMAT = "MM/dd/yyyy";

    private DateUtil(){}

    public static  Date convertTextToDate(String stringDate){
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
        return df.format(date);
    }

    public static Date getFirstDayOfMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();

    }
}
