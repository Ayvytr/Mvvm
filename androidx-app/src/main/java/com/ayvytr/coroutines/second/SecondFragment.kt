package com.ayvytr.coroutines.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.ayvytr.coroutine.BaseCoroutineFragment
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.coroutines.R
import com.ayvytr.coroutines.main.MainViewModel
import com.ayvytr.ktx.ui.show
import com.ayvytr.logger.L
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author EDZ
 */
class SecondFragment : BaseCoroutineFragment<MainViewModel>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        launch {
            mViewModel.mLoadingLiveData.value = true
            delay(3000)
            mViewModel.mLoadingLiveData.value = false
        }

        btn_get_data.setOnClickListener {
//            mViewModel.getAndroidAndIos()
            mViewModel.getAndroidAndIosPost()
        }
//        mViewModel.androidAndIosLiveData.observe(this, Observer {
//            tv_value.text = it.toString()
//        })
        mViewModel.androidAndIosLiveDataPost.observe(this, Observer {
            L.e()
            tv_value.text = it.data.toString()
        })
    }

    override fun showLoading(isShow: Boolean) {
        pb.show(isShow)
    }
}