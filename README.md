
# 定时执行器-TimingExecutor
![](https://api.bintray.com/packages/li-xiaojun/jrepo/xpopup/images/download.svg)  
![](https://img.shields.io/badge/author-ldh-brightgreen.svg) ![](https://img.shields.io/badge/compileSdkVersion-32-orange.svg) ![](https://img.shields.io/badge/minSdkVersion-21-orange.svg) ![](https://img.shields.io/hexpm/l/plug.svg)


### 说明

在某些情形下：比如要用户触摸完某块区域的2秒后触发一个事件。
监听触摸的函数在触摸时会不停的调用，我们希望在最后一次调用的几秒后触发这个事件，于是有了这个工具。

就算频繁的刷新计时，也不会造成频繁的创建新对象和切换线程

### 使用

#### 1.在 build.gradle 中添加依赖


```gradle
implementation 'com.github.ldh-star:TimingExecutor:1.0.0'
```

#### 2.使用

```kotlin
//参数1： 是要延迟的时间
//参数2： 这一个延迟任务的唯一标识，可以为任意，Int,String,TextView...
TimingExecutor.delayExecute(2000, id) {
    println("时间到！")
}
```
