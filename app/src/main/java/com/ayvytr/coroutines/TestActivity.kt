package com.ayvytr.coroutines

import android.os.Bundle
import com.ayvytr.commonlibrary.bean.Gank
import com.ayvytr.logger.L
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_test)
    }

    override fun initData(savedInstanceState: Bundle?) {
        launch {
            val iosGank = async {
                ApiSource.apiService.getIosGank().async()
            }.await()

            val androidGank = async {
                ApiSource.apiService.getAndroidGank().async()
            }.await()


            val list = mutableListOf<Gank>().apply {
                iosGank?.results?.let {
                    addAll(it)
                }
                androidGank?.results?.let {
                    addAll(it)
                }
            }

            L.e(list)
        }
    }
}
