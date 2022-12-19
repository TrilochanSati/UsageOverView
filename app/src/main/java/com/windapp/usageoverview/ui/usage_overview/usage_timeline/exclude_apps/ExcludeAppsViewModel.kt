package com.windapp.usageoverview.ui.usage_overview.usage_timeline.exclude_apps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windapp.usageoverview.UsageOverviewApp
import com.windapp.usageoverview.data.AppUsageStatsRepository
import com.windapp.usageoverview.data.entities.AppNameInfo
import com.windapp.usageoverview.util.AppUtil
import com.windapp.usageoverview.util.ApplicationFetcher
import com.windapp.usageoverview.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExcludeAppsViewModel @Inject constructor (
    private val repository: AppUsageStatsRepository
        )

    :ViewModel() {

        var apps =repository.getAppNameInfo()

        private val _uiEvent= Channel<UiEvent>()
        val uiEvent=_uiEvent.receiveAsFlow()

    init {
        insertAppName()
    }

    fun onEvent(event: ExcludeAppsEvent){
        when(event){
            is ExcludeAppsEvent.OnExcludeChange ->{
                viewModelScope.launch {
                    repository.excludeApp(event.appNameInfo.packageName,event.appNameInfo.excludeApp)
                }

            }
        }
    }


    private fun insertAppName(){
        var appList=ApplicationFetcher.getInstalledApplications(UsageOverviewApp.APP.context)
        appList.sortedBy { it.getApplicationName() }

        appList.forEach {
            viewModelScope.launch {
                repository.insertAppNameInfo(
                    AppNameInfo(
                        it.getPackageName(),
                        it.getApplicationName(),
                        false
                    )
                )
            }
        }
    }

}