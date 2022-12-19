package com.windapp.usageoverview.ui.usage_overview.usage_timeline.exclude_apps

import com.windapp.usageoverview.data.entities.AppNameInfo

   sealed class ExcludeAppsEvent {
    data class OnExcludeChange(val appNameInfo: AppNameInfo):ExcludeAppsEvent()
}