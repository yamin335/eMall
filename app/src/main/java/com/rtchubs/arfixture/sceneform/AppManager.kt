package com.rtchubs.arfixture.sceneform

import android.app.Application
import android.os.StrictMode

class AppManager : Application() {
    override fun onCreate() {
        super.onCreate()

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }
}
