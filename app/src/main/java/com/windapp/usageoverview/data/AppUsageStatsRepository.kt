package com.windapp.usageoverview.data


import com.windapp.usageoverview.data.entities.AppNameInfo
import com.windapp.usageoverview.data.entities.AppUsageTimelineStats
import com.windapp.usageoverview.data.entities.AppsUsageStats
import com.windapp.usageoverview.data.entities.BrowsingTimeStats
import kotlinx.coroutines.flow.Flow

interface AppUsageStatsRepository {

    // App stats
    suspend fun insertTimelineUsageStats(appUsageStats: AppUsageTimelineStats)

    fun getAppTimelineUsageStats(): Flow<List<AppUsageTimelineStats>>

    suspend fun deleteAllTimelineUsageStats()

    fun getAppTimelineUsageStatsByPackageName(packageName:String):Flow<List<AppUsageTimelineStats>>

    fun getAppTimelineUsageStatsFromDateToDate(startDate:String, endDate:String):Flow<List<AppUsageTimelineStats>>

    fun getAppTimelineUsageStatsByDate(date:String):Flow<List<AppUsageTimelineStats>>

    suspend fun deleteTimelineUsageStats(appUsageStats: AppUsageTimelineStats)


    // App info

    suspend fun insertAppNameInfo(appNameInfo: AppNameInfo)

    fun getAppNameInfo():Flow<List<AppNameInfo>>

  suspend  fun excludeApp(packName:String,flag:Boolean)

    fun getExcludedApps():Flow<List<AppNameInfo>>

    // App Usage Stats
    suspend fun insertAppsUsageStats(appsUsageStats: AppsUsageStats)

    fun getAppsUsageStats():Flow<List<AppsUsageStats>>

    suspend fun deleteAllAppsUsageStats()


    // Browsing Time

    fun getBrowsingTime():Flow<List<BrowsingTimeStats>>

    suspend fun insertBrowsingTime(browsingTimeStats: BrowsingTimeStats)

}