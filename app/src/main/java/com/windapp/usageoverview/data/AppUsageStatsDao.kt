package com.windapp.usageoverview.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.windapp.usageoverview.data.entities.AppNameInfo
import com.windapp.usageoverview.data.entities.AppUsageTimelineStats
import com.windapp.usageoverview.data.entities.AppsUsageStats
import com.windapp.usageoverview.data.entities.BrowsingTimeStats
import kotlinx.coroutines.flow.Flow

@Dao
interface AppUsageStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimelineUsageStats(appUsageStats: AppUsageTimelineStats)

    @Delete
    suspend fun deleteTimelineUsageStats(appUsageStats: AppUsageTimelineStats)



    @Query("SELECT * FROM appusagetimelinestats ORDER BY timeStamp DESC")
    fun getAppTimelineUsageStats(): Flow<List<AppUsageTimelineStats>>



    @Query("DELETE FROM appusagetimelinestats")
     suspend fun deleteAllTimelineUsageStats()

     @Query("SELECT * FROM appusagetimelinestats WHERE packageName=:packageName")
     fun getAppTimelineUsageStatsByPackageName(packageName:String):Flow<List<AppUsageTimelineStats>>

     @Query("SELECT * FROM appusagetimelinestats WHERE date BETWEEN :startDate AND :endDate ORDER BY timeStamp DESC")
     fun getAppTimelineUsageStatsFromDateToDate(startDate:String, endDate:String):Flow<List<AppUsageTimelineStats>>

    @Query("SELECT * FROM appusagetimelinestats WHERE date=:date  ORDER BY timeStamp DESC")
    fun getAppTimelineUsageStatsByDate(date:String):Flow<List<AppUsageTimelineStats>>


    // App Name info

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAppNameInfo(appNameInfo: AppNameInfo)

    @Query("SELECT * FROM appnameinfo")
    fun getAppNameInfo():Flow<List<AppNameInfo>>

    @Query("UPDATE appnameinfo  SET excludeApp=:flag  WHERE packageName=:packName ")
 suspend   fun excludeApp(packName:String,flag:Boolean)

    @Query("SELECT * FROM appnameinfo WHERE excludeApp=1")
    fun getExcludedApps():Flow<List<AppNameInfo>>

    // App Usage Stats
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppsUsageStats(appsUsageStats: AppsUsageStats)

    @Query("SELECT * FROM appsusagestats")
    fun getAppsUsageStats():Flow<List<AppsUsageStats>>

    @Query("DELETE FROM appsusagestats")
    suspend fun deleteAllAppsUsageStats()

    // Browsing Time

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBrowsingTime(browsingTimeStats: BrowsingTimeStats)

    @Query("SELECT * FROM browsingtimestats")
    fun getBrowsingTime():Flow<List<BrowsingTimeStats>>

}