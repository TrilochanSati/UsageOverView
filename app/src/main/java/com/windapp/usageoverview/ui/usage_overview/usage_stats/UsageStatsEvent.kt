package com.windapp.usageoverview.ui.usage_overview.usage_stats

import com.windapp.usageoverview.ui.usage_overview.usage_timeline.UsageTimelineEvent

sealed class UsageStatsEvent {

       object OnClearDataClick: UsageStatsEvent()
       object OnExcludeAppFilterClick: UsageStatsEvent()
       data class OnTimelineFilterClick(val date:String): UsageStatsEvent()

   }