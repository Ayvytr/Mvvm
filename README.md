# Android Coroutines Library  [![](https://img.shields.io/badge/jCenter-0.2.1-red.svg)](https://bintray.com/ayvytr/maven/base-coroutine-activity/_latestVersion)


BaseCoroutineActivity and BaseCoroutineFragment for Android.

## Import

android

​	implementation 'com.ayvytr:base-coroutine-activity:0.2.1'

androidx

​	implementation 'com.ayvytr:base-coroutine-activity-androidx:0.2.1'

## ChangeLog
* 0.2.1
    放弃OnBackPressed接口，因为没有完备的管理回退栈机制，仅仅使用接口无法根本解决问题


### Base adapter

    implementation 'com.ayvytr:base-adapter:1.4.2'

### Network

    //2.1.0 支持OkHttp 4.x，后续直接以OkHttp 4.x为基础进行更新
    implementation 'com.ayvytr:network:2.1.0'
    //2.0.0 支持OkHttp 3.x
    implementation 'com.ayvytr:network:2.0.0'


## Use

```kotlin
//Activity:
class YourActivity : BaseCoroutineActivity() {
	override fun initData(savedInstanceState: Bundle?) {
		   launchWithLoading {
            	//Your network request. You don't need to control loading.
            }
            
            //Or
            launch {
            	//Your network request. You need to control loading.
            }
        }
	}
}

```



```kotlin
//Fragment:
class YourFragment : BaseCoroutineFragment() {
	override fun initData(savedInstanceState: Bundle?) {
		   launchWithLoading {
            	//Your network request. You don't need to control loading.
            }
            
            //Or
            launch {
            	//Your network request. You need to control loading.
            }
        }
	}
}

```






## 参考资料

[Android中用Kotlin Coroutine(协程)和Retrofit进行网络请求和取消请求](https://blog.csdn.net/huyongl1989/article/details/89456753)

[Retrofit 2.6.0 ! 更快捷的协程体验 ！](https://blog.csdn.net/sunluyao_/article/details/92799767)

[使用协程进行 UI 编程指南](https://github.com/hltj/kotlinx.coroutines-cn/blob/master/ui/coroutines-guide-ui.md)



