package com.windapp.usageoverview.ui.usage_overview.browsing_time

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windapp.usageoverview.data.AppUsageStatsRepository
import com.windapp.usageoverview.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowsingTimeViewModel @Inject constructor(
  private val repository: AppUsageStatsRepository
) :ViewModel(){
    var browsingTimeStats=repository.getBrowsingTime()

    private val _uiEvent= Channel<UiEvent>()
    val uiEvent=_uiEvent.receiveAsFlow()

    fun onEvent(event: BrowsingTimeEvent){

    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}