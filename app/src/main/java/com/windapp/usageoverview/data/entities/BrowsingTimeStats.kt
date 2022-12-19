package com.windapp.usageoverview.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BrowsingTimeStats(
    @PrimaryKey(autoGenerate = false)
    val timeStamp:Long,
    val packageName:String,
    val website:String,
    val time:String,
    val timeSpent: String,
    val date:String
)
