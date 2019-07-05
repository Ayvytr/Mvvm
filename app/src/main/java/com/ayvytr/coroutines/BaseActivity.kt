package com.ayvytr.coroutines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ayvytr.ktx.context.toast
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author admin
 */
open class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initExtra(savedInstanceState)
        initView(savedInstanceState)
        initData(savedInstanceState)
    }

    open fun initData(savedInstanceState: Bundle?) {

    }

    open fun initView(savedInstanceState: Bundle?) {

    }

    open fun initExtra(savedInstanceState: Bundle?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
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
                            it.resume(response.body()!!)
                        } else {
                            it.resumeWithException(Throwable(response.toString()))
                        }
                    }

                })
            }
        } catch (e: Exception) {
            showError(e.toString())
            return null
        }
    }

    private fun showError(error: String) {
        toast(error)
    }
}