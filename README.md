# PowerRPC

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

#### 入门案例

#### **服务端**

#####  1、配置文件

```java 
power.rpc.registry.server.ip=192.168.168.135:2181 #zookeeper的注册中心的地址
power.rpc.server.name=myapp # 服务的名称 唯一标志
power.rpc.server.port=8311 # 服务的端口号
```

##### 2、创建简单的服务

> 创建接口及其实现类

```java
package tn.threadnew.powerrpc.info;

public interface UserInfo {
    public String getName(String name);
    public int add(int a,int b);
    public String acc(String a, int b);
}
```

impl

```java
package tn.threadnew.powerrpc.info;

import tn.threadnew.powerrpc.rpc.netty.annotation.RpcService;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/4 22:04
 * @Version: 1.0
 */
@RpcService(version = "1.0",  group = "info")
public class Stu implements UserInfo {

    @Override
    public String getName(String name) {
        return name;
    }

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    public String acc(String a, int b) {
        return a + "   cen " + b;
    }


}
```

> 在具体的服务上加上@RpcService服务注解即可。
>
> @RpcService的参数讲解
>
> version :服务的版本号
>
> group：服务分组 ：其作用是区别同一接口的不同实现类 

##### 3、服务端的使用

```java
public class Server {
    private ServerProxy sp = ServerProxy.newInstance();

    @Test
    public void start() throws Exception {
        ZkConnectManager zk = new ZkConnectManager();
        zk.load("zk.properties");
        sp.scan("tn.threadnew.powerrpc.info").setRegister(zk).setConfig(zk.getConfig()).connect();
       // Thread.sleep(5000000);
    }
}
```

> 第一步： 创建与注册中心连接的管理器如ZkConnectManager ，
>
> 第二步：使用load函数加载配置文件
>
> 第三步： 创建服务代理器ServerProxy；使用newInstance() 函数创建对象
>
> 第四步：配置包扫描的路径 采用scan函数
>
> 第五步：注册注册中心；注册配置中心。
>
> 第六步：建立连接。
>
> 

**客户端**

##### 1、配置文件

```java 
power.rpc.registry.server.ip=192.168.168.135:2181 #zookeeper的注册中心的地址
```

##### 2、服务调用

> 提供要调用的接口

```java
public interface UserInfo {
    public String getName(String name);
    public int add(int a,int b);
    public String acc(String a, int b);
}
```

> 与注册中心建立连接
>
> 调用远程服务

```java
package tn.threadnew.powerrpc.rpc;

import org.junit.Test;
import tn.threadnew.powerrpc.info.UserInfo;
import tn.threadnew.powerrpc.register.manage.ZkConnectManager;
import tn.threadnew.powerrpc.rpc.netty.client.proxy.Proxy;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/4 22:29
 * @Version: 1.0
 */
public class client {
    // 第一步： 创建调用服务代理类
    private Proxy proxy = Proxy.newInstance();

    @Test
    public void Test() throws Exception {
        // 第二步：创建注册中心管理器
        ZkConnectManager zk = new ZkConnectManager();
        //第四步：加载配置文件
        zk.load("zk.properties");
        //第五步：注册注册中心且建立连接
        proxy.setRegister(zk).connect();
        //第六步： 调用服务
        UserInfo info = proxy.instance(UserInfo.class, "1.0", "info");
        System.out.println(info.add(2, 3));
        UserInfo info1 = proxy.call(UserInfo.class, "1.0", "info", new ResponseCallBack() {
            @Override
            public void done(Object o) {
                Response re = (Response) o;
                System.out.println("data  " + re.getData());
            }
        });
        info1.acc("che c", 7);
        Thread.sleep(50000);

        proxy.close();
    }

}

结果
5
data  che c   cen 7
```

> 服务的调用方式两种 **同步调用**和**异步调用**
>
> 1、 同步调用
>
> ```java
> 方法：instance(Class<T> clazz, String version, String group)
> clazz :服务接口的class对象
> version： 服务的版本号
> group： 服务分组
> ```

> 2、异步调用
>
> ```
>  方法：call(Class<T> clazz, String version, String group, ResponseCallBack callBack)
>  clazz :服务接口的class对象
>  version： 服务的版本号
>  group： 服务分组
>  callback ：异步回调函数 重写done方法。
> ```

