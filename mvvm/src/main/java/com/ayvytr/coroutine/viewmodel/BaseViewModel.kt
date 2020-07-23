package com.ayvytr.coroutine.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.network.wrap
import kotlinx.coroutines.*

/**
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 */
open class BaseViewModel : ViewModel(), CoroutineScope by MainScope() {
    //是不是正在加载的LiveData，true：正在加载.
    val mLoadingLiveData = MutableLiveData<Boolean>()

    val jobMap by lazy {
        hashMapOf<String, Job>()
    }

    override fun onCleared() {
        cancel()
    }

    /**
     * [launch]+[loading]+[liveData]，[ResponseWrapper]封装了响应体，适合不同接口错误处理不一致的情况.
     */
    fun <T> launchWrapper(
        liveData: MutableLiveData<ResponseWrapper<T>>,
        showLoading: Boolean = true,
        function: suspend () -> ResponseWrapper<T>
    ): Job {
        return launch {
            if (showLoading) {
                mLoadingLiveData.value = true
            }

            runCatching {
                function()
            }.onSuccess {
                liveData.postValue(it)
            }.onFailure {
                /**
                 * 不推[Job.cancel]引发的[CancellationException].
                 */
                if (it !is CancellationException) {
                    liveData.postValue(it.wrap<T>())
                }
            }

            if (showLoading) {
                mLoadingLiveData.value = false
            }
        }
    }

    @Synchronized
    fun cancelJob(key: String) {
        val job = jobMap[key]
        job?.apply {
            if (isActive) {
                cancel()
            }
            jobMap.remove(key)
        }
    }

    @Synchronized
    fun addJob(key: String, job: Job) {
        cancelJob(key)
        jobMap[key] = job
    }
}