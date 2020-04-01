# Android Coroutines Library 

一系列便于组件化开发的封装库，从ModularizationComponent合并了base-adapter, network，放弃了以前的MVP和rxlifecycle封装库。以base-coroutine-activity库为主的项目。



base-coroutine-activity：BaseViewModel和BaseCoroutineActivity组合的项目，BaseViewModel默认提供了mLoadingLiveData和mResponseLiveData，分别专职接收loading显示/隐藏，网络异常响应。 [![](https://img.shields.io/badge/jCenter-0.3.1-red.svg)](https://bintray.com/ayvytr/maven/base-coroutine-activity/_latestVersion)

network：网络封装库，2.1.0起基于OKhttp 4.4和Retrofit 2.8.1。 [![](https://img.shields.io/badge/jCenter-2.1.1-red.svg)](https://bintray.com/ayvytr/maven/network/_latestVersion)

base-adapter：RecyclerView Adapter封装库（目前暂未提供androidx版本，如有需要可留言）。[![](https://img.shields.io/badge/jCenter-1.4.1-red.svg)](https://bintray.com/ayvytr/maven/base-adapter/_latestVersion)





## Import

android和androidx通用

​	//2.1.0 支持OkHttp 4.x，后续直接以OkHttp 4.x为基础进行更新

​	implementation 'com.ayvytr:network:2.1.1'



android

​	implementation 'com.ayvytr:base-coroutine-activity:0.3.1'



​	//未提供androidx版本

​	implementation 'com.ayvytr:base-adapter:1.4.1'



androidx

​	implementation 'com.ayvytr:base-coroutine-activity-androidx:0.3.1'



## ChangeLog

### base-coroutine-activity-androidx
* 0.3.1
    1.修改BaseViewModel重复问题
* 0.3.0  
    1. 增加ViewModel支持和BaseViewModel
    2. 去除BaseCoroutineActivity，BaseCoroutineFragment不必要的协程支持。
    3. BaseCoroutineActivity，BaseCoroutineFragment改为泛型初始化BaseViewModel
* 0.2.1  放弃OnBackPressed接口，因为没有完备的管理回退栈机制，仅仅使用接口无法根本解决问题

### network

* 2.1.1  增加APIClient.throwable2ResponseMessage，作为全局的Throwable转ResponseMessage的网络异常转换函数
* 2.1.0  支持OkHttp 4.x，后续直接以OkHttp 4.x为基础进行更新



## Use

## network

```
//初始化，默认开启了OKhttp缓存，cache=null关闭
ApiClient.getInstance().init("https://gank.io/api/", cache = null)
//覆盖重写自定义全局网络异常转为ResponseMessage
ApiClient.throwable2ResponseMessage = {
ResponseMessage("自定义错误", throwable = it)
}

//获取api，第二个参数传入不同的base url，获得使用另一个base url的Api接口
private val api = ApiClient.getInstance().create(Api::class.java, other_url)

```



### base-coroutine-activity

```kotlin
//Activity: 最简单的实现：直接使用BaseViewModel，继承BaseCoroutineActivity，重写showLoading，自定义当前页面显示和隐藏loading的逻辑，简单加一个launch，显示loading，并延迟两秒隐藏loading。
class SecondActivity : BaseCoroutineActivity<BaseViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        getContext()
        setTitle("SecondActivity")
        launch{
            mViewModel.mLoadingLiveData.value = true
            delay(2000)
            mViewModel.mLoadingLiveData.value = false
        }
    }

    override fun showLoading(isShow: Boolean) {
        progressBar.show(isShow)
    }
}

```



```kotlin
//Activity: 带网络请求的例子，MainActivity，MainViewModel分别继承BaseViewModel，BaseCoroutineActivity，MainActivity传入泛型MainViewModel，重写getViewModelClass()，返回MainViewModel，自动初始化MainViewModel。
//自定义当前页面的showLoading, showMessage, 并在MainViewModel中调用父类封装的launchLoading进行网络请求，BaseViewModel自动发送显示/隐藏loading的通知，以及网络请求异常的通知。
class MainActivity : BaseCoroutineActivity<MainViewModel>() {


    override fun showLoading(isShow: Boolean) {
        pb.show(isShow)
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mViewModel.androidAndIosLiveData.observe(this, Observer {
            tv_value.text = it.toString()
            tv_error.text = null
        })

        btn_get_data.setOnClickListener {
            mViewModel.getAndroidAndIos()
        }

        mViewModel.getAndroidAndIos()
    }

    override fun showMessage(message: String) {
        super.showMessage(message)
        L.e("errorLiveData", message)
        tv_error.text = message
        pb.hide()
    }
}


class MainViewModel : BaseViewModel() {
    private val repository = MainRepository()
    val androidGankLiveData = MutableLiveData<BaseGank>()
    val iosGankLiveData = MutableLiveData<BaseGank>()
    val androidAndIosLiveData = MutableLiveData<List<Gank>>()

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

    fun getAndroidAndIos() {
        launchLoading {
            val android = async { repository.getAndroidGank() }.await()
            val ios = async { repository.getIosGank() }.await()
            val list = android.results!!.toMutableList()
            list.addAll(ios.results!!)
            androidAndIosLiveData.value = list
        }
    }
}


class MainRepository {
    private val api = ApiClient.getInstance().create(Api::class.java)

    suspend fun getAndroidGank(): BaseGank {
        return api.getAndroidGank()
    }

    suspend fun getIosGank(): BaseGank {
        return api.getIosGank()
    }
}



interface Api {
    @GET("data/iOS/2/1")
    suspend fun getIosGank(): BaseGank

    @GET("data/Android/2/1")
    suspend fun getAndroidGank(): BaseGank
}
```






## 参考资料

[Android中用Kotlin Coroutine(协程)和Retrofit进行网络请求和取消请求](https://blog.csdn.net/huyongl1989/article/details/89456753)

[Retrofit 2.6.0 ! 更快捷的协程体验 ！](https://blog.csdn.net/sunluyao_/article/details/92799767)

[使用协程进行 UI 编程指南](https://github.com/hltj/kotlinx.coroutines-cn/blob/master/ui/coroutines-guide-ui.md)



## 别忘了点个Star！O(∩_∩)O~



