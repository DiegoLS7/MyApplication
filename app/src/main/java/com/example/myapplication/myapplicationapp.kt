package com.example.myapplication

import android.app.Application
import com.google.android.gms.ads.MobileAds

class myapplicationapp: Application() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}