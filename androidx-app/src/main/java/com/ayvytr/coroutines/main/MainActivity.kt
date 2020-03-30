package com.ayvytr.coroutines.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ayvytr.coroutine.BaseCoroutineActivity
import com.ayvytr.coroutines.R
import com.ayvytr.ktx.ui.hide
import com.ayvytr.ktx.ui.show
import com.ayvytr.logger.L
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseCoroutineActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun showLoading(isShowLoading: Boolean) {
        pb.show(isShowLoading)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.androidGankLiveData.observe(this, Observer {
            tv_value.text = it.toString()
        })
        mainViewModel.androidAndIosLiveData.observe(this, Observer {
            tv_value.text = it.toString()
            tv_error.text = null
        })

        mainViewModel.loadingLiveData.observe(this, Observer {
            L.e("loadingLiveData", it)
            showLoading(it)
        })
        mainViewModel.errorLiveData.observe(this, Observer {
            L.e("errorLiveData", it)
            pb.hide()
            tv_error.text = it.errorMessage
            showMessage(it.errorMessage!!)
        })

        btn_get_data.setOnClickListener {
//            mainViewModel.getAndroidGank()
            mainViewModel.getAndroidAndIos()
        }

//        mainViewModel.getAndroidGank()
        mainViewModel.getAndroidAndIos()

    }


}
