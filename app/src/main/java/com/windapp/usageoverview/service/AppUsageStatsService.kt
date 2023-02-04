package com.windapp.usageoverview.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.util.Log

import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.windapp.usageoverview.MainActivity
import com.windapp.usageoverview.R
import com.windapp.usageoverview.data.AppUsageStatsDatabase
import com.windapp.usageoverview.data.AppUsageStatsRepository
import com.windapp.usageoverview.data.entities.BrowsingTimeStats
import com.windapp.usageoverview.receiver.PhoneUnlockedReceiver
import com.windapp.usageoverview.service.AppUsageStatsService.flagObj.mutableSharedFlowUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import javax.inject.Inject

@AndroidEntryPoint
class AppUsageStatsService: LifecycleService() {
    var oldUrlInfo: String ="null"
    var oldUrlTime:Long=System.currentTimeMillis()

    lateinit var receiver: PhoneUnlockedReceiver

    @Inject
    lateinit var repository: AppUsageStatsRepository
    object flagObj{
        var flag=true
        lateinit  var contextForeGround:Context

        var mutableSharedFlowUrl= MutableSharedFlow<String>()



    }

    override fun onCreate() {
        super.onCreate()
        receiver = PhoneUnlockedReceiver()
        val filter = IntentFilter()
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.USER_PRESENT");
        registerReceiver(receiver, filter)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)



        lifecycleScope.launch {
            mutableSharedFlowUrl
                .debounce(2000)
                .distinctUntilChanged()
                .collectLatest {
                    url->

                    val curUrlIndex=url.indexOf("packageNaam:")
                    var curUrl=url.substring(0,curUrlIndex)
                    val curBrowser=url.substring(curUrlIndex,url.length).replace("packageNaam:","")
                    Log.d("s_url",curUrl+"  browser:"+curBrowser)

                    var pattern = Pattern.compile(".*?/")

                    var matcher=pattern.matcher(curUrl)
                    if(matcher.find()){
                        Log.d("cur_url: ",matcher.group(0).replace("/",""))
                        curUrl=matcher.group(0).replace("/","")

                            if(curUrl.trim().contains("bro.browser_exited.com") && !(curUrl.trim().contains(oldUrlInfo)) ){
                                var timeSpent=System.currentTimeMillis()- oldUrlTime
                                val timeSpentStr=getMinutesFromMilli(timeSpent)


                                val currentTimestamp = System.currentTimeMillis()
                                val website=oldUrlInfo
                                val packageName=curBrowser
                                val time= SimpleDateFormat("hh:mm a", Locale.US).format( Calendar.getInstance().time)

                                repository.insertBrowsingTime(BrowsingTimeStats(currentTimestamp,packageName,website,time,timeSpentStr,getCurrentDate()))



                                Log.d("u_time1","curUrl: "+curUrl+" oldUrl: "+oldUrlInfo+"+")
                                oldUrlInfo=curUrl
                                oldUrlTime=System.currentTimeMillis()

                            }


                            else   if(!(curUrl.trim().contains(oldUrlInfo))) {

                                if(oldUrlInfo.trim().equals("bro.browser_exited.com") || oldUrlInfo.trim().equals("null") ){
                                    Log.d("u_time3","entered browser")
                                    oldUrlInfo=curUrl
                                }

                          else{
                                    var timeSpent=System.currentTimeMillis()- oldUrlTime
                                    val timeSpentStr=getMinutesFromMilli(timeSpent)

                                    val currentTimestamp = System.currentTimeMillis()
                                    val website=oldUrlInfo
                                    val packageName=curBrowser
                                    val time= SimpleDateFormat("hh:mm a", Locale.US).format( Calendar.getInstance().time)

                                    repository.insertBrowsingTime(BrowsingTimeStats(currentTimestamp,packageName,website,time,timeSpentStr,getCurrentDate()))



                                    Log.d("u_time2","curUrl: "+curUrl+" oldUrl: "+oldUrlInfo+"+")
                                    oldUrlInfo=curUrl
                                    oldUrlTime=System.currentTimeMillis()
                                }

                            }





                    }
                    else{

                    }





                }
/*
                .collectLatest {

                        url->

                    var curUrl=url
                    var pattern = Pattern.compile(".*?/")

                    var matcher=pattern.matcher(curUrl.urlName)
                    if(matcher.find()){
                        Log.d("cur_url: ",matcher.group(0).replace("/",""))
                        curUrl=url.copy(urlName = matcher.group(0).replace("/",""))

                        if(oldUrlInfo!=null){
                            if(curUrl.urlName.equals("bro.browser_exited.com")){
                                var timeSpent=curUrl.curTime- oldUrlInfo!!.curTime
                                timeSpent=timeSpent/1000


                                val currentTimestamp =( System.currentTimeMillis()/1000)
                                val website=oldUrlInfo!!.urlName
                                val packageName=oldUrlInfo!!.packageName
                                val time= SimpleDateFormat("hh:mm a", Locale.US).format( Calendar.getInstance().time)

                                repository.insertBrowsingTime(BrowsingTimeStats(currentTimestamp,packageName,website,time,timeSpent.toString(),getCurrentDate()))



                                Log.d("u_time1",timeSpent.toString())
                                oldUrlInfo=null

                            }

                            else   if(!curUrl.urlName.equals(oldUrlInfo!!.urlName)){
                                var timeSpent=curUrl.curTime- oldUrlInfo!!.curTime
                                timeSpent=timeSpent/1000

                                val currentTimestamp = System.currentTimeMillis()
                                val website=curUrl!!.urlName
                                val packageName=curUrl!!.packageName
                                val time= SimpleDateFormat("hh:mm a", Locale.US).format( Calendar.getInstance().time)

                                repository.insertBrowsingTime(BrowsingTimeStats(currentTimestamp,packageName,website,time,timeSpent.toString(),getCurrentDate()))



                                Log.d("u_time2",timeSpent.toString())
                                oldUrlInfo=curUrl

                            }

                        }
                        if(oldUrlInfo==null){
                            oldUrlInfo=curUrl
                        }


                    }
                    else{

                    }






                }
*/
        }



        generateForegroundNotification()
        return  START_STICKY

    }




    override fun onBind(p0: Intent?): IBinder? {
        return super.onBind(p0)
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
                PendingIntent.getActivity(this, 0, intentMainLanding,  PendingIntent.FLAG_MUTABLE)
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

    private fun getCurrentDate():String{
        var   calendar = Calendar. getInstance();
        var  dateFormat =  SimpleDateFormat("dd/MM/yyyy")
        var   date = dateFormat.format(calendar.getTime())
        return date
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)

    }

    private fun getMinutesFromMilli(milliseconds:Long):String{
        // This method uses this formula :minutes =
        // (milliseconds / 1000) / 60;
        val minutes
        = TimeUnit.MILLISECONDS.toMinutes(milliseconds);

        // This method uses this formula seconds =
        // (milliseconds / 1000);
        val seconds
        = (TimeUnit.MILLISECONDS.toSeconds(milliseconds)
                % 60);

        // Print the answer

              return minutes.toString()+":"+seconds.toString()
    }
    }
