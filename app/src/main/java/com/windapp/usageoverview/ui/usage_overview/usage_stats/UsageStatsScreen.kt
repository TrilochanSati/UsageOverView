package com.windapp.usageoverview.ui.usage_overview.usage_stats

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.windapp.usageoverview.R
import com.windapp.usageoverview.data.entities.AppNameInfo
import com.windapp.usageoverview.util.AppUtil
import com.windapp.usageoverview.util.SortEnum
import com.windapp.usageoverview.util.UtilUsageStats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SuspiciousIndentation")
@Composable
fun UsageStatsScreen(

    viewModel:UsageStatsViewModel= hiltViewModel()
){

    val excludedApps=viewModel.excludedApps.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple_200))
            .wrapContentSize(Alignment.Center)
    ) {
        val day=viewModel.day.observeAsState().value?:0

        val liveDataAppsUsageStats = MutableLiveData( UtilUsageStats.getApps("",day))
        val appsUsageStats=liveDataAppsUsageStats.value as List<UtilUsageStats.Applist>

            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(appsUsageStats){
                    usageStat->

                    if (!excludedApps.value.contains(AppNameInfo(usageStat.pkg, usageStat.name, true))) {


                        val time = SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.US).format(Date(usageStat.getTime()))
                        val timeSpent = AppUtil.formatMilliSeconds(usageStat.usedtime)
                        UsageStatsItem(
                            icon = usageStat.icon,
                            timeSpent = timeSpent,
                            time = time,
                            appName = usageStat.name,
                            onEvent = viewModel::onEvent

                        )

                    }

                }

            }

    }

}