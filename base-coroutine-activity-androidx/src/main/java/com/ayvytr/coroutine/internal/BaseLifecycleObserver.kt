package com.ayvytr.coroutine.internal

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 */
internal interface BaseLifecycleObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateEvent()
}