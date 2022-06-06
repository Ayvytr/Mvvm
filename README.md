# ~~Android Mvvm：不再更新，~~ 请移步[Ayvytr/Flow](https://github.com/Ayvytr/Flow)



#  [![](https://img.shields.io/badge/jCenter-0.1.4-red.svg)](https://bintray.com/ayvytr/maven/mvvm-androidx/_latestVersion)

Android Kotlin Mvvm框架，使用了ViewModel, LiveData,  协程Coroutine，为简化开发而生。



搭配如下框架使用，效果更佳

```
implementation "com.ayvytr:network:2.3.0"
implementation "com.ayvytr:ktx:2.5.0"
```



## Import



android

​	implementation 'com.ayvytr:mvvm:0.1.4'



androidx

​	implementation 'com.ayvytr:mvvm-androidx:0.1.4'



## ChangeLog

* 0.1.4 
  1. 删除BaseViewModel.mNetworkExceptionHandler，.launchLoading
  2. 增加BaseViewModel.jobMap，用以方便取消未结束的Job
  3. 修改WrapperObserver，增加WrapperListObserver专门支持List和分页相关参数

* 0.1.3 增加WrapperObserver.onSucceed ResponseWrapper参数

* 0.1.2 修改launchLoading，launchWrapper方法返回Job

* 0.1.1 修改WrapperObserver为abstract class，并提供onError默认实现

* 0.1.0 框架第一版



## 使用




## 参考资料

[Android中用Kotlin Coroutine(协程)和Retrofit进行网络请求和取消请求](https://blog.csdn.net/huyongl1989/article/details/89456753)

[Retrofit 2.6.0 ! 更快捷的协程体验 ！](https://blog.csdn.net/sunluyao_/article/details/92799767)

[使用协程进行 UI 编程指南](https://github.com/hltj/kotlinx.coroutines-cn/blob/master/ui/coroutines-guide-ui.md)







