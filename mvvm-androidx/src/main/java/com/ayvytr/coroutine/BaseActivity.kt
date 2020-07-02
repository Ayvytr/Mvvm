package com.ayvytr.coroutine

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ayvytr.coroutine.internal.BaseLifecycleObserver
import com.ayvytr.coroutine.internal.IInit
import com.ayvytr.coroutine.internal.getVmClazz
import com.ayvytr.coroutine.viewmodel.BaseViewModel

/**
 * BaseActivity.
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 */
open class BaseActivity<T : BaseViewModel> : AppCompatActivity(),
    IInit {

    private lateinit var baseObserver: BaseLifecycleObserver

    protected lateinit var mViewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseObserver = object :
            BaseLifecycleObserver {
            override fun onCreateEvent() {
                initView(savedInstanceState)
                initData(savedInstanceState)
            }
        }
        lifecycle.addObserver(baseObserver)
        initViewModel()
        initLiveDataObserver()
    }

    override fun initLiveDataObserver() {

    }


    open fun initViewModel() {
        mViewModel = ViewModelProvider(this)[getViewModelClass()]
        mViewModel.mLoadingLiveData.observe(this, Observer {
            showLoading(it)
        })
        mViewModel.mResponseLiveData.observe(this, Observer {
            showMessage(it.message)
        })
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
        lifecycle.removeObserver(baseObserver)
    }

    override fun showLoading(isShow: Boolean) {

    }

    override fun showMessage(@StringRes strId: Int) {
        showMessage(getString(strId))
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

