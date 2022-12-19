package com.windapp.usageoverview.util;

import java.util.TimeZone;

public class gtmzone {
    /* renamed from: t */
    public static String m0t() {
        return TimeZone.getDefault().getID();
    }

    public static String TimeZone() {
        return TimeZone.getDefault().getDisplayName(false, 0);
    }
}