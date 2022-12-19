package com.windapp.usageoverview.ui.usage_overview.usage_timeline.single_app_timeline

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.windapp.usageoverview.data.AppUsageStatsRepository
import com.windapp.usageoverview.data.entities.AppUsageTimelineStats
import com.windapp.usageoverview.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SingleAppTimelineViewModel @Inject constructor(
    private val repository: AppUsageStatsRepository,
    savedStateHandle: SavedStateHandle
):ViewModel() {
    var appStats by mutableStateOf<Flow<List<AppUsageTimelineStats>>?>(null)
    private set

    private val _uiEvent=Channel<UiEvent>()
    val uiEvent=_uiEvent.receiveAsFlow()

    fun onEvent(event:SingleAppTimelineEvent){
        when(event){
            is SingleAppTimelineEvent.OnClearDataClick->{

            }
        }
    }

    init{
        val packageName=savedStateHandle.get<String>("packageName")!!
      //  val packageName="com.stayfocused"
        Log.d("packii",packageName)
        if(!packageName.equals("null")){
            repository.getAppTimelineUsageStatsByPackageName(packageName)?.let {
                appStatsList->
                this@SingleAppTimelineViewModel.appStats=appStatsList

            }

        }
    }

}