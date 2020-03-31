package com.ayvytr.coroutines.second

import android.os.Bundle
import com.ayvytr.coroutine.BaseCoroutineActivity
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.coroutines.R
import com.ayvytr.ktx.ui.getContext
import com.ayvytr.ktx.ui.show
import kotlinx.android.synthetic.main.activity_main.*

class SecondActivity : BaseCoroutineActivity<BaseViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_second)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        getContext()
        setTitle("SecondFragment")
        supportFragmentManager.beginTransaction()
            .add(R.id.fl, SecondFragment(), SecondFragment::class.java.name)
            .commit()
//        launch{
//            mViewModel.mLoadingLiveData.value = true
//            delay(2000)
//            mViewModel.mLoadingLiveData.value = false
//        }
    }

    override fun showLoading(isShow: Boolean) {
        pb.show(isShow)
    }
}
