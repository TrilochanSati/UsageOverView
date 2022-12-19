package com.windapp.usageoverview.util;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class UtilUsageTimeline {

    static long[] timeRange;

    public static List<Applist> getApps(Context context,String date) {
        timerangeset(date);

        UsageEvents usageEvents;
        int intValue;
        ArrayList<Applist> arrayList = new ArrayList();
        int i = 1;
        try {
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

            usageEvents = usm.queryEvents(timeRange[0], timeRange[1]);
        } catch (Throwable unused) {
            usageEvents = null;
        }
        String str = "";
        long j = 0;
        if (usageEvents != null) {
            UsageEvents.Event event = new UsageEvents.Event();
            ArrayList<Long> arrayList2 = new ArrayList();
            while (usageEvents.getNextEvent(event)) {
                String packageName = event.getPackageName();
                long timeStamp = event.getTimeStamp();
                if (event.getEventType() == i) {
                    arrayList2.add(Long.valueOf(timeStamp));
                    str = packageName;
                    arrayList2 = arrayList2;
                } else if (!str.equals(packageName) || event.getEventType() != 2) {
                    arrayList2 = arrayList2;
                    str = str;
                } else {
                    arrayList2.add(Long.valueOf(timeStamp));
                    long j2 = 0;
                    long j3 = 0;
                    long j4 = 0;
                    for (Long l : arrayList2) {
                        long longValue = l.longValue();
                        if (j2 >= 1) {
                            j4 += longValue - j2;
                        }
                        if (j3 == 0) {
                            j3 = longValue;
                        }
                        j2 = longValue;
                    }
                    if (j4 >= 1000) {
                        arrayList2 = arrayList2;
                        str = str;
                        arrayList.add(new Applist(null, "", str, j3, j4,timeStamp));
                    } else {
                        arrayList2 = arrayList2;
                        str = str;
                    }
                    arrayList2.clear();
                }
                i = 1;
            }
        }
        ArrayList arrayList3 = new ArrayList();
        if (arrayList.size() > 0) {
            String str2 = "";
            long j5 = 0;
            long j6 = 0;
            for (Applist applist : arrayList) {
                String pkg = applist.getPkg();
                long usedtime = applist.getUsedtime();
                if (!str2.equals(pkg)) {
                    if (j6 >= j) {
                        String str3 = usedtime + "";
                        j5 = usedtime;
                        arrayList3.add(new Applist(getIcon(context,pkg), getAppname(context,pkg), str2, j6, (str3.length() < 4 || (intValue = Integer.valueOf(str3.substring(str3.length() + (-3), str3.length())).intValue()) < 550) ? j5 : j5 + (1000 - intValue),applist.timeStamp));
                    } else {
                        j5 = usedtime;
                    }
                    j6 = applist.getTime();
                    str2 = pkg;
                } else {
                    j5 += usedtime;
                }
                j = 0;
            }
        }

        return  arrayList3;
    }
    public static Drawable getIcon(Context context, String packageName){
        PackageInfo packageInfo = null;
        Drawable loadIcon=null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            loadIcon = packageInfo.applicationInfo.loadIcon(context.getPackageManager());

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return loadIcon;
    }

    public static String getAppname(Context context, String packageName){
        PackageInfo packageInfo = null;
        String appName=null;

        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appName;
    }
    public static void timerangeset(String str) {
        if (str.equals("")) {
            timeRange = new long[]{Long.valueOf(datetime.todaystart()).longValue() * 1000, Long.valueOf(datetime.todayend()).longValue() * 1000};
        } else {
            timeRange = new long[]{Long.valueOf(datetime.daystart(str, "dd/MM/yyyy")).longValue() * 1000, Long.valueOf(datetime.dayend(str, "dd/MM/yyyy")).longValue() * 1000};
        }
      //  this.datedetails.setText(datetime.m1d((this.timeRange[0] / 1000) + "", "EEEE dd, MMM yyyy"));
    }


    public static class Applist {
        Drawable icon;
        String name;
        String pkg;
        long time;
        long usedtime;
        long timeStamp;

        public Applist(Drawable drawable, String str, String str2, long j, long j2,long timestamp) {
            this.icon = drawable;
            this.name = str;
            this.pkg = str2;
            this.time = j;
            this.usedtime = j2;
            this.timeStamp=timestamp;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public Drawable getIcon() {
            return this.icon;
        }

        public void setIcon(Drawable drawable) {
            this.icon = drawable;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public String getPkg() {
            return this.pkg;
        }

        public void setPkg(String str) {
            this.pkg = str;
        }

        public long getTime() {
            return this.time;
        }

        public void setTime(long j) {
            this.time = j;
        }

        public long getUsedtime() {
            return this.usedtime;
        }

        public void setUsedtime(long j) {
            this.usedtime = j;
        }
    }


}
