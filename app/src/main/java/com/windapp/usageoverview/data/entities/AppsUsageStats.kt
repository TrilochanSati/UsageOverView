package com.windapp.usageoverview.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppsUsageStats (

   @PrimaryKey(autoGenerate = false)
      val packageName:String,
      val count:Int,
      val name:String,
      val time:Long,
      val usedData:Long,
      val usedTime:Long

   )