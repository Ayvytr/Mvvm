package com.ayvytr.coroutine.internal

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent


/**
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 */
internal interface BaseLifecycleObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateEvent()
}