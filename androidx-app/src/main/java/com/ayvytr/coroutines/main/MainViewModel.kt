package com.ayvytr.coroutines.main

import androidx.lifecycle.MutableLiveData
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.coroutines.bean.BaseGank
import com.ayvytr.coroutines.bean.Gank
import com.ayvytr.logger.L
import com.ayvytr.network.bean.ResponseWrapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import okhttp3.internal.toImmutableList

/**
 * @author EDZ
 */
class MainViewModel : BaseViewModel() {
    private val repository = MainRepository()
    val androidGankLiveData = MutableLiveData<BaseGank>()
    val iosGankLiveData = MutableLiveData<BaseGank>()

    //    val androidAndIosLiveData = MutableLiveData<List<Gank>>()
    val androidAndIosLiveDataPost = MutableLiveData<ResponseWrapper<List<Gank>>>()

    val jobMap by lazy {
        hashMapOf<String, Job>()
    }

    fun getAndroidGank() {
        launchLoading {
            androidGankLiveData.value = repository.getAndroidGank()
        }
    }

    fun getIosGank() {
        launchLoading {
            iosGankLiveData.value = repository.getIosGank()
        }
    }

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
        L.e(androidAndIosLiveDataPost.toString())
        val key = androidAndIosLiveDataPost.toString()
        var job = jobMap[key]
        job?.cancel()
        job = launchWrapper(androidAndIosLiveDataPost) {
            val android = async { repository.getAndroidGank() }.await()
            val ios = async { repository.getIosGank() }.await()
            val list = android.results!!.toMutableList()
            list.addAll(ios.results!!)
            ResponseWrapper(list.toImmutableList())
        }
        jobMap[key] = job
    }
}
