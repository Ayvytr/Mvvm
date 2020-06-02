package com.ayvytr.coroutine.bean

/**
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 * @since 0.3.3
 */
data class ResponseWrapper<T>(
    val data: T?,
    val isSucceed: Boolean = true,
    val page: Int = 1,
    val isLoadMore: Boolean = false,
    val hasMore: Boolean = false,
    val exception: Throwable? = null
)

