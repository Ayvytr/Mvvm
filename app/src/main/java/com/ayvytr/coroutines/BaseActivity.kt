package com.ayvytr.coroutines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * @author admin
 */
open class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initExtra(savedInstanceState)
        initView(savedInstanceState)
        initData(savedInstanceState)
    }

    private fun initData(savedInstanceState: Bundle?) {

    }

    private fun initView(savedInstanceState: Bundle?) {

    }

    private fun initExtra(savedInstanceState: Bundle?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}