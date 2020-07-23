package com.ayvytr.coroutine.internal

import android.content.res.Resources
import com.ayvytr.network.exception.ResponseException
import java.lang.reflect.ParameterizedType

/**
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 * @since 0.1.4
 */
internal fun IInit.showMessage(exception: ResponseException) {
    try {
        Resources.getSystem().getString(exception.messageStringId)
        showMessage(exception.messageStringId)
    } catch (e: Exception) {
        if (!exception.message.isNullOrEmpty()) {
            showMessage(exception.message!!)
        }
    }
}

/**
 * 获取vm clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}

