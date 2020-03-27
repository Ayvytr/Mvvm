package com.ayvytr.coroutine

import androidx.annotation.StringRes

/**
 * @author EDZ
 */

class ErrorResponse(
    val errorMessage: String? = null,
    @StringRes val errorStringRes: Int? = -1,
    exception: Throwable? = null
)