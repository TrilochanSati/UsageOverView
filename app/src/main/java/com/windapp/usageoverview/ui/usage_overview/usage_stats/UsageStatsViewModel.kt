package com.windapp.usageoverview.ui.usage_overview.usage_stats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windapp.usageoverview.data.AppUsageStatsRepository
import com.windapp.usageoverview.data.entities.AppsUsageStats
import com.windapp.usageoverview.util.UiEvent
import com.windapp.usageoverview.util.UtilUsageStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsageStatsViewModel @Inject constructor(
    private val repository: AppUsageStatsRepository
) :ViewModel() {

    val appUsageStats=repository.getAppsUsageStats()
    val excludedApps=repository.getExcludedApps()

    private val _uiEvent= Channel<UiEvent>()
    val uiEvent=_uiEvent.receiveAsFlow()

    var day=MutableLiveData<Int>(0)

    init {

    }

    fun onEvent(event:UsageStatsEvent){

    }


/*
    fun insertUsageStats(){
        var usageStats=UtilUsageStats.getApps()

        usageStats.forEach {
            usageStats->
            viewModelScope.launch {
                repository.insertAppsUsageStats(AppsUsageStats(
                    usageStats.pkg,
                    usageStats.count,
                    usageStats.name,
                    usageStats.time,
                    usageStats.useddata,
                    usageStats.usedtime
                ))
            }
        }
    }
*/
}