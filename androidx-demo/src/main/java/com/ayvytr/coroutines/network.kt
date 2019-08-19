package com.ayvytr.coroutines

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author admin
 */

/**
 * 判断网络是否可用
 *
 * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
 *
 * @return `true`: 可用<br></br>`false`: 不可用
 */
fun Context.isAvailable(): Boolean {
    val cm = getConnectivityManager()
    val activeNetworkInfo = cm.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isAvailable
}

fun Context.getConnectivityManager(): ConnectivityManager {
    return getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}

/**
 * 判断网络是否连接
 *
 * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
 *
 * @return `true`: 是<br></br>`false`: 否
 */
fun Context.isConnected(): Boolean {
    val cm = getConnectivityManager()
    val activeNetworkInfo = cm.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

