package com.windapp.usageoverview

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UsageOverviewApp:Application() {

    object APP{
    lateinit var  context: Context

    }


    override fun onCreate() {
        super.onCreate()
        APP.context=this
    }
}