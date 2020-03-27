package com.ayvytr.coroutines

import android.app.Application
import com.ayvytr.network.ApiClient

/**
 * @author admin
 */

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        ApiClient.getInstance().init("https://gank.io/api/", cache = null)
    }
}