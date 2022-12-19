package com.windapp.usageoverview.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.windapp.usageoverview.service.AppUsageStatsService.flagObj.mutableSharedFlowUrl

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.LinkedBlockingQueue


class UrlAccessiblityService : AccessibilityService() {


    var browserHashMap: HashMap<String, String> = HashMap(13)

    val linkedBlockingQueue: LinkedBlockingQueue<AccessibilityNodeInfo> = LinkedBlockingQueue(2)
    private var n = false


    init
    {
        browserHashMap["com.android.chrome"] = "com.android.chrome:id/url_bar"
        browserHashMap["com.chrome.beta"] = "com.chrome.beta:id/url_bar"
        browserHashMap["org.chromium.chrome"] = "org.chromium.chrome:id/url_bar"
        browserHashMap["com.sec.android.app.sbrowser"] =
            "com.sec.android.app.sbrowser:id/location_bar_edit_text"
        browserHashMap["com.android.browser"] = "com.android.browser:id/url"
        browserHashMap["com.opera.browser"] = "com.opera.browser:id/url_field"
        browserHashMap["com.opera.mini.native"] = "com.opera.mini.native:id/url_field"
        browserHashMap["com.opera.browser.beta"] = "com.opera.browser.beta:id/url_field"
        browserHashMap["com.opera.touch"] = "com.opera.touch:id/addressbarEdit"
        browserHashMap["org.mozilla.firefox"] = "org.mozilla.firefox:id/mozac_browser_toolbar_url_view"
        browserHashMap["org.mozilla.firefox_beta"] =
            "org.mozilla.firefox_beta:id/mozac_browser_toolbar_url_view"
        browserHashMap["com.microsoft.emmx"] = "com.microsoft.emmx:id/url_bar"
        browserHashMap["com.brave.browser"] = "com.brave.browser:id/url_bar"
        browserHashMap["com.vivaldi.browser"] = "com.vivaldi.browser:id/url_bar"
        browserHashMap["com.ecosia.android"] = "com.ecosia.android:id/url_bar"
        browserHashMap["com.kiwibrowser.browser"] = "com.kiwibrowser.browser:id/url_bar"
        browserHashMap["com.hsv.freeadblockerbrowser"] = "com.hsv.freeadblockerbrowser:id/url_bar"
        browserHashMap["com.huawei.browser"] = "com.huawei.browser:id/url_bar"
        browserHashMap["com.mi.globalbrowser"] = "com.mi.globalbrowser:id/url"
        browserHashMap["com.nationaledtech.spinbrowser"] =
            "com.nationaledtech.spinbrowser:id/mozac_browser_toolbar_url_view"
        browserHashMap["com.yandex.browser"] = "com.yandex.browser:id/bro_omnibar_address_title_text"
        browserHashMap["com.yandex.browser.beta"] =
            "com.yandex.browser.beta:id/bro_omnibar_address_title_text"
        browserHashMap["org.adblockplus.browser"] = "org.adblockplus.browser:id/url_bar"
        browserHashMap["com.sec.android.app.sbrowser.lite"] =
            "com.sec.android.app.sbrowser.lite:id/location_bar_edit_text"


    }

    fun a(str: String?): String? {

        return try {
            val poll: AccessibilityNodeInfo = linkedBlockingQueue.poll() ?: return null
            val packageName=poll.packageName.toString()
            val b2 = b(poll, 0, browserHashMap[poll.packageName.toString()])
            if (b2 != null && !b2.isFocused) {
                val url = b2.text.toString().toLowerCase()



                b2.recycle()

                if(AppUsageStatsService.flagObj.flag){
/*                    GlobalScope.launch {


                        if (repository.isWebsiteExits(lowerCase)) {
                            Log.d("lower", lowerCase)





                            launchBlockedActivity()
                       //     launchBlockedOverlay(contextForeGround)


                        }

                    }*/

                    if(!url.startsWith("search or type web")){
                        GlobalScope.launch {
                            mutableSharedFlowUrl.emit(url+":"+packageName)

                        }
                    }







                }



                return  url

            } else if (b2 == null) {
                null
            } else {
                b2.recycle()
                null
            }
        } catch (unused: Exception) {
            null
        }
    }

    private fun b(
        accessibilityNodeInfo: AccessibilityNodeInfo,
        i2: Int,
        str: String?
    ): AccessibilityNodeInfo? {
        if (i2 >= 60) {
            accessibilityNodeInfo.recycle()
            return null
        }
        val findAccessibilityNodeInfosByViewId =
            accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(
                str!!
            )
        var z = true
        return if (findAccessibilityNodeInfosByViewId.size > 0) {
            accessibilityNodeInfo.recycle()
            for (accessibilityNodeInfo2 in findAccessibilityNodeInfosByViewId) {
                if (z) {
                    z = false
                } else {
                    accessibilityNodeInfo2.recycle()
                }
            }
            findAccessibilityNodeInfosByViewId[0]
        } else if (accessibilityNodeInfo.parent != null) {
            val parent = accessibilityNodeInfo.parent
            accessibilityNodeInfo.recycle()
            b(parent, i2 + 1, str)
        } else {
            accessibilityNodeInfo.recycle()
            null
        }
    }

    // android.accessibilityservice.AccessibilityService
    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent) {


        var source: AccessibilityNodeInfo?=null
        try {
            a("xxxxx onAccessibilityEvent ")
            if (accessibilityEvent.eventType == 2048 && accessibilityEvent.source.also {
                    source = it
                } != null) {
                linkedBlockingQueue.add(source)
            }
        } catch (unused: Exception) {
        }
    }

    // android.accessibilityservice.AccessibilityService
    override fun onInterrupt() {
        a("xxxxx onInterrupt ")
    }

    // android.accessibilityservice.AccessibilityService
    override fun onServiceConnected() {
        super.onServiceConnected()

        n = true
        a("xxxxx Accesss onServiceConnected $n")
    }

    // android.app.Service
    override fun onUnbind(intent: Intent?): Boolean {
        n = false
        a("xxxxx Accesss onUnbind $n")
        return super.onUnbind(intent)
    }





}