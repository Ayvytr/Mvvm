package com.ayvytr.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Extension function of [Call].
 * @author Ayvytr
 */
suspend fun <T> Call<T>.async(): T {
    return suspendCoroutine {
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    //response.body=null的情况未处理，如果有问题后续再进行修改
                    response.body()?.apply {
                        it.resume(this)
                    }
                } else {
                    it.resumeWithException(HttpException(response))
                }
            }
        })
    }
}

