package com.windapp.usageoverview.ui.usage_overview.usage_timeline

import com.windapp.usageoverview.data.entities.AppUsageTimelineStats

sealed    class UsageTimelineEvent {
    data class OnAppClick(val appUsageStats:AppUsageTimelineStats): UsageTimelineEvent()
    object OnClearDataClick: UsageTimelineEvent()
    object OnExcludeAppFilterClick: UsageTimelineEvent()
    data class OnTimelineFilterClick(val date:String): UsageTimelineEvent()

}