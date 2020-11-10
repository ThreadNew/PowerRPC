# PowerRPCt

> PowerRPC是一款基于zookeeper、netty的小型的分布式的rpc框架

---

##### 特点

- 注册中心可定制化；目前服务发现和服务注册采用zookeeper
- 支持TCP的长连接
- 支持不同的负载均衡的策略，可定制化。注：已实现策略 随机、加权随机
- 消息序列化采用messagepack
- 支持同步和异步调用
- 支持配置文件配置

---

#### 简单入门

##### 1  配置文件的创建

```java
power.rpc.registry.server.ip=192.168.168.135:2181  #注册中心的地址
power.rpc.server.name=myapp #服务的名称
power.rpc.server.port=8311 #服务的端口号

```

