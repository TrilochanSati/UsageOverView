package com.windapp.usageoverview.ui.usage_overview.app_launches

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.windapp.usageoverview.data.AppUsageStatsRepository
import com.windapp.usageoverview.ui.usage_overview.usage_stats.UsageStatsEvent
import com.windapp.usageoverview.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class AppLaunchesViewModel @Inject constructor(
    private val repository: AppUsageStatsRepository
):ViewModel() {

    val appUsageStats=repository.getAppsUsageStats()
    val excludedApps=repository.getExcludedApps()

    private val _uiEvent= Channel<UiEvent>()
    val uiEvent=_uiEvent.receiveAsFlow()

    var day= MutableLiveData<Int>(0)

    init {

    }

    fun onEvent(event: AppLaunchesEvent){

    }



}