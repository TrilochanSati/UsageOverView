package com.windapp.usageoverview.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
/* loaded from: classes.dex */
public class datetime {
    public static String todaystart() {
        try {
            Date date = new Date((System.currentTimeMillis() / 1000) * 1000);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(gtmzone.TimeZone()));
            return String.valueOf(new SimpleDateFormat("dd/MM/yyyy").parse(simpleDateFormat.format(date)).getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String todayend() {
        try {
            Date date = new Date((System.currentTimeMillis() / 1000) * 1000);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String tz = gtmzone.TimeZone();
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(tz));
            String str = simpleDateFormat.format(date) + " 11:59:59 PM";
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone(tz));
            return String.valueOf(simpleDateFormat2.parse(str).getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String daystart(String str, String str2) {
        try {
            String t = gtmzone.m0t();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(t));
            return String.valueOf(simpleDateFormat.parse(str).getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String dayend(String str, String str2) {
        try {
            String t = gtmzone.m0t();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2 + " hh:mm:ss a");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(t));
            return String.valueOf(simpleDateFormat.parse(str + " 11:59:59 PM").getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return str;
        }
    }

    /* renamed from: d */
    public static String m2d() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /* renamed from: d */
    public static String m1d(String str, String str2) {
        try {
            if (str.equals("")) {
                return "";
            }
            String t = gtmzone.m0t();
            Date date = new Date(Long.parseLong(str) * 1000);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(t));
            return simpleDateFormat.format(date);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return str;
        }
    }

    /* renamed from: b */
    public static String m3b(String str, String str2) {
        try {
            if (str.equals("")) {
                return "";
            }
            String t = gtmzone.m0t();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(t));
            return String.valueOf(simpleDateFormat.parse(str).getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return str;
        }
    }

    public static long atStartOfDay(long j) {
        Date date = new Date(j * 1000);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        return instance.getTimeInMillis() / 1000;
    }

    public static long atEndOfDay(long j) {
        Date date = new Date(j * 1000);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(11, 23);
        instance.set(12, 59);
        instance.set(13, 59);
        instance.set(14, 999);
        return instance.getTimeInMillis() / 1000;
    }
}