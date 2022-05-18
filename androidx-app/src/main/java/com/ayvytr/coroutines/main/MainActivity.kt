package com.ayvytr.coroutines.main

import android.os.Bundle
import com.ayvytr.coroutine.BaseActivity
import com.ayvytr.coroutines.R
import com.ayvytr.ktx.ui.show
import com.ayvytr.logger.L
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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
        btn_get_data.setOnClickListener {
            GlobalScope.launch {
                flow {
//                    emit(mViewModel.repository.getAndroidGank())
                    L.e(Thread.currentThread().name)
                    emit("fff")
                }.flowOn(Dispatchers.IO)
                    .onStart {
                        showMessage("${Thread.currentThread().name} 开始")
                        showLoading()
                    }
                    .catch {
                        showMessage(it.message!!)
                    }
                    .onCompletion {
                        showLoading(false)
                        if (it != null)
                            showMessage(Thread.currentThread().name + " " + it.message!!)
                    }
                    .flowOn(Dispatchers.Main)
                    .collect {
                        L.e(Thread.currentThread().name, it)
                        tv_value.text = it.toString()
                    }

            }

        }

    }

}
