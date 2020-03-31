package com.ayvytr.coroutine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayvytr.network.ApiClient
import com.ayvytr.network.bean.ResponseMessage
import kotlinx.coroutines.*

/**
 * @author EDZ
 */

open class BaseViewModel : ViewModel(), CoroutineScope by MainScope() {
    //是不是正在加载的LiveData，true：正在加载.
    val mLoadingLiveData = MutableLiveData<Boolean>()

    //接受网络请求的LiveData，建议订阅用来只接收网络请求错误.
    val mResponseLiveData = MutableLiveData<ResponseMessage>()

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
    private fun loading(isLoading: Boolean = true) {
        mLoadingLiveData.value = isLoading
    }


    /**
     * [launch] + [loading].
     */
    fun launchLoading(block: suspend () -> Unit) {
        launch(mNetworkExceptionHandler) {
            loading()
            block()
            loading(false)
        }
    }

}