package com.windapp.usageoverview.ui.usage_overview.browsing_time

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.windapp.usageoverview.R
import com.windapp.usageoverview.UsageOverviewApp
import com.windapp.usageoverview.util.UiEvent
import com.windapp.usageoverview.util.UtilUsageTimeline
import kotlinx.coroutines.flow.collect

@Composable
fun BrowsingTimeScreen(
    onNavigate:(UiEvent.Navigate)->Unit,
    viewModel: BrowsingTimeViewModel= hiltViewModel()
){

    var browsingTimeStats=viewModel.browsingTimeStats.collectAsState(initial = emptyList()).value.sortedByDescending { it.timeStamp }

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{
            event->

        }
    }

    Column {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(browsingTimeStats){
                browsingTimeStat->

                val packageName=browsingTimeStat.packageName

                var icon =
                    UtilUsageTimeline
                        .getIcon(UsageOverviewApp.APP.context, packageName)

                icon?.let {
                    BrowsingTimeItem(icon = icon, timeSpent =browsingTimeStat.timeSpent , inTime =browsingTimeStat.time , website =browsingTimeStat.website , onEvent =viewModel::onEvent )

                }



            }
        }
    }
}