package com.ayvytr.coroutine.observer

import androidx.lifecycle.Observer
import com.ayvytr.coroutine.internal.IInit
import com.ayvytr.coroutine.internal.showMessage
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.network.exception.ResponseException

/**
 * [Observer]包装类，如果[onSucceed]时，[T]没有值，泛型最好传[Unit]，不然会抛空指针异常.
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 */
abstract class WrapperObserver<T>(private val iInit: IInit? = null) :
    Observer<ResponseWrapper<T>> {

    override fun onChanged(wrapper: ResponseWrapper<T>) {
        if (wrapper.isSucceed) {
            onSucceed(wrapper.dataNonNull)
        } else {
            onError(wrapper.exception!!)
        }
    }

    abstract fun onSucceed(data: T)

    open fun onError(exception: ResponseException) {
        iInit?.showMessage(exception)
    }

}