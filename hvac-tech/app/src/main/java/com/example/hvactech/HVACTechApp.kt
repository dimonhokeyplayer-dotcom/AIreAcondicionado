package com.example.hvactech

import android.app.Application
import com.example.hvactech.core.ServiceLocator

class HVACTechApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
    }
}

