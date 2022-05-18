package com.ayvytr.coroutines.api

import com.ayvytr.coroutines.bean.BaseGank
import com.ayvytr.coroutines.bean.Gank
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

/**
 * @author EDZ
 */
interface Api {
    @GET("data/iOS/2/1")
    suspend fun getIosGankSuspend(): BaseGank

    @GET("data/Android/2/1")
    suspend fun getAndroidGankSuspend(): BaseGank

    @GET("data/iOS/2/1")
    fun getIosGankDeferred(): Deferred<BaseGank>

    @GET("data/Android/2/1")
    fun getAndroidGankDeferred(): Deferred<BaseGank>

    @GET("data/iOS/2/1")
    fun getIosGank(): BaseGank

    @GET("data/Android/2/1")
    fun getAndroidGank(): BaseGank

    @GET("data/Android/2/1")
    fun getAndroidGankFlow(): Flow<BaseGank>
}
