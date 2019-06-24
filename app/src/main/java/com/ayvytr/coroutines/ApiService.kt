package com.ayvytr.coroutines

import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.okhttploginterceptor.LoggingInterceptor
import com.ayvytr.okhttploginterceptor.LoggingLevel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author admin
 */

interface ApiService {
    @GET("data/iOS/2/1")
    fun getIosGank(): Call<BaseGank>

    @GET("data/Android/2/1")
    fun getAndroidGank(): Call<BaseGank>
}

object ApiSource {
    @JvmField
    val loggingInterceptor = LoggingInterceptor()

    init {
        loggingInterceptor.level = LoggingLevel.SINGLE
    }

    @JvmField
    val okhttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    @JvmField
    val retrofit = Retrofit.Builder()
        .baseUrl("http://gank.io/api/")
        .client(okhttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    @JvmField
    val apiService = retrofit.create(ApiService::class.java)
}

suspend fun <T> Call<T>.wait(): T {
    return suspendCoroutine {
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    it.resume(response.body()!!)
                } else {
                    it.resumeWithException(Throwable(response.toString()))
                }
            }

        })
    }
}