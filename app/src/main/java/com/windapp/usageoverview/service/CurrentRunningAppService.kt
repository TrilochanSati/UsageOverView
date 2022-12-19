package com.windapp.usageoverview.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.util.Log

import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import com.windapp.usageoverview.MainActivity
import com.windapp.usageoverview.R

import com.windapp.usageoverview.util.ApplicationFetcher
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentRunningAppService: LifecycleService() {
    lateinit var liveData: LiveData<String>

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

/*         val repository:AppNameRepository

        val appNameDb= AppNameRoomDatabase.getInstance(application)
        val appNameDao=appNameDb.appNameDao()
        repository= AppNameRepository(appNameDao)
        Thread {

            while (true) {
              //  delay(5000)

        val bool=        repository.isSelected(ApplicationFetcher.printForegroundTask(applicationContext)?:"null")

        Log.d("bool",bool.toString())
          bool?.let {
              if(bool){
                  launchBlockedActivity()
              }
          }

                        Log.d("activeapp",
                            ApplicationFetcher.printForegroundTask(applicationContext)?:"null")



            }

        }.start()*/

        Thread{
            while (true){
           //     Log.d("activeapp", ApplicationFetcher.printForegroundTask(applicationContext)?:"null")


            }
         //   ApplicationFetcher.getAppStats(applicationContext)
        }.start()
        generateForegroundNotification()
        return  START_STICKY
    }




    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    //Notififcation for ON-going
    private var iconNotification: Bitmap? = null
    private var notification: Notification? = null
    var mNotificationManager: NotificationManager? = null
    private val mNotificationId = 123

    private fun generateForegroundNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intentMainLanding = Intent(this, MainActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intentMainLanding, 0)
            iconNotification = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            if (mNotificationManager == null) {
                mNotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                assert(mNotificationManager != null)
                mNotificationManager?.createNotificationChannelGroup(
                    NotificationChannelGroup("chats_group", "Chats")
                )
                val notificationChannel =
                    NotificationChannel("service_channel", "Service Notifications",
                        NotificationManager.IMPORTANCE_MIN)
                notificationChannel.enableLights(false)
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_SECRET
                mNotificationManager?.createNotificationChannel(notificationChannel)
            }
            val builder = NotificationCompat.Builder(this, "service_channel")

            builder.setContentTitle(StringBuilder(resources.getString(R.string.app_name)).append(" service is running").toString())
                .setTicker(StringBuilder(resources.getString(R.string.app_name)).append("service is running").toString())
                .setContentText("Touch to open") //                    , swipe down for more options.
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setWhen(0)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
            if (iconNotification != null) {
                builder.setLargeIcon(Bitmap.createScaledBitmap(iconNotification!!, 128, 128, false))
            }
            builder.color = resources.getColor(R.color.purple_200)
            notification = builder.build()
            startForeground(mNotificationId, notification)
        }

    }
}