package com.windapp.usageoverview.receiver

import android.R.attr.action
import android.app.KeyguardManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class PhoneUnlockedReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val keyguardManager=context?.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        keyguardManager?.let {
            if(keyguardManager.isKeyguardSecure){

                Log.d("unlocked","yes")

            }
        }

        if ("android.intent.action.USER_PRESENT" == intent?.action) {
            Log.d("smt", "Phone unlocked");

        }

        if (intent?.getAction().equals(Intent.ACTION_USER_PRESENT)){
            Log.d("smt", "Phone unlocked");
        }
    }
}