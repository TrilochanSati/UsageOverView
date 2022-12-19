package com.windapp.usageoverview.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScreenUnlockStats (
    @PrimaryKey(autoGenerate = false)
    val timeStamp:Long,
    val time:String,
    val date:String
)
