package com.ayvytr.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
    }

    private fun initData() {
//        ApiSource.apiService.getAndroidGank().enqueue(object : Callback<BaseGank> {
//
//            override fun onFailure(call: Call<BaseGank>, t: Throwable) {
//                L.e(call, t)
//            }
//
//            override fun onResponse(call: Call<BaseGank>, response: Response<BaseGank>) {
//                if (response.isSuccessful) {
//                    L.e(response.toString())
//                } else {
//                    L.e(response.errorBody())
//                }
//            }
//        })

//        launch {
//            queryGanksOrderly()
//        }

//        launch {
//            queryGanksAsync()
//        }

//        launch {
//            queryGanksJack()
//        }

//        launch {
//            try {
//                val androidGankSuspend = ApiSource.apiService.getAndroidGankSuspend()
//                androidGankSuspend.results!!
//            } catch (e: Exception) {
//            }
//            try {
//                val iosGankSuspend = ApiSource.apiService.getIosGankSuspend()
//                iosGankSuspend.results!!
//            } catch (e: Exception) {
//            }
//        }

//        //尝试封装
//        launch {
//            val iosDeferred = async {
//                ApiSource.apiService.getIosGank().async().also {
//                    L.e(it)
//                }
//            }
//
//            val androidDeferred = async {
//                ApiSource.apiService.getAndroidGank().async().also {
//                    L.e(it)
//                }
//            }
//
//            val list = mutableListOf<Gank>().apply {
////                addAll(iosDeferred.await()!!.results!!)
////                addAll(androidDeferred.await()!!.results!!)
//            }
//
//            L.e(list)
//        }
    }


//    suspend fun queryGanksJack(): List<Gank> {
////        return withContext(Dispatchers.Main) {
//        val result = mutableListOf<Gank>().apply {
//            addAll(ApiSource.apiService.getAndroidGankDeferred().await().results!!)
//            addAll(ApiSource.apiService.getIosGankDeferred().await().results!!)
//        }
//
//        return result
////        }
//    }

//    suspend fun queryGanksAsync(): List<Gank> {
//        return withContext(Dispatchers.Main) {
//            try {
//                val androidDeferred = async {
//                    L.e(Calendar.getInstance().time, Calendar.getInstance().get(Calendar.MILLISECOND))
//                    val baseGank = ApiSource.apiService.getAndroidGank().wait()
//                    L.e(Calendar.getInstance().time, Calendar.getInstance().get(Calendar.MILLISECOND))
//                    baseGank
//                }
//                val iosDeferred = async {
//                    L.e(Calendar.getInstance().time, Calendar.getInstance().get(Calendar.MILLISECOND))
//                    val baseGank = ApiSource.apiService.getIosGank().wait()
//                    L.e(Calendar.getInstance().time, Calendar.getInstance().get(Calendar.MILLISECOND))
//                    baseGank
//                }
//
//                val androidResult = androidDeferred.await().results
//                val iosResult = iosDeferred.await().results
//
//                val result = mutableListOf<Gank>().apply {
//                    addAll(androidResult!!)
//                    addAll(iosResult!!)
//                }
//
//                result
//            } catch (e: Throwable) {
//                e.printStackTrace()
//                throw e
//            }
//        }
//    }

//    suspend fun queryGanksOrderly(): List<Gank> {
//        return try {
//            L.e(Calendar.getInstance().time, Calendar.getInstance().get(Calendar.MILLISECOND))
//            val androidResult = ApiSource.apiService.getAndroidGank().wait()
//            L.e(Calendar.getInstance().time, Calendar.getInstance().get(Calendar.MILLISECOND))
//            val iosResult = ApiSource.apiService.getIosGank().wait()
//            L.e(Calendar.getInstance().time, Calendar.getInstance().get(Calendar.MILLISECOND))
//
//            val result = mutableListOf<Gank>().apply {
//                addAll(androidResult.results!!)
//                addAll(iosResult.results!!)
//            }
//
//            return result
//        } catch (e: Throwable) {
//            e.printStackTrace()
//            throw e
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }


}
