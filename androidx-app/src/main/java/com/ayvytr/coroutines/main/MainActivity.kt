package com.ayvytr.coroutines.main

import android.os.Bundle
import com.ayvytr.coroutine.BaseActivity
import com.ayvytr.coroutine.observer.WrapperListObserver
import com.ayvytr.coroutine.observer.WrapperObserver
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.coroutines.R
import com.ayvytr.coroutines.bean.Gank
import com.ayvytr.ktx.ui.hide
import com.ayvytr.ktx.ui.show
import com.ayvytr.logger.L
import com.ayvytr.network.exception.ResponseException
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {


    override fun showLoading(isShow: Boolean) {
        pb.show(isShow)
    }

//    override fun getViewModelClass(): Class<MainViewModel> {
//        return MainViewModel::class.java
//    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initData(savedInstanceState: Bundle?) {
//        mViewModel.androidAndIosLiveData.observe(this, Observer {
//            tv_value.text = it.toString()
//            tv_error.text = null
//        })

        btn_get_data.setOnClickListener {
//            mViewModel.getAndroidAndIos()
            mViewModel.getAndroidAndIosPost()
        }
//
//        mViewModel.androidAndIosLiveDataPost.observe(
//            this,
//            object : WrapperListObserver<List<Gank>>(this) {
//                override fun onSucceed(
//                    data: List<Gank>,
//                    page: Int,
//                    loadMore: Boolean,
//                    hasMore: Boolean
//                ) {
//                    tv_value.text = data.toString()
//                    tv_error.text = null
//                }
//
//                override fun onError(exception: ResponseException) {
//                    super.onError(exception)
//                    tv_value.text = null
//                    tv_error.text = exception.message
//                }
//            })
        mViewModel.androidAndIosLiveDataPost.observe(
            this,
            object : WrapperObserver<List<Gank>>(this) {
                override fun onSucceed(data: List<Gank>) {
                    tv_value.text = data.toString()
                    tv_error.text = null
                }

                override fun onError(exception: ResponseException) {
                    super.onError(exception)
                    tv_value.text = null
                    tv_error.text = exception.message
                }
            })
    }

    override fun showMessage(message: String) {
        super.showMessage(message)
//        L.e("errorLiveData", message)
        tv_error.text = message
        pb.hide()
    }
}
