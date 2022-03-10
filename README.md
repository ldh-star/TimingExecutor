
# 定时执行器-TimingExecutor

[![](https://jitpack.io/v/ldh-star/TimingExecutor.svg)](https://jitpack.io/#ldh-star/TimingExecutor) ![](https://img.shields.io/badge/author-ldh-orange.svg) ![](https://img.shields.io/hexpm/l/plug.svg)


### 说明

在某些情形下：比如要用户触摸完某块区域的2秒后触发一个事件。
监听触摸的函数在触摸时会不停的调用，我们希望在最后一次调用的几秒后触发这个事件，于是有了这个工具。

就算频繁的刷新计时，也不会造成频繁的创建新对象和切换线程

### 使用

#### 1.在 build.gradle 中添加依赖


```gradle
implementation 'com.github.ldh-star:TimingExecutor:1.0.1'
```

#### 2.使用

```kotlin
//参数1： 是要延迟的时间
//参数2： 这一个延迟任务的唯一标识，可以为任意，Int,String,TextView...（你可以放心的使用View对象作为id，因为内部对id和exec是以WeakReference的方式持有）
TimingExecutor.delayExecute(2000, id) {
    println("时间到！")
}
```


## Licenses

```
 Copyright 2022 original author or authors.
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```