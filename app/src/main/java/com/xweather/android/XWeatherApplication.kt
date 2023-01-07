package com.xweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class XWeatherApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        const val TOKEN = "ca1nALSrokmvk6jK"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}