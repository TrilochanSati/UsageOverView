package com.windapp.usageoverview.di

import android.app.Application
import androidx.room.Room
import com.windapp.usageoverview.data.AppUsageStatsDatabase
import com.windapp.usageoverview.data.AppUsageStatsRepository
import com.windapp.usageoverview.data.AppUsageStatsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppUsageStatsDatabase(app:Application):AppUsageStatsDatabase{
        return Room.databaseBuilder(
            app,
            AppUsageStatsDatabase::class.java,
            "usage_overview_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAppUsageStatsRepository(db:AppUsageStatsDatabase):AppUsageStatsRepository{
        return AppUsageStatsRepositoryImpl(db.dao)

    }
}