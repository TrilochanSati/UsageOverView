package com.windapp.usageoverview.data.entities

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppNameInfo(
    @PrimaryKey(autoGenerate = false)
    val packageName:String,
    val appName:String,
    val excludeApp:Boolean=false


)
