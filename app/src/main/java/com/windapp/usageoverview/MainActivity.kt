package com.windapp.usageoverview

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.windapp.usageoverview.MainActivity.helper.navController
import com.windapp.usageoverview.receiver.PhoneUnlockedReceiver
import com.windapp.usageoverview.service.AppUsageStatsService
import com.windapp.usageoverview.service.UrlAccessiblityService
import com.windapp.usageoverview.ui.theme.UsageOverviewTheme
import com.windapp.usageoverview.ui.usage_overview.UsageOverviewTabLayout
import com.windapp.usageoverview.ui.usage_overview.usage_timeline.SingleAppTimelineScreen
import com.windapp.usageoverview.ui.usage_overview.usage_timeline.UsageTimelineScreen
import com.windapp.usageoverview.ui.usage_overview.usage_timeline.exclude_apps.ExcludeAppsScreen
import com.windapp.usageoverview.util.ApplicationFetcher
import com.windapp.usageoverview.util.Routes
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    object helper{
        lateinit var navController: NavHostController
    }
  //  lateinit var receiver: PhoneUnlockedReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val calendarInstance = Calendar.getInstance()
    Log.d("date",    calendarInstance.get(Calendar.DATE).toString())


 /*       receiver = PhoneUnlockedReceiver()
        val filter = IntentFilter()
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.USER_PRESENT");
        registerReceiver(receiver, filter)*/

        if (!isAccessServiceEnabled(this, UrlAccessiblityService::class.java)) {

            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)

        }

        if (!isAccessGranted()) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        }

        startService(
            Intent(
                applicationContext,
                AppUsageStatsService::class.java
            ))

        ApplicationFetcher.getAppStats(this)
        setContent {
            UsageOverviewTheme {

                 navController= rememberNavController()

                NavHost(navController = navController,
                        startDestination = Routes.USAGE_OVERVIEW_TAB
                    ){
                    composable(Routes.USAGE_TIMELINE){
                        UsageTimelineScreen(onNavigate = {
                                                         navController.navigate(it.route)
                        })
                    }
                    composable(Routes.USAGE_OVERVIEW_TAB){
                        UsageOverviewTabLayout()
                    }

                    composable(Routes.EXCLUDE_APPS){
                        ExcludeAppsScreen()
                    }

                    composable(
                        route=Routes.SINGLE_USAGE_TIMELINE+"?packageName={packageName}",
                        arguments = listOf(
                            navArgument(name="packageName"){
                                type=NavType.StringType
                                defaultValue="com.stayfocused"
                            }
                        )
                    ){
                        SingleAppTimelineScreen(
                            onPopBackStack = {
                                navController.popBackStack()
                            }
                        )
                    }

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
   //     unregisterReceiver(receiver)

    }

    private fun isAccessGranted(): Boolean {
        return try {
            val packageManager = packageManager
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            val appOpsManager = getSystemService(APP_OPS_SERVICE) as AppOpsManager
            var mode = 0
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                mode = appOpsManager.checkOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    applicationInfo.uid, applicationInfo.packageName
                )
            }
            mode == AppOpsManager.MODE_ALLOWED
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun isAccessServiceEnabled(context: Context, accessibilityServiceClass: Class<*>): Boolean {
        val prefString = Settings.Secure.getString(context.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
        return prefString != null && prefString.contains(context.packageName + "/" + accessibilityServiceClass.name)
    }
}


