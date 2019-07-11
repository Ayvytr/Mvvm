# Kotlin协程总结

## 协程构建函数

| 方法        | 返回值         | 功能                                                         |
| ----------- | -------------- | ------------------------------------------------------------ |
| launch      | Job            | 启动一个没有返回值的协程                                     |
| async       | Deferred       | 创建一个协程并将未来结果作为Deferred的实现返回               |
| produce     | ReceiveChannel | 启动一个新的协程，产生一个数据流，发送到Channel，并将协程的引用作为ReceiveChannel返回，可用于接收该协程生成的元素 |
| runBlocking | T              | 运行一个新协程，中断当前线程，直到运行完成。This function should not be used from a coroutine. It is designed to bridge regular blocking code to libraries that are written in suspending style, to be used in `main` functions and in tests. |



## Dispatchers 协程调度器

| 属性       | 功能                                                         |
| ---------- | ------------------------------------------------------------ |
| DEFAULT    | launch, async等协程创建器的默认调度器                        |
| IO         | The [CoroutineDispatcher](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html) that is designed for offloading blocking IO tasks to a shared pool of threads. |
| Main       | 仅限于使用UI对象操作的主线程。通常这种调度程序是单线程的     |
| Unconfined | 不强制指定线程                                               |



NonCancellable: 不可取消的作业。专为withContext设计的执行不可取消的代码

```
withContext(NonCancellable) {
	//这里代码不可以被取消
}
```

[CoroutineExceptionHandler](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-exception-handler/index.html): 协程上下文中的可选元素，用于处理未捕获的异常。 通常，未捕获的异常只能来自使用[启动](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html)构建器创建的协程。使用[async](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html)创建的协程始终捕获其所有异常，并在生成的[Deferred](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html)对象中表示它们。

默认情况下，如果未安装处理程序，则以下列方式处理未捕获的异常：

- 如果异常是[CancellationException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-cancellation-exception/index.html)则忽略它（因为这是取消正在运行的协同程序的假设机制）
- 除此以外：
  - 如果上下文中有[Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html)，则调用[Job.cancel](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/cancel.html) ;
  - 否则，通过[ServiceLoader](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-exception-handler/index.html#)找到[CoroutineExceptionHandler的](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-exception-handler/index.md)所有实例
  - 并且调用当前线程的[Thread.uncaughtExceptionHandler](http://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#uncaughtExceptionHandler)。



## 实现协程同步的元素

Mutex	lock	互斥锁

[Channel](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-channel/index.html)  Channel是一个非阻塞原语，用于使用[SendChannel的](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/index.html)发送方和使用[ReceiveChannel的](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/index.html)接收方之间的通信。从概念上讲，一个通道类似于[BlockingQueue](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.html)，但是它具有挂起操作而不是阻塞操作，它可以被关闭。

## 顶级扩展方法

| 方法                                                         | 功能                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| delay                                                        | 非阻塞睡眠                                                   |
| yield                                                        | 在单线程调度器中产生线程                                     |
| withContext                                                  | 切换到不同的上下文                                           |
| [withTimeout](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-timeout.html) | 设置指定的超时在协程内运行给定的代码块，如果超时，抛出TimeoutCancellationException. The code that is executing inside the [block](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-timeout.html#kotlinx.coroutines$withTimeout(kotlin.Long, kotlin.SuspendFunction1((kotlinx.coroutines.CoroutineScope, kotlinx.coroutines.withTimeout.T)))/block) is cancelled on timeout and the active or next invocation of the cancellable suspending function inside the block throws a [TimeoutCancellationException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-timeout-cancellation-exception.html). |
| withTimeoutOrNull                                            | 设置指定的超时在协程内运行给定的代码块，如果超时，返回null   |
| awaitAll                                                     | https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/await-all.html |
| joinAll                                                      | https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/join-all.html |



## [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)

| 方法                                                         | 功能                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| interface [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)(source) | 定义新协程的范围。每个协程构建器都是[CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.md)的扩展，并继承其[coroutineContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/coroutine-context.html) 以自动传播上下文元素和取消。 |
| CoroutineScope.isAlive                                       | 当前作业处于活动状态时返回true（未完成且未取消）             |
| actor                                                        |                                                              |
| [async](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html) | 创建一个协同程序并将其未来结果作为[Deferred](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html)的实现返回。 |
| broadcast                                                    |                                                              |
| cancel                                                       | 取消此协程和所有子协程，可指定取消原因                       |
| ensureAlive                                                  |                                                              |
| launch                                                       | 在不阻塞当前线程的情况下启动新的协同程序，并将协程的引用作为[Job返回](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html)。取消生成的作业时，协程将被[取消](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/cancel.html)。 |
| newCoroutineContext                                          |                                                              |
| plus                                                         | 将指定的协程上下文添加到此作用域，使用相应的键覆盖当前作用域上下文中的现有元素。 |
| produce                                                      | 通过将新的协同程序发送到通道并将协程的引用作为[ReceiveChannel](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/index.html)返回，从而生成新的协同程序以生成值流。该结果对象可用于[接收](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/receive.html)由该协程生成的元素。 |











