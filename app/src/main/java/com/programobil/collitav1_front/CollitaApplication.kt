package com.programobil.collitav1_front

import android.app.Application
import com.programobil.collitav1_front.security.TokenManager

class CollitaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TokenManager.initialize(this)
    }
} 