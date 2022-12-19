package com.windapp.usageoverview.ui.usage_overview.usage_timeline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.windapp.usageoverview.UsageOverviewApp
import com.windapp.usageoverview.ui.usage_overview.usage_timeline.single_app_timeline.SingleAppTimelineViewModel
import com.windapp.usageoverview.ui.usage_overview.usage_timeline.single_app_timeline.SingleAppUsageItem
import com.windapp.usageoverview.util.UtilUsageTimeline

@Composable
fun SingleAppTimelineScreen(
    onPopBackStack:()->Unit,
    viewModel: SingleAppTimelineViewModel= hiltViewModel()
) {
    val appUsageStats= viewModel.appStats?.collectAsState(initial = emptyList())
    
    Text(text = "Singe App Usage")

    LazyColumn(modifier = Modifier.fillMaxSize()){

        if (appUsageStats != null) {
            items(appUsageStats.value){ appUsageStat->
                var icon= UtilUsageTimeline
                    .getIcon(UsageOverviewApp.APP.context,appUsageStat.packageName)
                icon?.let{
                    SingleAppUsageItem( icon=icon,
                        timeSpent =appUsageStat.timeSpent ,
                        inTime = appUsageStat.time,
                        UtilUsageTimeline
                            .getAppname(UsageOverviewApp.APP.context,appUsageStat.packageName),
                        onEvent = viewModel::onEvent,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                            }
                            .padding(16.dp)


                    )
                }




            }
        }

    }
}