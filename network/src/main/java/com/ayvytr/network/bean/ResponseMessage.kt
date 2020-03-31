package com.ayvytr.network.bean


/**
 * Response message data class for http response, default for http response error.
 * @author ayvytr
 *
 * @param message network response message, default [Throwable.toString].
 * @param messageStringId string id.
 * @param throwable exception of network.
 */
class ResponseMessage(
    val message: String? = null,
    val messageStringId: Int? = -1,
    val code: Int = 0,
    throwable: Throwable? = null
)