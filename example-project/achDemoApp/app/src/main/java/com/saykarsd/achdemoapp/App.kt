package com.saykarsd.achdemoapp

import android.app.Application
import com.saykarsd.androidcomposehellper.debugging.Logcat

class App() :
Application() {
override fun onCreate() {
super.onCreate()
Logcat.init(this)
}
}

