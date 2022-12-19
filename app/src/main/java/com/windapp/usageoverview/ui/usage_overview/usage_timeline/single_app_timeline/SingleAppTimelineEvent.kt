package com.windapp.usageoverview.ui.usage_overview.usage_timeline.single_app_timeline

import com.windapp.usageoverview.data.entities.AppUsageTimelineStats



sealed    class SingleAppTimelineEvent {
    data  class OnAddLimitClick(val appUsageStats: AppUsageTimelineStats) : SingleAppTimelineEvent()
    object OnClearDataClick: SingleAppTimelineEvent()
    object OnExcludeAppFilterClick: SingleAppTimelineEvent()
    object OnTimelineFilterClick: SingleAppTimelineEvent()

}