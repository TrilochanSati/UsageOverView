package com.windapp.usageoverview.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.windapp.usageoverview.R;

import java.util.Calendar;

/* loaded from: classes.dex */
public final class AppUtil {
    private static final long A_DAY = 86400000;

    public static String parsePackageName(PackageManager packageManager, String str) {
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(str, 128);
        } catch (PackageManager.NameNotFoundException unused) {
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            return str;
        }
        return ((Object) packageManager.getApplicationLabel(applicationInfo)) + "";
    }

    public static Drawable getPackageIcon(Context context, String str) {
        try {
            return context.getPackageManager().getApplicationIcon(str);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return context.getResources().getDrawable(R.drawable.ic_launcher_foreground);
        }
    }

    public static String formatMilliSeconds(long j) {
        long j2 = j / 1000;
        if (j2 < 60) {
            return String.format("%ss", Long.valueOf(j2));
        }
        if (j2 < 3600) {
            return String.format("%sm %ss", Long.valueOf(j2 / 60), Long.valueOf(j2 % 60));
        }
        long j3 = j2 % 3600;
        return String.format("%sh %sm %ss", Long.valueOf(j2 / 3600), Long.valueOf(j3 / 60), Long.valueOf(j3 % 60));
    }

    public static boolean isSystemApp(PackageManager packageManager, String str) {
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
            if (applicationInfo == null) {
                return false;
            }
            if ((applicationInfo.flags & 1) == 0) {
                if ((applicationInfo.flags & 128) == 0) {
                    return false;
                }
            }
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isInstalled(PackageManager packageManager, String str) {
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            applicationInfo = null;
        }
        return applicationInfo != null;
    }

    public static boolean openable(PackageManager packageManager, String str) {
        return packageManager.getLaunchIntentForPackage(str) != null;
    }

    public static int getAppUid(PackageManager packageManager, String str) {
        try {
            return packageManager.getApplicationInfo(str, 0).uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }


    private static long[] getTodayRange() {
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        return new long[]{instance.getTimeInMillis(), currentTimeMillis};
    }

    private static long[] getYesterday() {
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis - A_DAY);
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        long timeInMillis = instance.getTimeInMillis();
        long j = timeInMillis + A_DAY;
        if (j <= currentTimeMillis) {
            currentTimeMillis = j;
        }
        return new long[]{timeInMillis, currentTimeMillis};
    }

    private static long[] getThisWeek() {
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.set(7, 2);
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        long timeInMillis = instance.getTimeInMillis();
        System.out.println("getThisWeek");
        return new long[]{timeInMillis, currentTimeMillis};
    }

    private static long[] getLastWeek() {
        Calendar instance = Calendar.getInstance();
        instance.set(7, 2);
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        System.out.println("getLastWeek");
        return new long[]{instance.getTimeInMillis() - 604800000, instance.getTimeInMillis() - 1};
    }

    private static long[] getThisMonth() {
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.set(5, 1);
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        System.out.println("getThisMonth");
        return new long[]{instance.getTimeInMillis(), currentTimeMillis};
    }

    private static long[] getLastMonth() {
        System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.add(2, -1);
        instance.set(5, 1);
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        long timeInMillis = instance.getTimeInMillis();
        Calendar instance2 = Calendar.getInstance();
        instance2.set(5, 1);
        instance2.set(11, 0);
        instance2.set(12, 0);
        instance2.set(13, 0);
        instance2.set(14, 0);
        System.out.println("getLastMonth");
        return new long[]{timeInMillis, instance2.getTimeInMillis() - 1};
    }

    private static long[] getThisYear() {
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.set(1, Calendar.getInstance().get(1));
        instance.set(2, 0);
        instance.set(5, 1);
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        return new long[]{instance.getTimeInMillis(), currentTimeMillis};
    }

    private static long[] getLastYear() {
        Calendar instance = Calendar.getInstance();
        instance.set(1, Calendar.getInstance().get(1) - 1);
        instance.set(2, 0);
        instance.set(5, 1);
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        long timeInMillis = instance.getTimeInMillis();
        Calendar instance2 = Calendar.getInstance();
        instance2.set(1, Calendar.getInstance().get(1));
        instance2.set(2, 0);
        instance2.set(5, 1);
        instance2.set(11, 0);
        instance2.set(12, 0);
        instance2.set(13, 0);
        instance2.set(14, 0);
        return new long[]{timeInMillis, instance2.getTimeInMillis() - 1};
    }

    public static long[] getTimeRange(SortEnum sortEnum) {
        switch (sortEnum) {
            case TODAY:
                return getTodayRange();
            case YESTERDAY:
                return getYesterday();
            case THIS_WEEK:
                return getThisWeek();
            case LAST_WEEK:
                return getLastWeek();
            case THIS_MONTH:
                return getThisMonth();
            case LAST_MONTH:
                return getLastMonth();
            case THIS_YEAR:
                return getThisYear();
            case LAST_YEAR:
                return getLastYear();
            default:
                return getTodayRange();
        }
    }

}