package com.ayvytr.coroutine.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
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
    val mNetworkExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            mLoadingLiveData.value = false
            mResponseLiveData.value = ApiClient.throwable2ResponseMessage.invoke(throwable)
        }

    override fun onCleared() {
        cancel()
    }

    /**
     * show loading or hide loading.
     * @param isLoading `true`: show loading
     */
    fun loading(isLoading: Boolean = true) {
        mLoadingLiveData.value = isLoading
    }


    /**
     * [launch] + [loading].
     */
    fun launchLoading(showLoading: Boolean = true, block: suspend () -> Unit) {
        launch(mNetworkExceptionHandler) {
            if(showLoading) {
                loading()
            }
            block()
            if(showLoading) {
                loading(false)
            }
        }
    }

    fun <T> launchWrapper(
        liveData: MutableLiveData<ResponseWrapper<T>>,
        showLoading: Boolean = true,
        function: suspend () -> ResponseWrapper<T>
    ) {
        launch {
            if (showLoading) {
                loading()
            }

            runCatching {
                function()
            }.onSuccess {
                liveData.postValue(it)
            }.onFailure {
                liveData.postValue(it.wrap<T>())
            }

            if (showLoading) {
                loading(false)
            }
        }
    }

}