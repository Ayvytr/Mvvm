package com.ayvytr.coroutines

import android.os.Bundle
import com.ayvytr.coroutine.BaseCoroutineActivity
import com.ayvytr.ktx.ui.hide
import com.ayvytr.ktx.ui.show
import com.ayvytr.logger.L
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class TestActivity : BaseCoroutineActivity() {

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_test)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initData1()
    }

    override fun showError(error: String) {
        super.showError(error)
        tv_error.setText(error)
    }

    override fun showLoading() {
        super.showLoading()
        pb.show()
    }

    override fun hideLoading() {
        super.hideLoading()
        pb.hide()
    }

    private fun initData1() {
//        launch {
//            val iosGank = async {
//                ApiSource.getInstance().apiService.getIosGank().async()
//            }.await()
//
////                val androidGank = async {
////                    ApiSource.apiService.getAndroidGank().async()
////                }.await()
//
//
//            val list = mutableListOf<Gank>().apply {
//                iosGank?.results?.let {
//                    addAll(it)
//                }
//                //                    androidGank.results?.let {
//                //                        addAll(it)
//                //                    }
//            }
//
//            tv_value.text = list.size.toString()
//        }

//        launchWithLoading {
//            val iosGank = async {
//                ApiSource.getInstance().apiService.getIosGank().async()
//            }.await()
//
//            val androidGank = async {
//                ApiSource.getInstance().apiService.getIosGank().async()
//            }.await()
//
//
//            val list = mutableListOf<Gank>().apply {
//                iosGank.results?.let {
//                    addAll(it)
//                }
//                androidGank.results?.let {
//                    addAll(it)
//                }
//            }
//
//            tv_value.text = list.size.toString()
//        }

//        launchWithLoading {
//            val iosGank = async {
//                ApiSource.getInstance().apiService.getIosGank().await()
//            }.await()
//
//            val androidGank = async {
//                ApiSource.getInstance().apiService.getIosGank().await()
//            }.await()
//
//
//            val list = mutableListOf<Gank>().apply {
//                iosGank.results?.let {
//                    addAll(it)
//                }
//                androidGank.results?.let {
//                    addAll(it)
//                }
//            }
//
//            tv_value.text = list.size.toString()
//        }
        launchWithLoading {
            var i = 0
            while (isActive) {
                L.e(i)
                tv_value.text = i.toString()
                i++
                delay(1000)
            }
        }
    }
}
