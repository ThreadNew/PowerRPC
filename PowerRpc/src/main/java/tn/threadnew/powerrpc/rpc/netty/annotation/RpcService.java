package tn.threadnew.powerrpc.rpc.netty.annotation;

import java.lang.annotation.*;

// 服务提供者类注解
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcService {
    String version() default "1.0";

    String group() default "";
}
