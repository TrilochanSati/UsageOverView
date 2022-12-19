package com.windapp.usageoverview.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.windapp.usageoverview.data.entities.AppNameInfo
import com.windapp.usageoverview.data.entities.AppUsageTimelineStats
import com.windapp.usageoverview.data.entities.AppsUsageStats
import com.windapp.usageoverview.data.entities.BrowsingTimeStats

@Database(
    entities = [AppUsageTimelineStats::class,AppNameInfo::class, AppsUsageStats::class,BrowsingTimeStats::class],
    version = 1
)
abstract  class AppUsageStatsDatabase:RoomDatabase() {
    abstract val dao:AppUsageStatsDao
}