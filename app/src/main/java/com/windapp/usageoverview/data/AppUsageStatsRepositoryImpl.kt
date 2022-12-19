package com.windapp.usageoverview.data

import com.windapp.usageoverview.data.entities.AppNameInfo
import com.windapp.usageoverview.data.entities.AppUsageTimelineStats
import com.windapp.usageoverview.data.entities.AppsUsageStats
import com.windapp.usageoverview.data.entities.BrowsingTimeStats
import kotlinx.coroutines.flow.Flow

class AppUsageStatsRepositoryImpl(
    private val dao:AppUsageStatsDao
) :AppUsageStatsRepository{
    override suspend fun insertTimelineUsageStats(appUsageStats: AppUsageTimelineStats) {
        dao.insertTimelineUsageStats(appUsageStats)
    }

    override fun getAppTimelineUsageStats(): Flow<List<AppUsageTimelineStats>> {
         return  dao.getAppTimelineUsageStats()
    }

    override suspend fun deleteAllTimelineUsageStats() {
        dao.deleteAllTimelineUsageStats()
    }

    override fun getAppTimelineUsageStatsByPackageName(packageName: String): Flow<List<AppUsageTimelineStats>> {
      return  dao.getAppTimelineUsageStatsByPackageName(packageName)
    }

    override fun getAppTimelineUsageStatsFromDateToDate(
        startDate: String,
        endDate: String
    ): Flow<List<AppUsageTimelineStats>> {
        return dao.getAppTimelineUsageStatsFromDateToDate(startDate, endDate)
    }

    override fun getAppTimelineUsageStatsByDate(date: String): Flow<List<AppUsageTimelineStats>> {
        return  dao.getAppTimelineUsageStatsByDate(date)
    }

    override suspend fun deleteTimelineUsageStats(appUsageStats: AppUsageTimelineStats) {
        dao.deleteTimelineUsageStats(appUsageStats)
    }

    override suspend fun insertAppNameInfo(appNameInfo: AppNameInfo) {
        dao.insertAppNameInfo(appNameInfo)
    }

    override fun getAppNameInfo(): Flow<List<AppNameInfo>> {
        return dao.getAppNameInfo()
    }

    override suspend fun excludeApp(packName: String, flag: Boolean) {
        dao.excludeApp(packName,flag)
    }

    override fun getExcludedApps(): Flow<List<AppNameInfo>> {
       return dao.getExcludedApps()
    }

    override suspend fun insertAppsUsageStats(appsUsageStats: AppsUsageStats) {
        dao.insertAppsUsageStats(appsUsageStats)
    }

    override fun getAppsUsageStats(): Flow<List<AppsUsageStats>> {
        return dao.getAppsUsageStats()
        }

    override suspend fun deleteAllAppsUsageStats() {
        dao.deleteAllAppsUsageStats()
    }

    override fun getBrowsingTime(): Flow<List<BrowsingTimeStats>> {
        return dao.getBrowsingTime()
    }

    override suspend fun insertBrowsingTime(browsingTimeStats: BrowsingTimeStats) {
    dao.insertBrowsingTime(browsingTimeStats)
    }
}