package com.ayvytr.coroutine

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.coroutines.*
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

/**
 * Base Fragment with coroutine, used to inherit it. You can launch coroutine with [launchWithLoading] or [launch], and
 * don't need to call [Job.cancel].
 * @author Ayvytr
 */
open class BaseCoroutineFragment : Fragment(), CoroutineScope by MainScope(), OnBackPressedListener {
    private val mBaseJob = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + mBaseJob

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = getContentView()
        if (contentView > 0) {
            return layoutInflater.inflate(contentView, container, false)
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
        initData(savedInstanceState)
    }

    open fun getContentView(): Int {
        return 0
    }

    open fun initView(savedInstanceState: Bundle?) {
    }

    open fun initData(savedInstanceState: Bundle?) {
    }


    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    protected open fun showLoading() {
    }


    protected open fun hideLoading() {
    }

    protected open fun showError(@StringRes strId: Int) {
        showError(getString(strId))
    }

    protected open fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
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

    override fun onBackPressed(): Boolean {
        return false
    }

}

