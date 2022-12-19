package com.windapp.usageoverview.ui.usage_overview


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.windapp.usageoverview.R

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.accompanist.pager.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.windapp.usageoverview.MainActivity
import com.windapp.usageoverview.ui.usage_overview.usage_stats.UsageStatsViewModel
import com.windapp.usageoverview.ui.usage_overview.usage_timeline.UsageTimelineEvent
import com.windapp.usageoverview.ui.usage_overview.usage_timeline.UsageTimelineViewModel
import com.windapp.usageoverview.util.Routes
import com.windapp.usageoverview.util.SortEnum
import com.windapp.usageoverview.util.UiEvent
import com.windapp.usageoverview.util.UtilUsageStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter



@OptIn(ExperimentalPagerApi::class)
@Composable
fun UsageOverviewTabLayout(
    usageStatsViewModel:UsageStatsViewModel= hiltViewModel()
){
val tabs= listOf(
    TabItem.UsageStats,
    TabItem.AppLaunches,
    TabItem.BrowsingTime,
    TabItem.ScreenUnlocks,
    TabItem.UsageTimeline
)
    val pagerState= rememberPagerState()

    var datePickerDisplayMenu by remember {
        mutableStateOf(false)
    }


    Scaffold(
        topBar = { TopBar(pagerState) },
    ) {padding->
        Column(
            modifier = Modifier.padding(padding)
        ) {

           var mDaysDisplayMenu by remember {
                mutableStateOf(false)
            }

            if(pagerState.currentPage==0 || pagerState.currentPage==1) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray),


                    ) {

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { /*TODO*/ },


                        ) {
                        Text(text = "<")
                    }

                    Button(
                        onClick = { mDaysDisplayMenu=!mDaysDisplayMenu },

                        ) {
                        var day= getDayName(usageStatsViewModel.day.value?:0)
                        Text(text = day)

                        DropdownMenu(expanded = mDaysDisplayMenu,
                            onDismissRequest = { mDaysDisplayMenu=false
                            }) {
                            DropdownMenuItem(onClick = {
                                mDaysDisplayMenu=false
                                usageStatsViewModel.day.value=0

                            }) {
                                Text(text = "TODAY")
                            }
                            DropdownMenuItem(onClick = {
                                mDaysDisplayMenu=false
                                usageStatsViewModel.day.value=1

                            }) {
                                Text(text = "YESTERDAY")

                            }
                            DropdownMenuItem(onClick = {
                                mDaysDisplayMenu=false
                                usageStatsViewModel.day.value=2
                            }) {
                                Text(text = "THIS WEEK")

                            }
                            DropdownMenuItem(onClick = {
                                mDaysDisplayMenu=false
                                usageStatsViewModel.day.value=3
                            }) {
                                Text(text = "LAST WEEK")

                            }
                            DropdownMenuItem(onClick = {
                                mDaysDisplayMenu=false
                                usageStatsViewModel.day.value=4
                            }) {
                                Text(text = "THIS MONTH")

                            }
                            DropdownMenuItem(onClick = {
                                mDaysDisplayMenu=false
                                usageStatsViewModel.day.value=5
                            }) {
                                Text(text = "LAST MONTH")

                            }
                            DropdownMenuItem(onClick = {
                                mDaysDisplayMenu=false
                                usageStatsViewModel.day.value=6
                            }) {
                                Text(text = "THIS YEAR")

                            }
                            DropdownMenuItem(onClick = {
                                mDaysDisplayMenu=false
                                usageStatsViewModel.day.value=7
                            }) {
                                Text(text = "LAST YEAR")

                            }
                        }

                    }

                    Button(
                        onClick = { /*TODO*/ },

                        ) {
                        Text(text = ">")
                    }

                }


            }

            Tabs(tabs = tabs,pagerState=pagerState)
            TabsContent(tabs = tabs, pagerState =pagerState )

        }

    }

}



@OptIn(ExperimentalPagerApi::class)
@Composable
fun TopBar(pagerState: PagerState,
           viewModel: UsageTimelineViewModel = hiltViewModel()
) {

    var mDisplayMenu by remember {
        mutableStateOf(false)
    }
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd/MM/yyyy")
                .format(pickedDate)
        }
    }
    val dateDialogState= rememberMaterialDialogState()
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
        backgroundColor = colorResource(id =R.color.purple_200 ),
        contentColor = Color.White,
        actions = {

            if(pagerState.currentPage==Screens.UsageStatsScreen.ordinal){
                IconButton(onClick = {
                    MainActivity.helper.navController.navigate(Routes.EXCLUDE_APPS)
                }) {
                    Icon(painter = painterResource(R.drawable.ic_baseline_filter_alt_24), "Exclude Apps")
                }


            }

       if(pagerState.currentPage==Screens.UsageTimelineScreen.ordinal){
           IconButton(onClick = {
               dateDialogState.show()

           }) {
               Icon(Icons.Default.DateRange, "Date Range")
           }
           IconButton(onClick = {
               MainActivity.helper.navController.navigate(Routes.EXCLUDE_APPS)
           }) {
               Icon(painter = painterResource(R.drawable.ic_baseline_filter_alt_24), "Exclude Apps")
           }
           IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
               Icon(Icons.Default.MoreVert, "")
           }
           DropdownMenu(
               expanded = mDisplayMenu,
               onDismissRequest = { mDisplayMenu = false }) {
               DropdownMenuItem(onClick = {

                   viewModel.onEvent(UsageTimelineEvent.OnClearDataClick)

               },


               ) {
                   Text(text = "Clear Data")

               }
           }

           MaterialDialog(dialogState = dateDialogState,
           buttons = {
               positiveButton(text = "Ok"){

               }
               negativeButton(text = "Cancel")
           }
               ) {
               datepicker(
                   initialDate=LocalDate.now(),
                   title = "Pick a date"

                   ){
                   pickedDate=it
                   viewModel.onEvent(UsageTimelineEvent.OnTimelineFilterClick(formattedDate))


               }

           }

       }




        },

    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs:List<TabItem>,pagerState:PagerState){
    val scope= rememberCoroutineScope()
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = colorResource(id = R.color.purple_200),
        contentColor = Color.Red,
        indicator = { tabPositions ->
        TabRowDefaults.Indicator(
            Modifier.pagerTabIndicatorOffset(pagerState,tabPositions)
        )
        }
        ) {
        tabs.forEachIndexed { index, tabItem ->
            Tab(
                text = { Text(tabItem.title, modifier = Modifier.wrapContentSize()) },
                icon = { Icon(painter = painterResource(id = tabItem.icon), modifier = Modifier.size(50.dp), contentDescription = "") },


            selected = pagerState.currentPage==index,
                 onClick = {
                           scope.launch {
                               pagerState.animateScrollToPage(index)
                           }
                 })
        }

    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(tabs:List<TabItem>,pagerState: PagerState){
    HorizontalPager(count = tabs.size, state = pagerState) {
        page -> tabs[page].screen()


    }
}

fun getDayName(day:Int): String {
    

    if(day==0)
        return "TODAY"
    else if(day==1)
        return "YESTERDAY"
    else if(day==2)
            return "THIS WEEK"
    else if(day==3)
            return "LAST WEEK"
    else if(day==4)
            return "THIS MONTH"
    else if(day==5)
        return  "LAST MONTH"
    else if(day==6)
            return "THIS YEAR"
    else if(day==7)
        return  "LAST YEAR"

    return  "-1";
}





