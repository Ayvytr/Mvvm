package com.ayvytr.coroutine.internal

import java.lang.reflect.ParameterizedType

/**
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 * @since 0.3.3
 */

/**
 * 获取vm clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}

