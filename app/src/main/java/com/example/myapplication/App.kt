package com.example.myapplication

import android.app.Application
import com.example.myapplication.utils.SharedPref
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext

val prefs: SharedPref by lazy {
    App.prefs!!
}
class App : Application() {
    companion object {
        var prefs: SharedPref? = null
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        prefs = SharedPref(applicationContext)
    }
}