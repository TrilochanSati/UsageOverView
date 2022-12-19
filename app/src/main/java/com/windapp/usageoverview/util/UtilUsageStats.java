package com.windapp.usageoverview.util;

import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.windapp.usageoverview.UsageOverviewApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

   public class  UtilUsageStats {

   static long[] timeRange;
 public static   int loadlistsof = 0;
  static   public List<Applist> getApps(String tRange,int loadOf) {

      loadlistsof=loadOf;
        timerangeset(tRange);

        UsageEvents usageEvents;
        boolean z;
        Map<String, Long> map;
        int intValue;
        ArrayList<Applist> arrayList = new ArrayList();
        try {
            usageEvents = ((UsageStatsManager) UsageOverviewApp.APP.context.getSystemService(Context.USAGE_STATS_SERVICE)).queryEvents(timeRange[0], timeRange[1]);
        } catch (Throwable unused) {
            usageEvents = null;
        }
        String str = "";
        if (usageEvents != null) {
            UsageEvents.Event event = new UsageEvents.Event();
            ArrayList<Long> arrayList2 = new ArrayList();
            while (usageEvents.getNextEvent(event)) {
                String packageName = event.getPackageName();
                long timeStamp = event.getTimeStamp();
                if (event.getEventType() == 1) {
                    arrayList2.add(Long.valueOf(timeStamp));
                    str = packageName;
                    arrayList2 = arrayList2;
                    usageEvents = usageEvents;
                    event = event;
                } else if (!str.equals(packageName) || event.getEventType() != 2) {
                    str = str;
                    arrayList2 = arrayList2;
                    usageEvents = usageEvents;
                    event = event;
                } else {
                    arrayList2.add(Long.valueOf(timeStamp));
                    long j = 0;
                    long j2 = 0;
                    long j3 = 0;
                    for (Long l : arrayList2) {
                        long longValue = l.longValue();
                        if (j2 >= 1) {
                            j += longValue - j2;
                        }
                        if (j3 == 0) {
                            j3 = longValue;
                        }
                        j2 = longValue;
                    }
                    str = str;
                    arrayList2 = arrayList2;
                    usageEvents = usageEvents;
                    event = event;
                    arrayList.add(new Applist(null, "", str, j3, j <= 1000 ? 1000L : j, 0, 0L));
                    arrayList2.clear();
                }
            }
        }
        ArrayList<Applist> arrayList3 = new ArrayList();
        if (arrayList.size() > 0) {
            String str2 = "";
            long j4 = 0;
            long j5 = 0;
            for (Applist applist : arrayList) {
                String pkg = applist.getPkg();
                long usedtime = applist.getUsedtime();
                if (!str2.equals(pkg)) {
                    if (j5 >= 0) {
                        String str3 = usedtime + "";
                        j4 = usedtime;
                        str2 = pkg;
                        arrayList3.add(new Applist(null, "", str2, j5, (str3.length() < 4 || (intValue = Integer.valueOf(str3.substring(str3.length() + (-3), str3.length())).intValue()) < 550) ? j4 : j4 + (1000 - intValue), 0, 0L));
                    } else {
                        j4 = usedtime;
                        str2 = pkg;
                    }
                    j5 = applist.getTime();
                } else {
                    j4 += usedtime;
                }
            }
        }
        ArrayList arrayList4 = new ArrayList();
        if (arrayList3.size() > 0) {
            HashMap hashMap = new HashMap();
            if (Build.VERSION.SDK_INT >= 23) {
                map = getMobileData((TelephonyManager) UsageOverviewApp.APP.context.getSystemService(Context.TELEPHONY_SERVICE), (NetworkStatsManager) UsageOverviewApp.APP.context.getSystemService(Context.NETWORK_STATS_SERVICE));
                z = true;
            } else {
                map = hashMap;
                z = false;
            }
            ArrayList arrayList5 = new ArrayList();
            for (Applist applist2 : arrayList3) {
                arrayList5.add(applist2.getPkg());
            }
            HashSet<String> hashSet = new HashSet(arrayList5);
            hashSet.toArray(new String[hashSet.size()]);
            PackageManager packageManager = UsageOverviewApp.APP.context.getPackageManager();
            for (String str4 : hashSet) {
                if (!AppUtil.openable(packageManager, str4)) {
                    map = map;
                    packageManager = packageManager;
                } else if (z) {
                    long j6 = 0;
                    long j7 = 0;
                    int i = 0;
                    for (Applist applist3 : arrayList3) {
                        if (str4.equals(applist3.getPkg())) {
                            j7 += applist3.getUsedtime();
                            if (j6 == 0) {
                                j6 = applist3.getTime();
                            }
                            i++;
                        }
                    }
                    String str5 = "u" + AppUtil.getAppUid(packageManager, str4);
                    map = map;
                    packageManager = packageManager;
                    arrayList4.add(new Applist(AppUtil.getPackageIcon(UsageOverviewApp.APP.context, str4), AppUtil.parsePackageName(packageManager, str4), str4, j6, j7, i, (map.size() <= 0 || !map.containsKey(str5)) ? 0L : map.get(str5).longValue()));
                } else {
                    map = map;
                    packageManager = packageManager;
                }
            }
        }
        arrayList.clear();
        return arrayList4;
    }

    public static class Applist {
        int count;
        Drawable icon;
        String name;
        String pkg;
        long time;
        long useddata;
        long usedtime;

        public Applist(Drawable drawable, String str, String str2, long j, long j2, int i, long j3) {
            this.icon = drawable;
            this.name = str;
            this.pkg = str2;
            this.time = j;
            this.usedtime = j2;
            this.count = i;
            this.useddata = j3;
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

        public int getCount() {
            return this.count;
        }

        public void setCount(int i) {
            this.count = i;
        }

        public long getUseddata() {
            return this.useddata;
        }

        public void setUseddata(long j) {
            this.useddata = j;
        }
    }

    private static Map<String, Long> getMobileData(TelephonyManager telephonyManager, NetworkStatsManager networkStatsManager) {
        NetworkStats querySummary;
        HashMap hashMap = new HashMap();
        if (ActivityCompat.checkSelfPermission(UsageOverviewApp.APP.context, "android.permission.READ_PHONE_STATE") == 0) {
            try {
                if (Build.VERSION.SDK_INT >= 23 && (querySummary = networkStatsManager.querySummary(0, telephonyManager.getSubscriberId(), timeRange[0], timeRange[1])) != null) {
                    while (querySummary.hasNextBucket()) {
                        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
                        querySummary.getNextBucket(bucket);
                        String str = "u" + bucket.getUid();
                        Log.d("******", str + " " + bucket.getTxBytes() + "");
                        if (hashMap.containsKey(str)) {
                            hashMap.put(str, Long.valueOf(((Long) hashMap.get(str)).longValue() + bucket.getTxBytes() + bucket.getRxBytes()));
                        } else {
                            hashMap.put(str, Long.valueOf(bucket.getTxBytes() + bucket.getRxBytes()));
                        }
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e(">>>>>", e.getMessage());
            }
        }
        return hashMap;
    }

  static   public void timerangeset(String str) {
        if (str.equals("")) {
            timeRange = AppUtil.getTimeRange(SortEnum.getSortEnum(loadlistsof));
        } else {
            timeRange = new long[]{Long.valueOf(datetime.daystart(str, "dd/MM/yyyy")).longValue() * 1000, Long.valueOf(datetime.dayend(str, "dd/MM/yyyy")).longValue() * 1000};
        }
    }

}


