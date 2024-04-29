package com.example.enroute.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    public static Date ParseFromUTCString(String str) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.US);
        Date date = new Date();

        try {
            date = inputFormat.parse(str);
        } catch (ParseException e) {
        }

        return date;
    }
}
