package com.ayvytr.coroutine

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Base Activity with coroutine, used to inherit it.
 * @author Ayvytr
 */
open class BaseCoroutineActivity<T : BaseViewModel> : AppCompatActivity(), IInit,
    CoroutineScope by MainScope() {

    lateinit var baseObserver: BaseObserver

    protected lateinit var mViewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseObserver = object : BaseObserver {
            override fun onCreateEvent() {
                initView(savedInstanceState)
                initData(savedInstanceState)
            }
        }
        lifecycle.addObserver(baseObserver)
        initBaseViewModel()
    }

    /**
     * 如果继承的子类传入的泛型不是[BaseViewModel],需要重写这个方法，提供自定义的[BaseViewModel]子类.
     */
    protected open fun getViewModelClass(): Class<T> {
        return BaseViewModel::class.java as Class<T>
    }

    open fun initBaseViewModel() {
        mViewModel = ViewModelProvider(this)[getViewModelClass()]
        mViewModel.mLoadingLiveData.observe(this, Observer {
            showLoading(it)
        })
        mViewModel.mResponseLiveData.observe(this, Observer {
            showMessage(it.message!!)
        })
    }

    /**
     * 没有提供getLayoutRes方法获取子类布局，可以在[onCreate]，或者[initView]中调用[setContentView]
     * 初始化view.
     */
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
        lifecycle.removeObserver(baseObserver)
    }

    override fun showLoading(isShow: Boolean ) {

    }

    override fun showMessage(@StringRes strId: Int) {
        showMessage(getString(strId))
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

