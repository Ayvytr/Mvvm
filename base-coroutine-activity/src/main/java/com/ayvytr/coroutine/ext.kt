package com.ayvytr.coroutine

import com.ayvytr.coroutine.bean.ResponseWrapper
import java.lang.reflect.ParameterizedType

/**
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 * @since 0.3.3
 */

fun <T> Throwable.wrapper(): ResponseWrapper<T> {
    return ResponseWrapper(null, isSucceed = false, exception = this)
}

fun <T> T.wrapper(
    isSucceed: Boolean = true,
    page: Int = 1,
    isLoadMore: Boolean = false,
    hasMore: Boolean = false,
    exception: Exception? = null
): ResponseWrapper<T> {
    return ResponseWrapper(this, isSucceed, page, isLoadMore, hasMore, exception)
}

/**
 * 获取vm clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}

