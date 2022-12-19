package com.windapp.usageoverview.ui.usage_overview.usage_timeline

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windapp.usageoverview.UsageOverviewApp
import com.windapp.usageoverview.data.AppUsageStatsRepository
import com.windapp.usageoverview.data.entities.AppUsageTimelineStats
import com.windapp.usageoverview.util.AppUtil
import com.windapp.usageoverview.util.Routes
import com.windapp.usageoverview.util.UiEvent
import com.windapp.usageoverview.util.UtilUsageTimeline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UsageTimelineViewModel @Inject constructor(
    private val repository: AppUsageStatsRepository
    ): ViewModel() {

    var curDate= MutableLiveData(getCurrentDate())


    var appUsageStats=repository.getAppTimelineUsageStats()

    val excludedApps=repository.getExcludedApps()




    private val _uiEvent= Channel<UiEvent>()
    val uiEvent=_uiEvent.receiveAsFlow()


    //   private var deletedAppUsageStats:AppUsageStats?=null

    init {

        insertAppTimelineStats()

    }

    fun onEvent(event: UsageTimelineEvent){
        when(event){
            is UsageTimelineEvent.OnAppClick ->{
                sendUiEvent(UiEvent.Navigate(Routes.SINGLE_USAGE_TIMELINE+"?packageName=${event.appUsageStats.packageName}"))

            }
            is UsageTimelineEvent.OnClearDataClick ->{
                viewModelScope.launch {
                    repository.deleteAllTimelineUsageStats()
                    sendUiEvent(UiEvent.ShowSnackbar(
                        "All Data Cleared"
                    ))
                }
            }
            is UsageTimelineEvent.OnTimelineFilterClick ->{

                appUsageStats=repository.getAppTimelineUsageStatsByDate(event.date)
                curDate.value=event.date
            }

        }
    }

    private fun sendUiEvent(event:UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

  private  fun insertAppTimelineStats(){
       val date=getCurrentDate()
 //      val date="04/12/2022"
        var usageList=    UtilUsageTimeline
            .getApps(UsageOverviewApp.APP.context,date)


        usageList.reverse()
        usageList.forEach {
                appUsageStat->
            Log.d("assci",appUsageStat.pkg)
            val time= SimpleDateFormat("hh:mm a", Locale.US).format( Date(appUsageStat.getTime()))
            val usedTime= AppUtil.formatMilliSeconds(appUsageStat.getUsedtime())
            viewModelScope.launch {
                repository.insertTimelineUsageStats(
                    AppUsageTimelineStats(
                        packageName = appUsageStat.pkg,
                        time =  time,
                        timeSpent = usedTime,
                        date=date,
                        timeStamp = appUsageStat.timeStamp
                    )
                )
            }

        }
    }

    private fun getCurrentDate():String{
        var   calendar = Calendar. getInstance();
        var  dateFormat =  SimpleDateFormat("dd/MM/yyyy")
        var   date = dateFormat.format(calendar.getTime())
        return date
    }



}