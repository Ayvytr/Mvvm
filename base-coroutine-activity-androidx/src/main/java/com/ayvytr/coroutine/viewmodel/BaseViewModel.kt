package com.ayvytr.coroutine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayvytr.coroutine.ErrorResponse
import kotlinx.coroutines.*

/**
 * @author EDZ
 */

open class BaseViewModel : ViewModel(), CoroutineScope by MainScope() {
    //是不是正在加载的LiveData，true：正在加载
    val loadingLiveData = MutableLiveData<Boolean>()

    //接受错误信息的LiveData
    val errorLiveData = MutableLiveData<ErrorResponse>()

    val networkExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, exception ->
            loadingLiveData.value = false
            errorLiveData.value = parseNetworkResponse(exception)
        }

    override fun onCleared() {
        cancel()
    }

    private fun loading(isLoading: Boolean = true) {
        loadingLiveData.value = isLoading
    }

    /**
     * 把异常转换成需要在界面上提示的文本.
     */
    fun parseNetworkResponse(exception: Throwable): ErrorResponse {
        return ErrorResponse(exception.toString(), exception = exception)
    }

    /**
     * [launch] + [loading]，封装方法，简化显示/隐藏loading
     */
    fun launchLoading(block: suspend () -> Unit) {
        launch(networkExceptionHandler) {
            loading()
            block()
            loading(false)
        }
    }

}