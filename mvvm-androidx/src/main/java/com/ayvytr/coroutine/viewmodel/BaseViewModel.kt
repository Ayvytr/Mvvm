package com.ayvytr.coroutine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayvytr.network.ApiClient
import com.ayvytr.network.bean.BaseResponse
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.network.wrap
import kotlinx.coroutines.*

/**
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 */
open class BaseViewModel : ViewModel(), CoroutineScope by MainScope() {
    //是不是正在加载的LiveData，true：正在加载.
    val mLoadingLiveData = MutableLiveData<Boolean>()

    //接受网络请求的LiveData，建议订阅用来只接收网络请求错误.
    val mResponseLiveData = MutableLiveData<BaseResponse>()

    /**
     * catch and parse http exception, use [mResponseLiveData] to observe.
     */
    protected var mNetworkExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            mLoadingLiveData.value = false
            mResponseLiveData.value = ApiClient.throwable2ResponseMessage.invoke(throwable)
        }

    override fun onCleared() {
        cancel()
    }


    /**
     * [launch] + [loading]，适合页面所有请求错误处理都一样的情况.
     * 建议使用[launchWrapper]，更适合灵活多变的情况.
     */
    fun launchLoading(showLoading: Boolean = true, block: suspend () -> Unit): Job {
        return launch(mNetworkExceptionHandler) {
            if (showLoading) {
                mLoadingLiveData.value = true
            }
            block()
            if (showLoading) {
                mLoadingLiveData.value = false
            }
        }
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
                liveData.postValue(it.wrap<T>())
            }

            if (showLoading) {
                mLoadingLiveData.value = false
            }
        }
    }

}