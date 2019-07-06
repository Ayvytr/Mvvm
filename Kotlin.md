# Kotlin



## 基本类型

| Type   | Bit width |
| ------ | --------- |
| Double | 64        |
| Float  | 32        |
| Long   | 64        |
| Int    | 32        |
| Short  | 16        |
| Byte   | 8         |

### 字面常量

16进制：0x0F

2进制：0b0001

**不支持8进制**

float：0.1F

### 数字字面值中的下划线

val oneMillion = 1_000_000
val creditCardNumber = 1234_5678_9012_3456L
val socialSecurityNumber = 999_99_9999L
val hexBytes = 0xFF_EC_DE_5E
val bytes = 0b11010010_01101001_10010100_10010010



**在 Java 平台数字是物理存储为 JVM 的原生类型，除非我们需要一个可空的引用（如 `Int?`）或泛型。 后者情况下会把数字装箱**

[基础语法](https://www.kotlincn.net/docs/reference/basic-syntax.html)

[习惯用法](https://www.kotlincn.net/docs/reference/idioms.html)

[编码规范](https://www.kotlincn.net/docs/reference/coding-conventions.html)



基础



类与对象



函数与 Lambda 表达式



集合



多平台程序设计



其他



核心库



参考



Java 互操作



JavaScript



原生



协程



工具



演进



常见问题

- [完整 Kotlin 参考（PDF）](https://www.kotlincn.net/docs/kotlin-docs.pdf)
- [完整 Kotlin 参考（字大PDF）](https://www.gitbook.com/download/pdf/book/hltj/kotlin-reference-chinese)
- [完整 Kotlin 参考（ePUB）](https://www.gitbook.com/download/epub/book/hltj/kotlin-reference-chinese)
- [完整 Kotlin 参考（Mobi）](https://www.gitbook.com/download/mobi/book/hltj/kotlin-reference-chinese)

 

[编辑本页](https://github.com/hltj/kotlin-web-site-cn/edit/master/pages/docs/reference/basic-types.md)

# 基本类型

在 Kotlin 中，所有东西都是对象，在这个意义上讲我们可以在任何变量上调用成员函数与属性。 一些类型可以有特殊的内部表示——例如，数字、字符以及布尔值可以在运行时表示为原生类型值，但是对于用户来说，它们看起来就像普通的类。 在本节中，我们会描述 Kotlin 中使用的基本类型：数字、字符、布尔值、数组与字符串。

## 数字

Kotlin 处理数字在某种程度上接近 Java，但是并不完全相同。例如，对于数字没有隐式拓宽转换（如 Java 中 `int`可以隐式转换为`long`——译者注)，另外有些情况的字面值略有不同。

Kotlin 提供了如下的内置类型来表示数字（与 Java 很相近）：

| Type   | Bit width |
| :----- | :-------- |
| Double | 64        |
| Float  | 32        |
| Long   | 64        |
| Int    | 32        |
| Short  | 16        |
| Byte   | 8         |

注意在 Kotlin 中字符不是数字

### 字面常量

数值常量字面值有以下几种:

- 十进制:

   

  ```
  123
  ```

  - Long 类型用大写 `L` 标记: `123L`

- 十六进制: `0x0F`

- 二进制: `0b00001011`

注意: 不支持八进制

Kotlin 同样支持浮点数的常规表示方法:

- 默认 double：`123.5`、`123.5e10`
- Float 用 `f` 或者 `F` 标记: `123.5f`



### 每个数字类型支持如下的转换:

- `toByte(): Byte`
- `toShort(): Short`
- `toInt(): Int`
- `toLong(): Long`
- `toFloat(): Float`
- `toDouble(): Double`
- `toChar(): Char`



### 运算

Kotlin支持数字运算的标准集，运算被定义为相应的类成员（但编译器会将函数调用优化为相应的指令）。 参见[运算符重载](https://www.kotlincn.net/docs/reference/operator-overloading.html)。

对于位运算，没有特殊字符来表示，而只可用中缀方式调用命名函数，例如:



```
val x = (1 shl 2) and 0x000FF000
```



这是完整的位运算列表（只用于 `Int` 与 `Long`）：

- `shl(bits)` – 有符号左移 (Java 的 `<<`)
- `shr(bits)` – 有符号右移 (Java 的 `>>`)
- `ushr(bits)` – 无符号右移 (Java 的 `>>>`)
- `and(bits)` – 位与
- `or(bits)` – 位或
- `xor(bits)` – 位异或
- `inv()` – 位非



### 字符

字符用 `Char` 类型表示。它们不能直接当作数字



```
fun check(c: Char) {
    if (c == 1) { // 错误：类型不兼容
        // ……
    }
}
```



字符字面值用单引号括起来: `'1'`。 特殊字符可以用反斜杠转义。 支持这几个转义序列：`\t`、 `\b`、`\n`、`\r`、`\'`、`\"`、`\\` 与 `\$`。 编码其他字符要用 Unicode 转义序列语法：`'\uFF00'`。

我们可以显式把字符转换为 `Int` 数字：

```
fun decimalDigitValue(c: Char): Int {
    if (c !in '0'..'9')
        throw IllegalArgumentException("Out of range")
    return c.toInt() - '0'.toInt() // 显式转换为数字
}
```



当需要可空引用时，像数字、字符会被装箱。装箱操作不会保留同一性。

### 数组

注意: 与 Java 不同的是，Kotlin 中数组是不型变的（invariant）。这意味着 Kotlin 不让我们把 `Array<String>` 赋值给 `Array<Any>`，以防止可能的运行时失败（但是你可以使用 `Array<out Any>`, 参见[类型投影](https://www.kotlincn.net/docs/reference/generics.html#类型投影)）。

Kotlin 也有无装箱开销的专门的类来表示原生类型数组: `ByteArray`、 `ShortArray`、`IntArray` 等等。这些类与 `Array` 并没有继承关系，但是它们有同样的方法属性集。它们也都有相应的工厂方法:



```
val x: IntArray = intArrayOf(1, 2, 3)
x[0] = x[1] + x[2]
```



### 字符串

*原始字符串* 使用三个引号（`"""`）分界符括起来，内部没有转义并且可以包含换行以及任何其他字符:

```
val text = """
    for (c in "foo")
        print(c)
"""
```



## 类与继承

主构造函数不能包含任何的代码。初始化的代码可以放到以 *init* 关键字作为前缀的**初始化块（initializer blocks）**中。

与普通属性一样，主构造函数中声明的属性可以是可变的（*var*）或只读的（*val*）。

如果构造函数有注解或可见性修饰符，这个 *constructor* 关键字是必需的，并且这些修饰符在它前面：

```
class Customer public @Inject constructor(name: String) { …… }
```



### 继承

可继承的父类必须是open class，可继承的方法必须是open fun，可覆盖的属性必须是open val，子类可使用override覆盖。

**可以用一个 `var` 属性覆盖一个 `val` 属性，但反之则不行。这是允许的，因为一个 `val` 属性本质上声明了一个 getter 方法，而将其覆盖为 `var` 只是在子类中额外声明一个 setter 方法。**

如果派生类有一个主构造函数，其基类型可以（并且必须） 用基类的主构造函数参数就地初始化。

如果派生类没有主构造函数，那么每个次构造函数必须使用 *super* 关键字初始化其基类型，或委托给另一个构造函数做到这一点。 注意，在这种情况下，不同的次构造函数可以调用基类型的不同的构造函数：



```
class MyView : View {
    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
}
```



### 派生类初始化顺序

在构造派生类的新实例的过程中，第一步完成其基类的初始化（在之前只有对基类构造函数参数的求值），因此发生在派生类的初始化逻辑运行之前。

```
open class Base(val name: String) {

    init { println("Initializing Base") }

    open val size: Int = 
        name.length.also { println("Initializing size in Base: $it") }
}

class Derived(
    name: String,
    val lastName: String
) : Base(name.capitalize().also { println("Argument for Base: $it") }) {

    init { println("Initializing Derived") }

    override val size: Int =
        (super.size + lastName.length).also { println("Initializing size in Derived: $it") }
}
```



这意味着，基类构造函数执行时，派生类中声明或覆盖的属性都还没有初始化。如果在基类初始化逻辑中（直接或通过另一个覆盖的 *open* 成员的实现间接）使用了任何一个这种属性，那么都可能导致不正确的行为或运行时故障。设计一个基类时，应该避免在构造函数、属性初始化器以及 *init* 块中使用 *open* 成员。

**可以使用super调用父类的方法和属性。在一个内部类中访问外部类的超类，可以通过由外部类名限定的 *super* 关键字来实现：`super@Outer`：**

### 延迟初始化属性与变量  

`lateinit`修饰符只能用于在类体中的属性（不是在主构造函数中声明的 `var` 属性，并且仅当该属性没有自定义 getter 或 setter 时），而自 Kotlin 1.2 起，也用于顶层属性与局部变量。该属性或变量必须为非空类型，并且不能是原生类型。在初始化前访问一个 `lateinit` 属性会抛出一个特定异常，该异常明确标识该属性被访问及它没有初始化的事实。

### 检测一个 lateinit var 是否已初始化（自 1.2 起）

要检测一个 `lateinit var` 是否已经初始化过，请在[该属性的引用](https://www.kotlincn.net/docs/reference/reflection.html#属性引用)上使用 `.isInitialized`：

```
if (foo::bar.isInitialized) {
    println(foo.bar)
}
```

此检测仅对可词法级访问的属性可用，即声明位于同一个类型内、位于其中一个外围类型中或者位于相同文件的顶层的属性。



### 数据类

我们经常创建一些只保存数据的类。 在这些类中，一些标准函数往往是从数据机械推导而来的。在 Kotlin 中，这叫做 *数据类* 并标记为 `data`：



```
data class User(val name: String, val age: Int)
```



编译器自动从主构造函数中声明的所有属性导出以下成员：

- `equals()`/`hashCode()` 对；
- `toString()` 格式是 `"User(name=John, age=42)"`；
- [`componentN()` 函数](https://www.kotlincn.net/docs/reference/multi-declarations.html) 按声明顺序对应于所有属性；
- `copy()` 函数（见下文）。

为了确保生成的代码的一致性以及有意义的行为，数据类必须满足以下要求：

- 主构造函数需要至少有一个参数；
- 主构造函数的所有参数需要标记为 `val` 或 `var`；
- 数据类不能是抽象、开放、密封或者内部的；
- （在1.1之前）数据类只能实现接口。

此外，成员生成遵循关于成员继承的这些规则：

- 如果在数据类体中有显式实现 `equals()`、 `hashCode()` 或者 `toString()`，或者这些函数在父类中有*final* 实现，那么不会生成这些函数，而会使用现有函数；
- 如果超类型具有 *open* 的 `componentN()` 函数并且返回兼容的类型， 那么会为数据类生成相应的函数，并覆盖超类的实现。如果超类型的这些函数由于签名不兼容或者是 final 而导致无法覆盖，那么会报错；
- 从一个已具 `copy(……)` 函数且签名匹配的类型派生一个数据类在 Kotlin 1.2 中已弃用，并且在 Kotlin 1.3 中已禁用。
- 不允许为 `componentN()` 以及 `copy()` 函数提供显式实现。

自 1.1 起，数据类可以扩展其他类（示例请参见[密封类](https://www.kotlincn.net/docs/reference/sealed-classes.html)）。

在 JVM 中，如果生成的类需要含有一个无参的构造函数，则所有的属性必须指定默认值。 （参见[构造函数](https://www.kotlincn.net/docs/reference/classes.html#构造函数)）。



```
data class User(val name: String = "", val age: Int = 0)
```



请注意，对于那些自动生成的函数，编译器只使用在主构造函数内部定义的属性。如需在生成的实现中排出一个属性，请将其声明在类体中：



```
data class Person(val name: String) {
    var age: Int = 0
}
```



在 `toString()`、 `equals()`、 `hashCode()` 以及 `copy()` 的实现中只会用到 `name` 属性，并且只有一个 component 函数 `component1()`。虽然两个 `Person` 对象可以有不同的年龄，但它们会视为相等。



```
val person1 = Person("John")
val person2 = Person("John")
person1.age = 10
person2.age = 20
```



在很多情况下，我们需要复制一个对象改变它的一些属性，但其余部分保持不变。 `copy()` 函数就是为此而生成。对于上文的 `User` 类，其实现会类似下面这样：

```
fun copy(name: String = this.name, age: Int = this.age) = User(name, age)
```

这让我们可以写：

```
val jack = User(name = "Jack", age = 1)
val olderJack = jack.copy(age = 2)
```



为数据类生成的 *Component 函数* 使它们可在[解构声明](https://www.kotlincn.net/docs/reference/multi-declarations.html)中使用：

```
val jane = User("Jane", 35)
val (name, age) = jane
println("$name, $age years of age") // 输出 "Jane, 35 years of age"
```



## 泛型

### 类型投影

#### 使用处型变：类型投影

将类型参数 T 声明为 *out* 非常方便，并且能避免使用处子类型化的麻烦，但是有些类实际上**不能**限制为只返回 `T`！ 一个很好的例子是 Array：



```
class Array<T>(val size: Int) {
    fun get(index: Int): T { …… }
    fun set(index: Int, value: T) { …… }
}
```



该类在 `T` 上既不能是协变的也不能是逆变的。这造成了一些不灵活性。考虑下述函数：

```
fun copy(from: Array<Any>, to: Array<Any>) {
    assert(from.size == to.size)
    for (i in from.indices)
        to[i] = from[i]
}
```



这个函数应该将项目从一个数组复制到另一个数组。让我们尝试在实践中应用它：

```
val ints: Array<Int> = arrayOf(1, 2, 3)
val any = Array<Any>(3) { "" } 
copy(ints, any)
//   ^ 其类型为 Array<Int> 但此处期望 Array<Any>
```



这里我们遇到同样熟悉的问题：`Array <T>` 在 `T` 上是**不型变的**，因此 `Array <Int>` 和 `Array <Any>` 都不是另一个的子类型。为什么？ 再次重复，因为 copy **可能**做坏事，也就是说，例如它可能尝试**写**一个 String 到 `from`， 并且如果我们实际上传递一个 `Int` 的数组，一段时间后将会抛出一个 `ClassCastException` 异常。

那么，我们唯一要确保的是 `copy()` 不会做任何坏事。我们想阻止它**写**到 `from`，我们可以：



```
fun copy(from: Array<out Any>, to: Array<Any>) { …… }
```



这里发生的事情称为**类型投影**：我们说`from`不仅仅是一个数组，而是一个受限制的（**投影的**）数组：我们只可以调用返回类型为类型参数 `T` 的方法，如上，这意味着我们只能调用 `get()`。这就是我们的**使用处型变**的用法，并且是对应于 Java 的 `Array<? extends Object>`、 但使用更简单些的方式。

你也可以使用 **in** 投影一个类型：

```
fun fill(dest: Array<in String>, value: String) { …… }
```



**`Array<in String>` 对应于 Java 的 `Array<? super String>`，也就是说，你可以传递一个 `CharSequence` 数组或一个 `Object` 数组给 `fill()` 函数。**



## 委托

[委托模式](https://zh.wikipedia.org/wiki/委托模式)已经证明是实现继承的一个很好的替代方式， 而 Kotlin 可以零样板代码地原生支持它。 

## 委托属性



## 协程

GlobalScope.launch 启动一个全局的没有和任何Job绑定的协程

delay() 挂起函数，非阻塞。（Thread.sleep() 阻塞）

runBlocking{delay(2000L)} 协程使用runBlocking阻塞线程，效果和 Thread.sleep(2000) 相同

```
import kotlinx.coroutines.*

fun main() {
    GlobalScope.launch { // 在后台启动一个新的协程并继续
        delay(1000L)
        println("World!")
    }
    println("Hello,") // 主线程中的代码会立即执行
    runBlocking {     // 但是这个表达式阻塞了主线程
        delay(2000L)  // ……我们延迟 2 秒来保证 JVM 的存活
    } 
}
```

使用runBlocking包装main函数的执行

```
import kotlinx.coroutines.*

fun main() = runBlocking<Unit> { // 开始执行主协程
    GlobalScope.launch { // 在后台启动一个新的协程并继续
        delay(1000L)
        println("World!")
    }
    println("Hello,") // 主协程在这里会立即执行
    delay(2000L)      // 延迟 2 秒来保证 JVM 存活
}
```

Job.join() 等待子协程执行结束

```
fun main() = runBlocking {    
	val job = GlobalScope.launch {        
		delay(1000)        
		println("World!")    
    }    
    println("Hello, ")    
    job.join()
}
```

​	**协程的实际使用还有一些需要改进的地方。当我们使用`GlobalScope.launch`时，我们会创建一个顶层协程，虽然它很轻量，但是运行时仍会小号一些内存资源。如果忘记保持对新启动的协程的引用，它还会继续运行。如果协程中的代码挂起了会怎么样（例如，我们错误地延迟了太长时间），如果我们启动了太多的协程并导致内存不足会怎么样？必须手动保持对已启动协程的引用并join之很容易出错**。

​	**更好的解决办法是可以在代码中使用结构化并发。我们可以在执行操作所在的指定作用域内启动协程，而不是像通常使用线程（线程总是全局的）那样在GlobalScope中启动**。

**在我们的示例中，我们使用 [runBlocking](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/run-blocking.html) 协程构建器将 `main` 函数转换为协程。 包括 `runBlocking` 在内的每个协程构建器都将 [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) 的实例添加到其代码块所在的作用域中。 我们可以在这个作用域中启动协程而无需显式 `join` 之，因为外部协程（示例中的 `runBlocking`）直到在其作用域中启动的所有协程都执行完毕后才会结束。因此，可以将我们的示例简化为：**

```
import kotlinx.coroutines.*

fun main() = runBlocking { // this: CoroutineScope
    launch { // 在 runBlocking 作用域中启动一个新协程
        delay(1000L)
        println("World!")
    }
    println("Hello,")
}
```



### 作用域构建器

除了由不同的构建器提供协程作用域之外，还可以使用 [coroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) 构建器声明自己的作用域。它会创建一个协程作用域并且在所有已启动子协程执行完毕之前不会结束。[runBlocking](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/run-blocking.html) 与 [coroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) 的主要区别在于后者在等待所有子协程执行完毕时不会阻塞当前线程。

```
import kotlinx.coroutines.*

fun main() = runBlocking { // this: CoroutineScope
    launch { 
        delay(200L)
        println("Task from runBlocking")
    }
    
    coroutineScope { // 创建一个协程作用域
        launch {
            delay(500L) 
            println("Task from nested launch")
        }
    
        delay(100L)
        println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
    }
    
    println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
}
```

### 挂起函数：suspend fun



```
import kotlinx.coroutines.*

fun main() = runBlocking {
    launch { doWorld() }
    println("Hello,")
}

// 这是你的第一个挂起函数
suspend fun doWorld() {
    delay(1000L)
    println("World!")
}
```

### 协程很轻量

运行以下代码：

```
import kotlinx.coroutines.*

fun main() = runBlocking {
    repeat(100_000) { // 启动大量的协程
        launch {
            delay(1000L)
            print(".")
        }
    }
}
```

对比线程，协程执行速度非常快。线程实现几秒钟输出还不到100000个，很可能会产生内存不足的错误

```
fun main() = runBlocking {
    repeat(1000000) {
        //        launch {
//            delay(1000)
//            print(".")
//        }
        Thread(Runnable {
            Thread.sleep(1000)
            print(".")
        }).start()
    }
}
```



### 全局协程像守护线程

以下代码在 [GlobalScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-global-scope/index.html) 中启动了一个长期运行的协程，该协程每秒输出“I'm sleeping”两次，之后在主函数中延迟一段时间后返回。

```
import kotlinx.coroutines.*

fun main() = runBlocking {
    GlobalScope.launch {
        repeat(1000) { i ->
                println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // 在延迟后退出
}

//输出结果如下，输出了3行后终止
I'm sleeping 0 ...
I'm sleeping 1 ...
I'm sleeping 2 ...
```

在 [GlobalScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-global-scope/index.html) 中启动的活动协程并不会使进程保活。它们就像守护线程。



### 取消与超时

Job.cancel() 取消协程

Job.join() 等待协程执行结束

Job.cancelAndJoin() 包含cancel和join的操作

协程的取消是 *协作* 的。一段协程代码必须协作才能被取消。 所有 `kotlinx.coroutines` 中的挂起函数都是 *可被取消的* 。它们检查协程的取消， 并在取消时抛出 [CancellationException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-cancellation-exception/index.html)。 然而，如果协程正在执行计算任务，并且没有检查取消的话，那么它是不能被取消的，就如如下示例代码所示：

```
import kotlinx.coroutines.*

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) { // 一个执行计算的循环，只是为了占用 CPU
            // 每秒打印消息两次
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // 等待一段时间
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // 取消一个作业并且等待它结束
    println("main: Now I can quit.")
}
```

运行示例代码，并且我们可以看到它连续打印出了“I'm sleeping”，甚至在调用取消后， 作业仍然执行了五次循环迭代并运行到了它结束为止。



### 使计算代码可取消

我们有两种方法来使执行计算的代码可以被取消。第一种方法是定期调用挂起函数来检查取消。对于这种目的 [yield](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/yield.html) 是一个好的选择。 另一种方法是显式的检查取消状态。让我们试试第二种方法。

将前一个示例中的 `while (i < 5)` 替换为 `while (isActive)` 并重新运行它。

```
import kotlinx.coroutines.*

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) { // 可以被取消的计算循环
            // 每秒打印消息两次
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // 等待一段时间
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // 取消该作业并等待它结束
    println("main: Now I can quit.")
}
```



[协程指南](https://www.kotlincn.net/docs/reference/coroutines/coroutines-guide.html)

[基础](https://www.kotlincn.net/docs/reference/coroutines/basics.html)



取消与超时

[通道](https://www.kotlincn.net/docs/reference/coroutines/channels.html)

[组合挂起函数](https://www.kotlincn.net/docs/reference/coroutines/composing-suspending-functions.html)

[协程上下文与调度器](https://www.kotlincn.net/docs/reference/coroutines/coroutine-context-and-dispatchers.html)

[异常处理](https://www.kotlincn.net/docs/reference/coroutines/exception-handling.html)

[Select 表达式](https://www.kotlincn.net/docs/reference/coroutines/select-expression.html)

[共享的可变状态与并发](https://www.kotlincn.net/docs/reference/coroutines/shared-mutable-state-and-concurrency.html)



工具



演进



常见问题

- [完整 Kotlin 参考（PDF）](https://www.kotlincn.net/docs/kotlin-docs.pdf)
- [完整 Kotlin 参考（字大PDF）](https://www.gitbook.com/download/pdf/book/hltj/kotlin-reference-chinese)
- [完整 Kotlin 参考（ePUB）](https://www.gitbook.com/download/epub/book/hltj/kotlin-reference-chinese)
- [完整 Kotlin 参考（Mobi）](https://www.gitbook.com/download/mobi/book/hltj/kotlin-reference-chinese)

 

[编辑本页](https://github.com/hltj/kotlinx.coroutines-cn/edit/master/docs/cancellation-and-timeouts.md)

**目录**

- 取消与超时
  - [取消协程的执行](https://www.kotlincn.net/docs/reference/coroutines/cancellation-and-timeouts.html#取消协程的执行)
  - [取消是协作的](https://www.kotlincn.net/docs/reference/coroutines/cancellation-and-timeouts.html#取消是协作的)
  - [使计算代码可取消](https://www.kotlincn.net/docs/reference/coroutines/cancellation-and-timeouts.html#使计算代码可取消)
  - [在 `finally` 中释放资源](https://www.kotlincn.net/docs/reference/coroutines/cancellation-and-timeouts.html#在-finally-中释放资源)
  - [运行不能取消的代码块](https://www.kotlincn.net/docs/reference/coroutines/cancellation-and-timeouts.html#运行不能取消的代码块)
  - [超时](https://www.kotlincn.net/docs/reference/coroutines/cancellation-and-timeouts.html#超时)

## 取消与超时

这一部分包含了协程的取消与超时。

### 取消协程的执行

在一个长时间运行的应用程序中，你也许需要对你的后台协程进行细粒度的控制。 比如说，一个用户也许关闭了一个启动了协程的界面，那么现在协程的执行结果已经不再被需要了，这时，它应该是可以被取消的。 该 [launch](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html) 函数返回了一个可以被用来取消运行中的协程的 [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html)：





```
val job = launch {
    repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
        delay(500L)
    }
}
delay(1300L) // 延迟一段时间
println("main: I'm tired of waiting!")
job.cancel() // 取消该作业
job.join() // 等待作业执行结束
println("main: Now I can quit.")
```



程序执行后的输出如下：

```
job: I'm sleeping 0 ...
job: I'm sleeping 1 ...
job: I'm sleeping 2 ...
main: I'm tired of waiting!
main: Now I can quit.
```

一旦 main 函数调用了 `job.cancel`，我们在其它的协程中就看不到任何输出，因为它被取消了。 这里也有一个可以使 [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) 挂起的函数 [cancelAndJoin](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/cancel-and-join.html) 它合并了对 [cancel](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/cancel.html) 以及 [join](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/join.html) 的调用。

### 取消是协作的

协程的取消是 *协作* 的。一段协程代码必须协作才能被取消。 所有 `kotlinx.coroutines` 中的挂起函数都是 *可被取消的* 。它们检查协程的取消， 并在取消时抛出 [CancellationException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-cancellation-exception/index.html)。 然而，如果协程正在执行计算任务，并且没有检查取消的话，那么它是不能被取消的，就如如下示例代码所示：

```
import kotlinx.coroutines.*

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) { // 一个执行计算的循环，只是为了占用 CPU
            // 每秒打印消息两次
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // 等待一段时间
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // 取消一个作业并且等待它结束
    println("main: Now I can quit.")
}
```



运行示例代码，并且我们可以看到它连续打印出了“I'm sleeping”，甚至在调用取消后， 作业仍然执行了五次循环迭代并运行到了它结束为止。

### 使计算代码可取消

我们有两种方法来使执行计算的代码可以被取消。第一种方法是定期调用挂起函数来检查取消。对于这种目的 [yield](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/yield.html) 是一个好的选择。 另一种方法是显式的检查取消状态。让我们试试第二种方法。

将前一个示例中的 `while (i < 5)` 替换为 `while (isActive)` 并重新运行它。你可以看到，现在循环被取消了。



```
import kotlinx.coroutines.*

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) { // 可以被取消的计算循环
            // 每秒打印消息两次
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // 等待一段时间
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // 取消该作业并等待它结束
    println("main: Now I can quit.")
}
```





[isActive](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/is-active.html) 是一个可以被使用在 [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) 中的扩展属性。

### 在 `finally` 中释放资源

我们通常使用如下的方法处理在被取消时抛出 [CancellationException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-cancellation-exception/index.html) 的可被取消的挂起函数。比如说，`try {……} finally {……}` 表达式以及 Kotlin 的 `use` 函数一般在协程被取消的时候执行它们的终结动作：



```
val job = launch {
    try {
        repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
            delay(500L)
        }
    } finally {
        println("job: I'm running finally")
    }
}
delay(1300L) // 延迟一段时间
println("main: I'm tired of waiting!")
job.cancelAndJoin() // 取消该作业并且等待它结束
println("main: Now I can quit.")
```



### 运行不能取消的代码块

在前一个例子中任何尝试在 `finally` 块中调用挂起函数的行为都会抛出 [CancellationException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-cancellation-exception/index.html)，因为这里持续运行的代码是可以被取消的。通常，这并不是一个问题，所有良好的关闭操作（关闭一个文件、取消一个作业、或是关闭任何一种通信通道）通常都是非阻塞的，并且不会调用任何挂起函数。然而，在真实的案例中，当你需要挂起一个被取消的协程，你可以将相应的代码包装在 `withContext(NonCancellable) {……}`中，并使用 [withContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html) 函数以及 [NonCancellable](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-non-cancellable.html) 上下文，见如下示例所示：

```
val job = launch {
    try {
        repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
            delay(500L)
        }
    } finally {
        withContext(NonCancellable) {
            println("job: I'm running finally")
            delay(1000L)
            println("job: And I've just delayed for 1 sec because I'm non-cancellable")
        }
    }
}
delay(1300L) // 延迟一段时间
println("main: I'm tired of waiting!")
job.cancelAndJoin() // 取消该作业并等待它结束
println("main: Now I can quit.")
```

### 超时

使用 [withTimeout](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-timeout.html) 函数在超时后抛出TimeoutCancellationException。[它是 [CancellationException](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-cancellation-exception/index.html) 的子类。 我们之前没有在控制台上看到堆栈跟踪信息的打印。这是因为在被取消的协程中 `CancellationException` 被认为是协程执行结束的正常原因。 

由于取消只是一个例外，所有的资源都使用常用的方法来关闭。 如果你需要做一些各类使用超时的特别的额外操作，可以使用类似 [withTimeout](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-timeout.html) 的 [withTimeoutOrNull](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-timeout-or-null.html) 函数，并把这些会超时的代码包装在 `try {...} catch (e: TimeoutCancellationException) {...}` 代码块中，而 [withTimeoutOrNull](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-timeout-or-null.html) 通过返回 `null`来进行超时操作，从而替代抛出一个异常：

```
val result = withTimeoutOrNull(1300L) {
    repeat(1000) { i ->
            println("I'm sleeping $i ...")
        delay(500L)
    }
    "Done" // 在它运行得到结果之前取消它
}
println("Result is $result")
```






