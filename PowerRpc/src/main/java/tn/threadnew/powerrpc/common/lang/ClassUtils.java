package tn.threadnew.powerrpc.common.lang;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Author: hase
 * @Description: TODO 工具类
 * @Date: 2020/10/23 20:28
 * @Version: 1.0
 */
public class ClassUtils {
    private static final ObjectMapper mapper = new ObjectMapper(new MessagePackFactory());

    //get the classLoader
    public static ClassLoader getClassLoader() {
        ClassLoader cl = null;
        if (cl == null) {
            cl = Thread.currentThread().getContextClassLoader();
        }
        if (cl == null) {
            cl = ClassUtils.class.getClassLoader();
        }
        return cl;
    }

    public static <T> T convert(Object object, Class<T> type) {
        if (object == null) return null;
        return mapper.convertValue(object, type);
    }

    public static byte[] convertToByte(Object obj) throws JsonProcessingException {
        if (obj == null) return null;
        byte[] bytes = mapper.writeValueAsBytes(obj);
        return bytes;
    }

    public static <T> T byteToObject(byte[] bytes, Class<T> type) {
        try {
            return mapper.readValue(bytes, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //多个参数调用

    public static Object invoke(Class clazz, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws Exception {
        Object o = clazz.newInstance();
        Method declaredMethod = clazz.getDeclaredMethod(methodName, parameterTypes);
        Object invoke = declaredMethod.invoke(o, parameters);
        return invoke;
    }
}
