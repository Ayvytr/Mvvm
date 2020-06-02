package com.ayvytr.coroutine

import android.os.Bundle
import android.support.annotation.StringRes

/**
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 */
interface IInit {
    fun initView(savedInstanceState: Bundle?)
    fun initData(savedInstanceState: Bundle?)
    fun showLoading(isShow: Boolean = true)
    fun showMessage(@StringRes strId: Int)
    fun showMessage(message: String)
}
