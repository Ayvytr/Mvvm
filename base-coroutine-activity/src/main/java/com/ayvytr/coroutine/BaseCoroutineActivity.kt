package com.ayvytr.coroutine

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

interface BaseObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated()
}

/**
 * Base Activity with coroutine, used to inherit it. You can launch coroutine with [launchWithLoading] or [launch], and
 * don't need to call [Job.cancel].
 * @author Ayvytr
 */
open class BaseCoroutineActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    /**
     * Must use field of [Job].
     */
    private val mBaseJob = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + mBaseJob

    lateinit var baseObserver: BaseObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val contentView = getContentView()
//        if (contentView > 0) {
//            setContentView(contentView)
//        }
        baseObserver = object : BaseObserver {
            override fun onCreated() {
                initView(savedInstanceState)
                initData(savedInstanceState)
            }
        }
        lifecycle.addObserver(baseObserver)
    }


    open fun initView(savedInstanceState: Bundle?) {
    }

    open fun initData(savedInstanceState: Bundle?) {
    }


    override fun onDestroy() {
        super.onDestroy()
        cancel()
        lifecycle.removeObserver(baseObserver)
    }

    protected open fun showLoading() {
    }


    protected open fun hideLoading() {
    }

    protected open fun showError(@StringRes strId: Int) {
        showError(getString(strId))
    }

    protected open fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    /**
     * Launch coroutine with [showLoading], [block] and [hideLoading], if you don't need [showLoading] or [hideLoading],
     * just call [launch].
     */
    fun launchWithLoading(block: suspend () -> Unit) {
        launch {
            showLoading()
            try {
                block()
            } catch (e: Exception) {
                when (e) {
                    //Ignore CancellationException
                    is CancellationException -> {
                    }
                    else -> {
                        showError(getExceptionString(e))
                    }
                }
            }
            hideLoading()
        }
    }

    /**
     * Convert Exception to a string that can displayed on the interface, used when you need to parse conversion error
     * information (such as multi-language configuration), overwrite it.
     *
     * @param e Exception
     * @see [Throwable.toVisibleString]
     */
    protected open fun getExceptionString(e: Exception) = e.toVisibleString()

}

