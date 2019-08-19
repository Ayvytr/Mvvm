package com.ayvytr.coroutine

/**
 * @author Ayvytr
 * @since 0.1.0
 */

/**
 * Convert Throwable to String, default return [Throwable.toString].
 */
fun Throwable.toVisibleString(): String {
    return toString()
//    return when {
//        this is UnknownHostException -> "网络未连接"
//        this is HttpException -> "${this.code()} ${this.message()}"
//        else -> toString()
//    }
}