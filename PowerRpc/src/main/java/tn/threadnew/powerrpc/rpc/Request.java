package tn.threadnew.powerrpc.rpc;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: ThreadNew
 * @Description: TODO  请求
 * @Date: 2020/11/1 16:21
 * @Version: 1.0
 */
public class Request {
    //唯一标志
    private long id;
    //类名
    private String className;
    //方法名
    private String methodName;
    //参数类型
    private Class<?>[] parameterTypes;
    //参数值
    private Object[] parameters;
    //版本
    private String version;
    //分组
    private String group;

    private static long newId() {
        // getAndIncrement()增长到MAX_VALUE时，再增长会变为MIN_VALUE，负数也可以做为ID
        return INVOKE_ID.getAndIncrement();
    }

    private static final AtomicLong INVOKE_ID = new AtomicLong(0);

    public Request() {
        this.id = newId();
    }

    public long getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", parameters=" + Arrays.toString(parameters) +
                ", version='" + version + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
