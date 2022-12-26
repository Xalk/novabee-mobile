package com.example.novabee

import android.app.Application
import android.content.res.Resources
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NovabeeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }



    companion object {
        lateinit var instance: NovabeeApplication
            private set
    }
}