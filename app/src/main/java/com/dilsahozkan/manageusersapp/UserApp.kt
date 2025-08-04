package com.dilsahozkan.manageusersapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UserApp : Application() {
    companion object{
        var instance: UserApp? = null
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}