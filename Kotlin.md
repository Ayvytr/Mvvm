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

[https://github.com/hltj/kotlin-web-site-cn/edit/master/pages/docs/reference/basic-types.md)

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





### 取消与超时

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



### 通道

延期的值提供了一种便捷的方法使单个值在多个协程之间进行相互传输。 通道提供了一种在流中传输值的方法。

### 通道基础

一个 [Channel](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-channel/index.html) 是一个和 `BlockingQueue` 非常相似的概念。其中一个不同是它代替了阻塞的 `put` 操作并提供了挂起的 [send](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/send.html)，还替代了阻塞的 `take` 操作并提供了挂起的 [receive](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/receive.html)。



```
val channel = Channel<Int>()
launch {
    // 这里可能是消耗大量 CPU 运算的异步逻辑，我们将仅仅做 5 次整数的平方并发送
    for (x in 1..5) channel.send(x * x)
}
// 这里我们打印了 5 次被接收的整数：
repeat(5) { println(channel.receive()) }
println("Done!")
```



### 关闭与迭代通道

和队列不同，一个通道可以通过被关闭来表明没有更多的元素将会进入通道。 在接收者中可以定期的使用 `for` 循环来从通道中接收元素。

从概念上来说，一个 [close](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-send-channel/close.html) 操作就像向通道发送了一个特殊的关闭指令。 这个迭代停止就说明关闭指令已经被接收了。所以这里保证所有先前发送出去的元素都在通道关闭前被接收到。



```
val channel = Channel<Int>()
launch {
    for (x in 1..5) channel.send(x * x)
    channel.close() // 我们结束发送
}
// 这里我们使用 `for` 循环来打印所有被接收到的元素（直到通道被关闭）
for (y in channel) println(y)
println("Done!")
```



### 构建通道生产者

协程生成一系列元素的模式很常见。 这是 *生产者——消费者* 模式的一部分，并且经常能在并发的代码中看到它。 你可以将生产者抽象成一个函数，并且使通道作为它的参数，但这与必须从函数中返回结果的常识相违悖。

这里有一个名为 [produce](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/produce.html) 的便捷的协程构建器，可以很容易的在生产者端正确工作， 并且我们使用扩展函数 [consumeEach](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/consume-each.html) 在消费者端替代 `for` 循环：

```
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) send(x * x)
}

fun main() = runBlocking {
    val squares = produceSquares()
    squares.consumeEach { println(it) }
    println("Done!")
}
```



### 管道

管道是一种一个协程在流中开始生产可能无穷多个元素的模式，并且另一个或多个协程开始消费这些流，做一些操作，并生产了一些额外的结果。 在下面的例子中，对这些数字仅仅做了平方操作：

```
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking {
    val numbers = produceNumbers() // 从 1 开始生产整数
    val squares = square(numbers) // 对整数做平方
    for (i in 1..5) println(squares.receive()) // 打印前 5 个数字
    println("Done!") // 我们的操作已经结束了
    coroutineContext.cancelChildren() // 取消子协程
}

fun CoroutineScope.produceNumbers() = produce<Int> {
    var x = 1
    while (true) send(x++) // 从 1 开始的无限的整数流
}

fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in numbers) send(x * x)
}
```

**所有创建了协程的函数被定义在了 [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) 的扩展上， 所以我们可以依靠[结构化并发](https://kotlinlang.org/docs/reference/coroutines/composing-suspending-functions.html#structured-concurrency-with-async)来确保没有常驻在我们的应用程序中的全局协程。**

### 组合挂起函数

### 使用 async 并发

如果 `doSomethingUsefulOne` 与 `doSomethingUsefulTwo` 之间没有依赖，并且我们想更快的得到结果，让它们进行 *并发* 吗？这就是 [async](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html) 可以帮助我们的地方。

在概念上，[async](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html) 就类似于 [launch](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html)。它启动了一个单独的协程，这是一个轻量级的线程并与其它所有的协程一起并发的工作。不同之处在于 `launch` 返回一个 [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) 并且不附带任何结果值，而 `async` 返回一个 [Deferred](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/index.html)—— 一个轻量级的非阻塞 future，  这代表了一个将会在稍后提供结果的 promise。你可以使用 `.await()`在一个延期的值上得到它的最终结果， 但是 `Deferred` 也是一个 `Job`，所以如果需要的话，你可以取消它。

**注意，使用协程进行并发总是显式的。**

```
suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // 假设我们在这里做了一些有用的事
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // 假设我们在这里也做了一些有用的事
    return 29
}

fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}
```



### 惰性启动的 async

使用一个可选的参数 `start` 并传值 [CoroutineStart.LAZY](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-start/-l-a-z-y.html)，可以对 [async](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html) 进行惰性操作。 只有当结果需要被 [await](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/await.html) 或者如果一个 [start](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/start.html) 函数被调用，协程才会被启动。

**注意，如果我们在 `println` 中调用 [await](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/await.html) 并在个别协程上省略 [start](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/start.html)，则我们会得到顺序的行为作为 [await](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/await.html) 来启动协程的执行并且等待执行结束，这不是懒序列的预期用例。 当计算值涉及暂停函数时，该用例中使用 `async(start = CoroutineStart.LAZY)` 替换 标准库中的 `lazy` 函数。**

### async 风格的函数

我们可以定义异步风格的函数来 *异步* 的调用 `doSomethingUsefulOne` 和 `doSomethingUsefulTwo` 并使用 [async](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html) 协程建造器并带有一个显式的 [GlobalScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-global-scope/index.html) 引用。 我们给这样的函数的名称中加上“Async”后缀来突出表明：事实上，它们只做异步计算并且需要使用延期的值来获得结果。

```
// somethingUsefulOneAsync 函数的返回值类型是 Deferred<Int>
fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

// somethingUsefulTwoAsync 函数的返回值类型是 Deferred<Int>
fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}
```



注意，这些 `xxxAsync` 函数**不是** *挂起* 函数。它们可以在任何地方被使用。 然而，它们总是在调用它们的代码中意味着异步（这里的意思是 *并发* ）执行。

下面的例子展示了它们在协程的外面是如何使用的：

```
// 注意，在这个示例中我们在 `main` 函数的右边没有加上 `runBlocking`
fun main() {
    val time = measureTimeMillis {
        // 我们可以在协程外面启动异步执行
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        // 但是等待结果必须调用其它的挂起或者阻塞
        // 当我们等待结果的时候，这里我们使用 `runBlocking { …… }` 来阻塞主线程
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")
}
```

这种带有异步函数的编程风格仅供参考，因为这在其它编程语言中是一种受欢迎的风格。在 Kotlin 的协程中使用这种风格是**强烈不推荐**的， 原因如下所述。

考虑一下如果 `val one = somethingUsefulOneAsync()` 这一行和 `one.await()` 表达式这里在代码中有逻辑错误， 并且程序抛出了异常以及程序在操作的过程中被中止，将会发生什么。 通常情况下，一个全局的异常处理者会捕获这个异常，将异常打印成日记并报告给开发者，但是反之该程序将会继续执行其它操作。但是这里我们的 `somethingUsefulOneAsync` 仍然在后台执行， 尽管如此，启动它的那次操作也会被终止。这个程序将不会进行结构化并发，如下一小节所示。

### 使用 async 的结构化并发

让我们使用[使用 async 的并发](https://www.kotlincn.net/docs/reference/coroutines/composing-suspending-functions.html#使用-async-的结构化并发)这一小节的例子并且提取出一个函数并发的调用 `doSomethingUsefulOne` 与 `doSomethingUsefulTwo` 并且返回它们两个的结果之和。 由于 [async](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html) 被定义为了 [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) 上的扩展，我们需要将它写在作用域内，并且这是 [coroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) 函数所提供的：

```
suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}
```

这种情况下，如果在 `concurrentSum` 函数内部发生了错误，并且它抛出了一个异常， 所有在作用域中启动的协程都将会被取消。



```
val time = measureTimeMillis {
    println("The answer is ${concurrentSum()}")
}
println("Completed in $time ms")
```



从上面的 main 函数的输出可以看出，我们仍然可以同时执行这两个操作：

```
The answer is 42
Completed in 1017 ms
```

取消始终通过协程的层次结构来进行传递：

```
import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    try {
        failedConcurrentSum()
    } catch(e: ArithmeticException) {
        println("Computation failed with ArithmeticException")
    }
}

suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async<Int> { 
        try {
            delay(Long.MAX_VALUE) // 模拟一个长时间的运算
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> { 
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}

/**
输出结果：
Second child throws an exception
First child was cancelled
Computation failed with ArithmeticException
*/
```



### 协程上下文与调度器

协程总是运行在一些以 [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/) 类型为代表的上下文中，它们被定义在了 Kotlin 的标准库里。

协程上下文是各种不同元素的集合。其中主元素是协程中的 [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html)。

### 非受限调度器 vs 受限调度器

[Dispatchers.Unconfined](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-unconfined.html) 协程调度器会在程序运行到第一个挂起点时，在调用者线程中启动。挂起后，它将在挂起函数执行的线程中恢复，恢复的线程完全取决于该挂起函数在哪个线程执行。非受限调度器适合协程不消耗 CPU 时间也不更新任何限于特定线程的共享数据（如 UI）的情境。

另一方面，协程调度器默认承袭外部的 [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) 的调度器。 特别是 [runBlocking](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/run-blocking.html) 的默认协程调度器仅限于调用者线程，因此承袭它将会把执行限制在该线程中， 并具有可预测的 FIFO 调度的效果。



```
import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    launch(Dispatchers.Unconfined) { // 非受限的——将和主线程一起工作
        println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
        delay(500)
        println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
    }
    launch { // 父协程的上下文，主 runBlocking 协程
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        delay(1000)
        println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
    }    
}
```

执行后的输出：

```
Unconfined      : I'm working in thread main
main runBlocking: I'm working in thread main
Unconfined      : After delay in thread kotlinx.coroutines.DefaultExecutor
main runBlocking: After delay in thread main
```

因此，该协程从 `runBlocking {……}` 协程中承袭了上下文并在主线程中执行，同时使用非受限调度器的协程从被执行 [delay](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/delay.html) 函数的默认执行者线程中恢复。

> 非受限的调度器是一种高级机制，可以在某些极端情况下提供帮助而不需要调度协程以便稍后执行或产生不希望的副作用， 因为某些操作必须立即在协程中执行。 非受限调度器不应该被用在通常的代码中。

### 调试协程与线程

协程可以在一个线程上挂起并在其它线程上恢复。 甚至一个单线程的调度器也是难以弄清楚协程在何时何地正在做什么事情。使用通常调试应用程序的方法是让线程在每一个日志文件的日志声明中打印线程的名字。这种特性在日志框架中是普遍受支持的。但是在使用协程时，单独的线程名称不会给出很多协程上下文信息，所以`kotlinx.coroutines` 包含了调试工具来让它更简单。

使用 `-Dkotlinx.coroutines.debug` JVM 参数在打印Thread.currentThread().name时，输出的信息会带上协程上下文信息。

 [CoroutineName](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-name/index.html) 上下文元素可以给线程像给函数命名一样命名。它在协程被执行且[调试模式](https://www.kotlincn.net/docs/reference/coroutines/coroutine-context-and-dispatchers.html#调试协程与线程)被开启时将显示线程的名字。

### 上下文中的作业

协程的 [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) 是它上下文中的一部分。协程可以在它所属的上下文中使用 `coroutineContext[Job]` 表达式来取回它。

### 子协程

当一个协程被其它协程在 [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) 中启动的时候， 它将通过 [CoroutineScope.coroutineContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/coroutine-context.html) 来承袭上下文，并且这个新协程的 [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) 将会成为父协程作业的 *子* 作业。当一个父协程被取消的时候，所有它的子协程也会被递归的取消。

然而，当 [GlobalScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-global-scope/index.html) 被用来启动一个协程时，它与作用域无关且是独立被启动的。

### 协程的取消

当一个协程被其它协程在 [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) 中启动的时候， 它将通过 [CoroutineScope.coroutineContext](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/coroutine-context.html) 来承袭上下文，并且这个新协程的 [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/index.html) 将会成为父协程作业的 *子* 作业。当一个父协程被取消的时候，所有它的子协程也会被递归的取消。**当 [GlobalScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-global-scope/index.html) 被用来启动一个协程时，它与作用域无关且是独立被启动的。**

**一个父协程总是等待所有的子协程执行结束。父协程并不显式的跟踪所有子协程的启动以及不必使用 [Job.join](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/join.html) 在最后的时候等待它们。**

### 组合上下文中的元素

有时我们需要在协程上下文中定义多个元素。我们可以使用 `+` 操作符来实现。 比如说，我们可以显式指定一个调度器来启动协程并且同时显式指定一个命名：

```
launch(Dispatchers.Default + CoroutineName("test")) {
    println("I'm working in thread ${Thread.currentThread().name}")
}
```



### 协程作用域

让我们把有关上下文、子协程以及作业的知识梳理一下。假设我们的应用程序中有一个在生命周期中的对象，但这个对象并不是协程。假如，我们写了一个 Android 应用程序并在上下文中启动了多个协程来为 Android activity 进行异步操作来拉取以及更新数据，或作动画等。当 activity 被销毁的时候这些协程必须被取消以防止内存泄漏。我们当然可以手动操作上下文以及作业来绑定 activity 与协程的生命周期，但是 `kotlinx.coroutines` 提供了一个抽象的封装：[CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html)。 你应该已经熟悉了协程作用域，因为所有的协程构建器都被声明为扩展。

我们通过创建一个 [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) 实例来管理协程的生命周期，并使它与 activit 的生命周期相关联。`CoroutineScope` 可以通过 [CoroutineScope()](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope.html) 创建或者通过[MainScope()](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-main-scope.html) 工厂函数。前者创建了一个通用作用域，而后者为使用 [Dispatchers.Main](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-main.html) 作为默认调度器的 UI 应用程序 创建作用域：



```
class Activity {
    private val mainScope = MainScope()

    fun destroy() {
        mainScope.cancel()
    }
    // 继续运行……
```

或者，我们可以在这个 `Activity` 类中实现 [CoroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html) 接口。最好的方法是使用具有默认工厂函数的委托。 我们也可以将所需的调度器与作用域合并（我们在这个示例中使用 [Dispatchers.Default](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-default.html)）。

```
class Activity : CoroutineScope by CoroutineScope(Dispatchers.Default) {
    // 继续运行……
```



### 线程局部数据

有时能够传递一些线程局部的数据很方便，但是，对于协程来说，它们不受任何特定线程的约束，所以很难手动的去实现它并且不写出大量的样板代码。

[`ThreadLocal`](https://docs.oracle.com/javase/8/docs/api/java/lang/ThreadLocal.html)， [asContextElement](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/java.lang.-thread-local/as-context-element.html) 扩展函数在这里会充当救兵。它创建了额外的上下文元素， 且保留给定 `ThreadLocal` 的值，并在每次协程切换其上下文时恢复它。



### 异常的传播

协程构建器有两种风格：自动的传播异常（[launch](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html) 以及 [actor](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/actor.html)） 或者将它们暴露给用户（[async](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html) 以及 [produce](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/produce.html)）。 前者对待异常是不处理的，类似于 Java 的 `Thread.uncaughtExceptionHandler`， 而后者依赖用户来最终消耗异常，比如说，通过 [await](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/await.html) 或 [receive](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/receive.html) （[produce](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/produce.html) 以及 [receive](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/-receive-channel/receive.html) 在[通道](https://www.kotlincn.net/docs/reference/coroutines/channels.html)中介绍过）。

但是如果不想将所有的异常打印在控制台中呢？ [CoroutineExceptionHandler](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-exception-handler/index.html) 上下文元素被用来将通用的 `catch` 代码块用于在协程中自定义日志记录或异常处理。 它和使用 [`Thread.uncaughtExceptionHandler`](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#setUncaughtExceptionHandler(java.lang.Thread.UncaughtExceptionHandler)) 很相似。

在 JVM 中可以重定义一个全局的异常处理者来将所有的协程通过 [`ServiceLoader`](https://docs.oracle.com/javase/8/docs/api/java/util/ServiceLoader.html) 注册到 [CoroutineExceptionHandler](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-exception-handler/index.html)。 全局异常处理者就如同 [`Thread.defaultUncaughtExceptionHandler`](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#setDefaultUncaughtExceptionHandler(java.lang.Thread.UncaughtExceptionHandler)) 一样，在没有更多的指定的异常处理者被注册的时候被使用。 在 Android 中， `uncaughtExceptionPreHandler` 被设置在全局协程异常处理者中。

[CoroutineExceptionHandler](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-exception-handler/index.html) 仅在预计不会由用户处理的异常上调用， 所以在 [async](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html) 构建器中注册它没有任何效果。

### 取消与异常

取消与异常紧密相关。协程内部使用 `CancellationException` 来进行取消，这个异常会被所有的处理者忽略，所以那些可以被 `catch` 代码块捕获的异常仅仅应该被用来作为额外调试信息的资源。 当一个协程在没有任何理由的情况下使用 [Job.cancel](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/cancel.html) 取消的时候，它会被终止，但是它不会取消它的父协程。 无理由取消是父协程取消其子协程而非取消其自身的机制。



### CoroutineExceptionHandler

 [CoroutineExceptionHandler](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-exception-handler/index.html) 上下文元素被用来将通用的 `catch` 代码块用于在协程中自定义日志记录或异常处理。 它和使用 [`Thread.uncaughtExceptionHandler`](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#setUncaughtExceptionHandler(java.lang.Thread.UncaughtExceptionHandler)) 很相似。

在 JVM 中可以重定义一个全局的异常处理者来将所有的协程通过 [`ServiceLoader`](https://docs.oracle.com/javase/8/docs/api/java/util/ServiceLoader.html) 注册到 [CoroutineExceptionHandler](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-exception-handler/index.html)。 全局异常处理者就如同 [`Thread.defaultUncaughtExceptionHandler`](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#setDefaultUncaughtExceptionHandler(java.lang.Thread.UncaughtExceptionHandler)) 一样，在没有更多的指定的异常处理者被注册的时候被使用。 在 Android 中， `uncaughtExceptionPreHandler` 被设置在全局协程异常处理者中。

[CoroutineExceptionHandler](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-exception-handler/index.html) 仅在预计不会由用户处理的异常上调用， 所以在 [async](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/async.html) 构建器中注册它没有任何效果。



```
val handler = CoroutineExceptionHandler { _, exception -> 
        println("Caught $exception") 
}
val job = GlobalScope.launch(handler) {
    throw AssertionError()
}
val deferred = GlobalScope.async(handler) {
    throw ArithmeticException() // 没有打印任何东西，依赖用户去调用 deferred.await()
}
joinAll(job, deferred)
```



取消与异常紧密相关。协程内部使用 `CancellationException` 来进行取消，这个异常会被所有的处理者忽略，所以那些可以被 `catch` 代码块捕获的异常仅仅应该被用来作为额外调试信息的资源。 当一个协程在没有任何理由的情况下使用 [Job.cancel](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job/cancel.html) 取消的时候，它会被终止，但是它不会取消它的父协程。 无理由取消是父协程取消其子协程而非取消其自身的机制。

```
val job = launch {
    val child = launch {
        try {
            delay(Long.MAX_VALUE)
        } finally {
            println("Child is cancelled")
        }
    }
    yield()
    println("Cancelling child")
    child.cancel()
    child.join()
    yield()
    println("Parent is not cancelled")
}
job.join()
```

如果协程遇到除 `CancellationException` 以外的异常，它将取消具有该异常的父协程。 这种行为不能被覆盖，且它被用来提供一个稳定的协程层次结构来进行[结构化并发](https://github.com/Kotlin/kotlinx.coroutines/blob/master/docs/composing-suspending-functions.md#structured-concurrency-with-async)而无需依赖 [CoroutineExceptionHandler](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-exception-handler/index.html) 的实现。 且当所有的子协程被终止的时候，原本的异常被父协程所处理。

### 监督作业

[SupervisorJob](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-supervisor-job.html) 可以被用于这些目的。它类似于常规的 [Job](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-job.html)，唯一的取消异常将只会向下传播。

### 监督作用域

对于*作用域*的并发，[supervisorScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/supervisor-scope.html) 可以被用来替代 [coroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) 来实现相同的目的。它只会单向的传播并且当子作业自身执行失败的时候将它们全部取消。它也会在所有的子作业执行结束前等待， 就像 [coroutineScope](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) 所做的那样。

```
import kotlin.coroutines.*
import kotlinx.coroutines.*

fun main() = runBlocking {
    try {
        supervisorScope {
            val child = launch {
                try {
                    println("Child is sleeping")
                    delay(Long.MAX_VALUE)
                } finally {
                    println("Child is cancelled")
                }
            }
            // 使用 yield 来给我们的子作业一个机会来执行打印
            yield()
            println("Throwing exception from scope")
            throw AssertionError()
        }
    } catch(e: AssertionError) {
        println("Caught assertion error")
    }
}
```

### 监督协程中的异常

常规的作业和监督作业之间的另一个重要区别是异常处理。 每一个子作业应该通过异常处理机制处理自身的异常。 这种差异来自于子作业的执行失败不会传播给它的父作业的事实。



```
import kotlin.coroutines.*
import kotlinx.coroutines.*

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception -> 
        println("Caught $exception") 
    }
    supervisorScope {
        val child = launch(handler) {
            println("Child throws an exception")
            throw AssertionError()
        }
        println("Scope is completing")
    }
    println("Scope is completed")
}
```



### 共享的可变状态与并发

协程可用多线程调度器（比如默认的 [Dispatchers.Default](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-default.html)）并发执行。这样就可以提出所有常见的并发问题。主要的问题是同步访问**共享的可变状态**。 协程领域对这个问题的一些解决方案类似于多线程领域中的解决方案， 但其它解决方案则是独一无二的。

volatile不能解决协程并发问题。为 volatile 变量保证可线性化（这是“原子”的技术术语）读取和写入变量，但在大量动作（在我们的示例中即“递增”操作）发生时并不提供原子性。

### 线程安全的数据结构

一种对线程、协程都有效的常规解决方法，就是使用线程安全（也称为同步的、 可线性化、原子）的数据结构，它为需要在共享状态上执行的相应操作提供所有必需的同步处理。在简单的计数器场景中，我们可以使用具有 `incrementAndGet` 原子操作的 `AtomicInteger` 类：

```
var counter = AtomicInteger()

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            counter.incrementAndGet()
        }
    }
    println("Counter = $counter")
}
```



### 以细粒度限制线程

*限制线程* 是解决共享可变状态问题的一种方案：对特定共享状态的所有访问权都限制在单个线程中。它通常应用于 UI 程序中：所有 UI 状态都局限于单个事件分发线程或应用主线程中。这在协程中很容易实现，通过使用一个单线程上下文：

```
val counterContext = newSingleThreadContext("CounterContext")
var counter = 0

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            // 将每次自增限制在单线程上下文中
            withContext(counterContext) {
                counter++
            }
        }
    }
    println("Counter = $counter")
}
```

这段代码运行非常缓慢，因为它进行了 *细粒度* 的线程限制。每个增量操作都得使用 [withContext(counterContext)] 块从多线程 [Dispatchers.Default](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-default.html) 上下文切换到单线程上下文。

### 以粗粒度限制线程

在实践中，线程限制是在大段代码中执行的，例如：状态更新类业务逻辑中大部分都是限于单线程中。下面的示例演示了这种情况， 在单线程上下文中运行每个协程。



```
val counterContext = newSingleThreadContext("CounterContext")
var counter = 0

fun main() = runBlocking {
    // 将一切都限制在单线程上下文中
    withContext(counterContext) {
        massiveRun {
            counter++
        }
    }
    println("Counter = $counter")
}
```



### 互斥

该问题的互斥解决方案：使用永远不会同时执行的 *关键代码块* 来保护共享状态的所有修改。在阻塞的世界中，你通常会为此目的使用 `synchronized` 或者 `ReentrantLock`。 在协程中的替代品叫做 [Mutex](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/-mutex/index.html) 。它具有 [lock](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/-mutex/lock.html) 和 [unlock](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/-mutex/unlock.html) 方法， 可以隔离关键的部分。关键的区别在于 `Mutex.lock()` 是一个挂起函数，它不会阻塞线程。

还有 [withLock](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.sync/with-lock.html) 扩展函数，可以方便的替代常用的 `mutex.lock(); try { …… } finally { mutex.unlock() }` 模式：

```
val mutex = Mutex()
var counter = 0

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            // 用锁保护每次自增
            mutex.withLock {
                counter++
            }
        }
    }
    println("Counter = $counter")
}
```



一个 [actor](https://en.wikipedia.org/wiki/Actor_model) 是由协程、 被限制并封装到该协程中的状态以及一个与其它协程通信的 *通道* 组合而成的一个实体。一个简单的 actor 可以简单的写成一个函数， 但是一个拥有复杂状态的 actor 更适合由类来表示。

有一个 [actor](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/actor.html) 协程构建器，它可以方便地将 actor 的邮箱通道组合到其作用域中（用来接收消息）、组合发送 channel 与结果集对象，这样对 actor 的单个引用就可以作为其句柄持有。

使用 actor 的第一步是定义一个 actor 要处理的消息类。 Kotlin 的[密封类](https://kotlinlang.org/docs/reference/sealed-classes.html)很适合这种场景。

actor 本身执行时所处上下文（就正确性而言）无关紧要。一个 actor 是一个协程，而一个协程是按顺序执行的，因此将状态限制到特定协程可以解决共享可变状态的问题。实际上，actor 可以修改自己的私有状态， 但只能通过消息互相影响（避免任何锁定）。

actor 在高负载下比锁更有效，因为在这种情况下它总是有工作要做，而且根本不需要切换到不同的上下文。

> 注意，[actor](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/actor.html) 协程构建器是一个双重的 [produce](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.channels/produce.html) 协程构建器。一个 actor 与它接收消息的通道相关联，而一个 producer 与它发送元素的通道相关联。