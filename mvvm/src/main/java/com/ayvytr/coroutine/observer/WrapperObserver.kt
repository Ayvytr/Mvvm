package com.ayvytr.coroutine.observer

import android.arch.lifecycle.Observer
import android.content.res.Resources
import com.ayvytr.coroutine.internal.IInit
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.network.exception.ResponseException

/**
 * [Observer]包装类，如果[onSucceed]时，[T]没有值，泛型最好传[Unit]，不然会抛空指针异常.
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 */
abstract class WrapperObserver<T>(private val iInit: IInit? = null) :
    Observer<ResponseWrapper<T>> {

    override fun onChanged(wrapper: ResponseWrapper<T>?) {
        if (wrapper!!.isSucceed) {
            onSucceed(wrapper.dataNonNull, wrapper)
        } else {
            onError(wrapper.exception, wrapper.message, wrapper.messageStringId)
        }
    }

    abstract fun onSucceed(data: T, wrapper: ResponseWrapper<T>?)

    open fun onError(
        exception: ResponseException?,
        message: String,
        messageStringId: Int
    ) {
        iInit?.let {
            try {
                Resources.getSystem().getString(messageStringId)
                iInit.showMessage(messageStringId)
            } catch (e: Exception) {
                iInit.showMessage(message)
            }
        }
    }

}