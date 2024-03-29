package com.ayvytr.coroutines.main

import com.ayvytr.coroutines.api.Api
import com.ayvytr.coroutines.bean.BaseGank
import com.ayvytr.network.ApiClient

class MainRepository {
    val api = ApiClient.create(Api::class.java)

    suspend fun getAndroidGank(): BaseGank {
        return api.getAndroidGankSuspend()
    }

    suspend fun getIosGank(): BaseGank {
        return api.getIosGankSuspend()
    }
}
