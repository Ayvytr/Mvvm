package com.ayvytr.coroutines

import android.app.Application
import com.ayvytr.logger.L
import com.ayvytr.network.ApiClient

/**
 * @author admin
 */

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        ApiClient.getInstance().init("https://gank.io/api/", cache = null)
        L.settings().showLog(BuildConfig.DEBUG)
//        ApiClient.throwable2ResponseMessage = {
//            ResponseMessage("哈哈", throwable = it)
//        }
    }
}