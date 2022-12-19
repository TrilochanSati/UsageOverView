package com.windapp.usageoverview.ui.usage_overview.usage_timeline

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.windapp.usageoverview.UsageOverviewApp
import com.windapp.usageoverview.data.entities.AppNameInfo
import com.windapp.usageoverview.util.*
import java.util.*


@SuppressLint("SuspiciousIndentation")
@Composable
fun UsageTimelineScreen(
    onNavigate:(UiEvent.Navigate) -> Unit,
    viewModel: UsageTimelineViewModel = hiltViewModel()
){
    var appUsageStats=viewModel.appUsageStats.collectAsState(initial = emptyList())

    val excludedApps=viewModel.excludedApps.collectAsState(initial = emptyList())





    val scaffoldState= rememberScaffoldState()

    var oldDate by remember {
        mutableStateOf("olddate")
    }

  oldDate= viewModel.curDate.observeAsState().value.toString()


    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect { event->
            when(event){
                is UiEvent.ShowSnackbar ->{
                    val result=scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )

                }

                is UiEvent.Refresh ->{

                   //  appUsageStats=viewModel.appUsageStats.collectAsState(initial = emptyList())
                }

                is UiEvent.Navigate ->{
                    onNavigate(event)


                }

                else -> Unit


            }

        }
    }

    Scaffold(
 //   topBar ={ TopBar()        }
    ) { padding->
        Column() {

            Text(text = oldDate)
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){

                items(appUsageStats.value){
                        appUsageStat->

                    if(!appUsageStat.date.equals(oldDate)){

                        oldDate=appUsageStat.date
                    }

                    val appName=
                        UtilUsageTimeline
                            .getAppname(UsageOverviewApp.APP.context,appUsageStat.packageName)
                    val packageName=appUsageStat.packageName

                    appName?.let {
                        if (!excludedApps.value.contains(AppNameInfo(packageName, appName, true))) {
                            var icon =
                                UtilUsageTimeline
                                    .getIcon(UsageOverviewApp.APP.context, packageName)
                            icon?.let {
                                AppUsageItem(icon = icon,
                                    timeSpent = appUsageStat.timeSpent,
                                    inTime = appUsageStat.time,
                                    appName,
                                    onEvent = viewModel::onEvent,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {

                                            viewModel.onEvent(
                                                UsageTimelineEvent.OnAppClick(
                                                    appUsageStat
                                                )
                                            )
                                        }
                                        .padding(16.dp)


                                )
                            }



                        }

                    }












                }

            }
        }

    }




}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun TopBar() {
    var mDisplayMenu by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = { Text(text = "", fontSize = 18.sp) },
   //     backgroundColor = colorResource(id = R.color.purple_200 ),
     //   contentColor = Color.White,
        actions = {

                IconButton(onClick = { }) {
                    Icon(Icons.Default.DateRange, "")
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.AccountBox, "")
                }
                IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                    Icon(Icons.Default.MoreVert, "")
                }
                DropdownMenu(
                    expanded = mDisplayMenu,
                    onDismissRequest = { mDisplayMenu = false }) {
                    DropdownMenuItem(onClick = {}) {
                        Text(text = "Clear Data")

                    }
                }



        }
    )
}
