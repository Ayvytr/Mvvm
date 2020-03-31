package com.ayvytr.coroutine

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Base Fragment with coroutine, used to inherit it.
 * @author Ayvytr
 */
open class BaseCoroutineFragment<T : BaseViewModel> : Fragment(), IInit,
    CoroutineScope by MainScope() {

    protected lateinit var mViewModel: T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBaseViewModel()
        initView(savedInstanceState)
        initData(savedInstanceState)
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

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    override fun showLoading(isShow: Boolean) {

    }

    override fun showMessage(@StringRes strId: Int) {
        showMessage(getString(strId))
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}

