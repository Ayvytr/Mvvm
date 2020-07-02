package com.ayvytr.coroutine.internal

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 */
interface IInit {
    fun initView(savedInstanceState: Bundle?)
    fun initData(savedInstanceState: Bundle?)
    fun showLoading(isShow: Boolean = true)
    fun showMessage(@StringRes strId: Int)
    fun showMessage(message: String)
    /**
     * 初始化[androidx.lifecycle.LiveData.observe]，确保只在[android.app.Activity.onCreate]或
     * [Fragment.onCreate]调用一次，绝对不能在之后的生命周期调用，否则切换[Fragment]导致多次
     * 注册导致未知问题.
     */
    fun initLiveDataObserver()
}
