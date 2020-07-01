package com.ayvytr.coroutine

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.res.Resources
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ayvytr.coroutine.internal.IInit
import com.ayvytr.coroutine.internal.getVmClazz
import com.ayvytr.coroutine.viewmodel.BaseViewModel

/**
 * BaseFragment.
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 */
abstract class BaseFragment<T : BaseViewModel> : Fragment(),
    IInit {

    protected lateinit var mViewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initLiveDataObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return try {
            inflater.inflate(getLayoutId(), container, false)
        } catch (e: Resources.NotFoundException) {
            if(BuildConfig.DEBUG) {
                e.printStackTrace()
            }
            return super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    @LayoutRes
    open fun getLayoutId(): Int {
        return -1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
        initData(savedInstanceState)
    }

    /**
     * 如果继承的子类传入的泛型不是[BaseViewModel],需要重写这个方法，提供自定义的[BaseViewModel]子类.
     *
     * 注意：[getVmClazz]报如下错时需要重写这个方法显式指明[T]的类型（这个情况是继承多层Fragment后没有获取到
     * 泛型导致的）：
     * ClassCastException: java.lang.Class cannot be cast to java.lang.reflect.ParameterizedType
     */
    protected open fun getViewModelClass(): Class<T> {
        return getVmClazz(this) as Class<T>
    }

    open fun initViewModel() {
        mViewModel = ViewModelProvider(viewModelStore,
            ViewModelProvider.NewInstanceFactory()).get(getViewModelClass())
        mViewModel.mLoadingLiveData.observe(this, Observer {
            showLoading(it!!)
        })
        mViewModel.mResponseLiveData.observe(this, Observer {
            showMessage(it!!.message)
        })
    }


    override fun initLiveDataObserver() {

    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData(savedInstanceState: Bundle?) {
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

