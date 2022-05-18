package com.ayvytr.coroutines.main

import androidx.lifecycle.MutableLiveData
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.coroutines.bean.BaseGank
import com.ayvytr.coroutines.bean.Gank
import com.ayvytr.logger.L
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.network.wrap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList

/**
 * @author EDZ
 */
class MainViewModel : BaseViewModel() {
    val repository = MainRepository()
    val androidGankLiveData = MutableLiveData<ResponseWrapper<BaseGank>>()
    val iosGankLiveData = MutableLiveData<ResponseWrapper<BaseGank>>()

    //    val androidAndIosLiveData = MutableLiveData<List<Gank>>()
    val androidAndIosLiveDataPost = MutableLiveData<ResponseWrapper<List<Gank>>>()

//    fun getAndroidGank() {
//        launchWrapper(androidGankLiveData) {
//            repository.getAndroidGank().wrap()
//        }
//    }
//
//    fun getIosGank() {
//        launchWrapper(iosGankLiveData) {
//            repository.getIosGank().wrap()
//        }
//    }

//    fun getAndroidAndIos() {
//        launchLoading {
//            L.e(System.currentTimeMillis())
//            val android = async { repository.getAndroidGank() }.await()
//            L.e(System.currentTimeMillis())
//            val ios = async { repository.getIosGank() }.await()
//            L.e(System.currentTimeMillis())
//            val list = android.results!!.toMutableList()
//            list.addAll(ios.results!!)
//            androidAndIosLiveData.value = list

//        }
//    }

    fun getAndroidAndIosPost() {
//        L.e(androidAndIosLiveDataPost.toString())
        val key = androidAndIosLiveDataPost.toString()
        cancelJob(key)
        val job = launchWrapper(androidAndIosLiveDataPost) {
            val android = async { repository.getAndroidGank() }.await()
            val ios = async { repository.getIosGank() }.await()
            val list = android.results!!.toMutableList()
            list.addAll(ios.results!!)
            ResponseWrapper(list.toImmutableList())
        }
        addJob(key, job)
    }

    fun <T> getAndroidPostFlow() {
//        return launchFlow(repository.api.getAndroidGank())
    }
}
