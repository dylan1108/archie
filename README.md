# 灰度发布方案

##  微服务灰度发布介绍

![image-20220404234524630](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220404234524630.png)

灰度发布的特性是混合部署，多版本共存，平滑升级，如果升级失败可以回滚至上一版本，新版本上线无需提供全套其余服务，节省资源。

核心思想是，通过用户特定标识（ parameter 、header、body、ip、id），根据路由策略，将请求引流到不同版本的服务上。

![image-20220404234622474](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220404234622474.png)

### 接入层的三种方案

#### 一、nginx+lua

参考逻辑架构图，用户携带标识，最外层nginx的lua脚本添加控制策略，解析出导流标识，网关根据导流标识分发至不同版本服务。

![image-20220404234700233](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220404234700233.png)

优点：

> 1. zuul无须负载策略压力，对于服务来讲灰度动作为黑盒，无须关心。
> 2. 即使没有加入服务中心的应用也可以通过nginx来做灰度。

 

缺点：

> 1. 内部请求无法控制灰度，比如Service1的v1需要请求Sservice2的v2。
> 2. 对于nginx来说，必须需要访问服务中心获取服务信息，否则服务集群情况相当于黑盒。

#### 二、zuul+groovy

此方案是将接入层下沉到zuul，并且配合groovy动态编译实现动态策略

![image-20220404234928905](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220404234928905.png)

优点：

> 1. zuul可以感知注册中心的服务，能够提供更灵活的分发策略。
> 2. 可以基于zuul的路由配置设置路由规则。

缺点：

> 1. zuul需要添加filter处理分发策略，如果开启策略过多，会带来性能压力。

#### 三、ribbon+rule

基于ribbon在客户端做负载均衡逻辑中添加分发逻辑。

![image-20220404235139796](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220404235139796.png)

![image-20220404235210359](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220404235210359.png)

优点：

1. 基于tag可以做长链路的灰度策略。
2. 分发策略在调用端，负载不会集中在某一服务端。
3. ribbon支持restTemplate、zuul、feign请求。

缺点：

1. 采用jar包方式引用，只能是内部项目使用，无法对外部服务提供灰度发布支持。

 

由于我们目前项目都为内部使用，并不管理外部项目，所以推荐第三种实现方式。能够提供更好的负载，更好的策略控制

### 版本管理方案

基于maven的project.version管理服务版本。

![image-20220404235254560](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220404235254560.png)

### 优雅停机方案

#### Shutdown

通过shutdown通知服务停止服务，等待处理完所有请求，此时需要做析构等处理操作，

之后通知注册中心服务下线。

#### 服务下线

通过http请求通知注册中心下线服务。

### 复杂场景的方案

#### 1. 长链路上有多个业务服务的场景

调用链路为AàBàC,总体处理策略为通过打tag的方式，将需要灰度的服务汇聚起来，此时注册在注册中服务的version信息应该就是tagA。

![image-20220404235332981](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220404235332981.png)

#### 2. 涉及到数据的灰度服务

如果灰度涉及到数据的话，并且两个数据版本字段有差异的话，根据灰度状态分为以下几种状态

##### l 部分灰度

此时状态为灰度刚上线，新旧版本共存。

新版本的数据需要转发至老版本，修改请求时，新版本数据找不到数据还需要同步拉取旧版本数据（旧版本请求了插入，新版本请求了更新或删除），如果有事物则以老版本为准。以此保证即使回滚，老版本数据是全量数据。

![image-20220404235406641](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220404235406641.png)

##### 全量灰度

此时为流量已经都切换至新版本，但新版本并未完全确认没问题，依然需要写入老版本，保证可以切回老版本。

![image-20220404235440070](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220404235440070.png)

##### l 灰度完成

此时代表新版本已经没有问题，需要将旧版本的数据同步至新版本数据库。

![image-20220404235510151](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20220404235510151.png)

http://huhanlin.com/2018/06/15/springcloud-%E7%81%B0%E5%BA%A6%E5%8F%91%E5%B8%83%E6%96%B9%E6%A1%88/



代码参考：http://itindex.net/detail/57096-spring-cloud-%E5%AE%9E%E8%B7%B5


