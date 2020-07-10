package com.ayvytr.coroutine.internal

import android.app.Activity
import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 */
/**
 * 用法：
 * ```kotlin
 * val vm by viewModel<CacheVM>()
 * ```
 */
private inline fun <reified T : ViewModel> FragmentActivity.viewModel() =
    lazy { ViewModelProvider(this).get(T::class.java) }

/**
 * 获取参数
 * 使用：
 * ```kotlin
 * val link by arg<String>("link")
 * ```
 */
private inline fun <reified T> Activity.arg(key: String) =
    lazy {
        val ret = when (T::class.java) {
            String::class.java -> intent.getStringExtra(key)
            Int::class.java -> intent.getIntExtra(key, 0)
            Float::class.java -> intent.getFloatExtra(key, 0f)
            Boolean::class.java -> intent.getBooleanExtra(key, false)
            else -> (intent.getParcelableExtra(key) as Parcelable)
        }
        ret as T?
    }