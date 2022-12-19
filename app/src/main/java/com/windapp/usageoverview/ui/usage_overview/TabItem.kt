package com.windapp.usageoverview.ui.usage_overview

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.windapp.usageoverview.MainActivity
import com.windapp.usageoverview.R
import com.windapp.usageoverview.ui.usage_overview.app_launches.AppLanuchesScreen
import com.windapp.usageoverview.ui.usage_overview.browsing_time.BrowsingTimeScreen
import com.windapp.usageoverview.ui.usage_overview.screen_unlocks.ScreenUnlocksScreen
import com.windapp.usageoverview.ui.usage_overview.usage_stats.UsageStatsScreen
import com.windapp.usageoverview.ui.usage_overview.usage_timeline.UsageTimelineScreen

typealias ComposableFun=@Composable () -> Unit

sealed class TabItem(var icon:Int,var title:String,var screen:ComposableFun){
    object UsageStats:TabItem(R.drawable.ic_launcher_foreground,"Usage Stats",{ UsageStatsScreen()})
    object AppLaunches:TabItem(R.drawable.ic_launcher_foreground,"App Launches",{AppLanuchesScreen()})
    object BrowsingTime:TabItem(R.drawable.ic_launcher_foreground,"Browsing Time",{ BrowsingTimeScreen({})})
    object ScreenUnlocks:TabItem(R.drawable.ic_launcher_foreground,"Screen Unlocks",{ ScreenUnlocksScreen()})
    object UsageTimeline:TabItem(R.drawable.ic_launcher_foreground,"Usage Timeline",{ UsageTimelineScreen({
        MainActivity.helper.navController.navigate(it.route)
    })})

}


