package com.windapp.usageoverview.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppUsageTimelineStats(
    @PrimaryKey(autoGenerate = false)
    val timeStamp:Long,
    val packageName:String,
    val time:String,
    val timeSpent: String,
    val date:String
)
