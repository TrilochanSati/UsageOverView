package com.windapp.usageoverview.util

import android.app.ActivityManager
import android.content.Context
import android.graphics.drawable.Drawable


/**
 * This is just a wrapper for encapsulating the information of the application such as
 *      1 - Application Name
 *      2 - Application Icon
 *  So this is basically a data class for holding some data together.
 */
class ApplicationInfoWrapper {

    /*******************************************************************************************************************
    VARIABLE DECLARATION
     ******************************************************************************************************************/
    private var applicationName: String
    private var packageName: String
    private var applicationIcon: Drawable

    /*******************************************************************************************************************
    CONSTRUCTORS
     ******************************************************************************************************************/
    public constructor(applicationName: String,
                       applicationIcon: Drawable,
                       packageName:String

    ) {
        this.applicationIcon = applicationIcon
        this.applicationName = applicationName
        this.packageName=packageName
    }

    /*******************************************************************************************************************
    GETTERS AND SETTERS
     ******************************************************************************************************************/
    public fun getApplicationName() : String {
        return this.applicationName
    }
    public fun getApplicationIcon() : Drawable {
        return this.applicationIcon
    }

    public fun getPackageName():String{
        return  this.packageName
    }



    public fun setApplicationName(applicationName: String) {
        this.applicationName = applicationName
    }
    public fun setApplicationIcon(applicationIcon: Drawable) {
        this.applicationIcon = applicationIcon
    }

    public fun setPackageName(packageName: String){
        this.packageName=packageName
    }





}