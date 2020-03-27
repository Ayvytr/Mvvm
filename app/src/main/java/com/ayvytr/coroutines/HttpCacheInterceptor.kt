package com.ayvytr.coroutines

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @author ayvytr
 */
class HttpCacheInterceptor @JvmOverloads constructor(
    private val context: Context? = null,
    private val needCache: Boolean = true,
    private val maxStaleSeconds: Int = 3600
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        /**
         * App第一次启动，且没有网络时，会返回504
         * @see okhttp3.internal.cache.CacheInterceptor
         */
        val response = chain.proceed(chain.request().newBuilder().build())
        if (response.code == 504) {
            return chain.proceed(chain.request())
        }

        if (context == null || !needCache) {
            return chain.proceed(chain.request())
        }

        if (context.isAvailable()) {
            return chain.proceed(chain.request())
        } else { // 如果没有网络，则返回缓存未过期一个月的数据
            val newRequest = chain.request().newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "only-if-cached, max-stale=$maxStaleSeconds")
                .build()

            return chain.proceed(newRequest)
        }

//        if (needCache) {
//            L.e("需要缓存")
//            val newRequest = chain.request().newBuilder()
//                .removeHeader("Pragma")
//                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStaleSeconds")
//                .build()
//            return chain.proceed(newRequest)
//        } else {
//            if (context == null) {
//                L.e("Context = null")
//                return chain.proceed(chain.request())
//            }
//
//            if (context.isAvailable()) {
//                L.e("不要缓存，有网络")
//                return chain.proceed(chain.request())
//            } else {
//                L.e("不要缓存，没网络")
//                throw IOException("没有网络连接")
//            }
//        }
    }
}

