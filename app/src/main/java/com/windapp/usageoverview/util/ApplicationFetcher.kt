package com.windapp.usageoverview.util

import android.app.ActivityManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.text.format.DateUtils
import android.util.Log
import java.sql.Timestamp
import java.text.DateFormat
import java.util.*


/**
 * This is class is used to provide the following functionalities
 *      -   A List of Installed User Applications
 *      -   Recently Opened Application (Can be used to detect the current foreground application
 */

class ApplicationFetcher {

    /*******************************************************************************************************************
     *                                          STATIC METHODS
     ******************************************************************************************************************/
    companion object {

        private const val HOUR_RANGE : Int = 1000 * 3600 * 24

        /**
         * @param context The current context of the application.
         * @return A list of User Installed Applications in the form of a custom wrapper.
         *
         * This method is used to return a list of User Installed Applications by accessing the Package manager of the
         * current context.It also excludes the pre-installed System-Applications by checking the flag of the
         * particular ApplicationInfo.
         */
        fun getPackageInfo(context: Context,packageName:String):ApplicationInfoWrapper?{

            try {
                val packageManager = context.packageManager
                val app = context.getPackageManager().getApplicationInfo(packageName, 0)
                val iconDrawable = packageManager.getApplicationIcon(app)
                val appName = packageManager.getApplicationLabel(app).toString()
                return ApplicationInfoWrapper(appName,iconDrawable,packageName)
            }
            catch (e:NameNotFoundException){
                e.printStackTrace()

            }
            return null


        }

        open fun getInstalledApps(ctx: Context): List<ApplicationInfoWrapper> {
            val packageManager = ctx.packageManager
            val allInstalledPackages =
                packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
            val installedApplications = ArrayList<ApplicationInfoWrapper>()
            val defaultActivityIcon = packageManager.defaultActivityIcon


            for (each in allInstalledPackages) {
                if (ctx.packageName == each.packageName) {
                    continue  // skip own app
                }
                try {
                    // add only apps with application icon
                    val intentOfStartActivity =
                        packageManager.getLaunchIntentForPackage(each.packageName)
                            ?: continue
                    val applicationIcon = packageManager.getApplicationIcon(each.applicationInfo)
                    val applicationName = packageManager.getApplicationLabel(each.applicationInfo).toString()

                    if (applicationIcon != null && defaultActivityIcon != applicationIcon) {
                        installedApplications.add(ApplicationInfoWrapper(applicationName,applicationIcon,each.packageName))
                    }
                } catch (e: NameNotFoundException) {
                    Log.i("MyTag", "Unknown package name " + each.packageName)
                }
            }
            return installedApplications
        }
        public fun getInstalledApplications(context: Context) : List<ApplicationInfoWrapper> {
            val packageManager = context.packageManager
            if(packageManager == null)
                return Collections.emptyList<ApplicationInfoWrapper>()
            // Querying the PackageManager to get all the InstalledApplications by passing 0 flags
            val applications = packageManager.getInstalledApplications(0)
            if(applications == null)
                return Collections.emptyList<ApplicationInfoWrapper>()
            val installedApplications = ArrayList<ApplicationInfoWrapper>()
            for(applicationInfo in applications) {
         //       if(isSystemApplication(applicationInfo))


                    val applicationName = packageManager.getApplicationLabel(applicationInfo).toString()
                    val applicationIcon = packageManager.getApplicationIcon(applicationInfo)
                    installedApplications.add(ApplicationInfoWrapper(applicationName,applicationIcon,applicationInfo.packageName))
            }
            return installedApplications
        }

        /**
         * @param context The context from which the active app needs to be identified.
         * @return The name of the application currently active,
         *         if no active application is found it returns null.
         *
         * This method is used to return the current active application on the given context by using the Usage Stats
         * Manager.
         */
        fun getAppStats(context: Context){

       //     Log.d("cal",DateUtils.formatSameDayTime(  cal.timeInMillis,System.currentTimeMillis(), DateFormat.MEDIUM, DateFormat.MEDIUM).toString())
            var foregroundAppPackageName : String? = null
            val currentTime = System.currentTimeMillis()
            val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val stats=usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,currentTime-1000,System.currentTimeMillis())
            stats.forEach {

                val usageTime=   DateUtils.formatElapsedTime(it.totalTimeInForeground/1000 )


                if(!usageTime.equals("00:00")){
                    val lastTimeUsed=    DateUtils.formatSameDayTime(  it.getLastTimeUsed(),System.currentTimeMillis(), DateFormat.MEDIUM, DateFormat.MEDIUM)


                    Log.d("usages:",it.packageName+" lastTime:"+lastTimeUsed+" usageTime: "+usageTime)
                }


            }

    }

        fun getDateCurrentTimeZone(timestamp: Long): Date? {
           val ts =Timestamp(timestamp)
            return Date(ts.time)
        }



        public fun printForegroundTask(context: Context): String? {
            var currentApp = "NULL"
                val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
                val time = System.currentTimeMillis()
                val appList =
                    usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time)
                if (appList != null && appList.size > 0) {
                    val mySortedMap: SortedMap<Long, UsageStats> = TreeMap()
                    for (usageStats in appList) {
                        mySortedMap[usageStats.lastTimeUsed] = usageStats
                    }
                    if (mySortedMap != null && !mySortedMap.isEmpty()) {
                        currentApp = mySortedMap[mySortedMap.lastKey()]!!.packageName
                    //    usageStats=mySortedMap[mySortedMap.lastKey()]!!
                    }
                }

            Log.e("adapter", "Current App in foreground is: $currentApp")
            return currentApp
        }

        /**
         * @param applicationInfo The ApplicationInfo of the respective application.
         * @return If the application is SystemApplication then true, else it returns false.
         *
         * This is just a service method that is used to indicate whether the given application is an System Installed
         * Application or a User Installed Application.
         */
        private fun isSystemApplication(applicationInfo: ApplicationInfo) :Boolean {
            if((applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0)
                return true
            return false
        }
    }


}