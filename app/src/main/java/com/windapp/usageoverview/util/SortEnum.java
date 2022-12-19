package com.windapp.usageoverview.util;

public enum SortEnum {
    TODAY(0),
    YESTERDAY(1),
    THIS_WEEK(2),
    LAST_WEEK(3),
    THIS_MONTH(4),
    LAST_MONTH(5),
    THIS_YEAR(6),
    LAST_YEAR(7);

    int sort;

    SortEnum(int i) {
        this.sort = i;
    }

    public static SortEnum getSortEnum(int i) {
        switch (i) {
            case 0:
                return TODAY;
            case 1:
                return YESTERDAY;
            case 2:
                return THIS_WEEK;
            case 3:
                return LAST_WEEK;
            case 4:
                return THIS_MONTH;
            case 5:
                return LAST_MONTH;
            case 6:
                return THIS_YEAR;
            case 7:
                return LAST_YEAR;
            default:
                return TODAY;
        }
    }
}