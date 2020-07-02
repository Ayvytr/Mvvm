package com.ayvytr.coroutine.observer

import androidx.lifecycle.Observer
import com.ayvytr.network.bean.ResponseWrapper

/**
 * [Observer]包装类，如果[onSucceed]时，[T]没有值，泛型最好传[Unit]，不然会抛空指针异常.
 * @author Administrator
 */
interface WrapperObserver<T> :
    Observer<ResponseWrapper<T>> {
    override fun onChanged(wrapper: ResponseWrapper<T>) {
        if (wrapper.isSucceed) {
            onSucceed(wrapper.data!!)
        } else {
            onError(wrapper.exception!!, wrapper.message, wrapper.messageStringId)
        }
    }

    fun onSucceed(data: T)
    fun onError(exception: Throwable, message: String, messageStringId: Int) {}
}