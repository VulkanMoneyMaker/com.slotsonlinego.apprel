package com.slotsonlinego.apprel.utils;


import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class DateUtils {

    private static final int[] timezone = {2,3,4,7,8,9,10,11,12};

    private DateUtils() {}

    @SuppressLint("SimpleDateFormat")
    public static long parseDate(String format, String dateTime) {
        long time = System.currentTimeMillis();
        try {
            DateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(dateTime);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static boolean isNeedTimeZones() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            TimeZone tz = TimeZone.getDefault();
            Date now = new Date();
            int offsetFromUtc = tz.getOffset(now.getTime()) / 1000 / 3600;
            for (int item : timezone) {
                if (offsetFromUtc == item)
                    return true;
            }
        } else {
            return true;
        }
        return false;
    }
}
