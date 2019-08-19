package com.ayvytr.coroutines

import android.app.Application

/**
 * @author admin
 */

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        ApiSource.getInstance(applicationContext).init()
    }
}