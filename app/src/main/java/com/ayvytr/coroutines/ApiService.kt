package com.ayvytr.coroutines

import android.content.Context
import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.logger.L
import com.ayvytr.okhttploginterceptor.LoggingInterceptor
import com.ayvytr.okhttploginterceptor.LoggingLevel
import kotlinx.coroutines.Deferred
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.*
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

    @GET("data/iOS/2/1")
    fun getIosGankDeferred(): Deferred<BaseGank>

    @GET("data/Android/2/1")
    fun getAndroidGankDeferred(): Deferred<BaseGank>

    @GET("data/iOS/2/1")
    suspend fun getIosGankSuspend(): BaseGank

    @GET("data/Android/2/1")
    suspend fun getAndroidGankSuspend(): BaseGank
}

class ApiSource private constructor(private var context: Context?) {
    val apiService: ApiService
    val okhttpClient: OkHttpClient
    val retrofit: Retrofit

    init {
        L.e("init")
        val builder = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor(LoggingLevel.SINGLE))
            .addInterceptor(HttpCacheInterceptor(context, maxStaleSeconds = 3600 * 24))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
        context?.also {
            L.e(it.cacheDir)
            builder.cache(Cache(it.cacheDir, 60 * 1024 * 1024))
        }
        okhttpClient = builder .build()
        retrofit = Retrofit.Builder()
            .baseUrl("http://gank.io/api/")
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    fun init() {
    }

    companion object {
        @Volatile
        private var apiSource: ApiSource? = null

        fun getInstance(): ApiSource {
            return getInstance(null)
        }

        fun getInstance(context: Context?): ApiSource {
            if (apiSource == null) {
                synchronized(ApiSource::class) {
                    if (apiSource == null) {
                        apiSource = ApiSource(context)
                    }
                }
            }
            return apiSource!!
        }
    }
}

suspend fun <T> Call<T>.async(): T? {
    try {
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        response.body()?.let { r ->
                            it.resume(r)
                        }
                    } else {
                        it.resumeWithException(HttpException(response))
                    }
                }
            })
        }
    } catch (e: Exception) {
        L.e(e)
        return null
    }
}
