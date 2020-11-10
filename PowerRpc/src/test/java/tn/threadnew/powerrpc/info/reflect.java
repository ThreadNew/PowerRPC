package tn.threadnew.powerrpc.info;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ThreadNew
 * @Description: TODO
 * @Date: 2020/11/5 23:27
 * @Version: 1.0
 */
public class reflect {
    public int get(int a,int b){
        return a+b;
    }
    public static final Map<String, Class<?>> BASIC_DATA_TYPE_MAP = new HashMap<>();
    static {
        BASIC_DATA_TYPE_MAP.put("int", Integer.class);
        BASIC_DATA_TYPE_MAP.put("double", Double.class);
        BASIC_DATA_TYPE_MAP.put("long", Long.class);
        BASIC_DATA_TYPE_MAP.put("short", Short.class);
        BASIC_DATA_TYPE_MAP.put("byte", Byte.class);
        BASIC_DATA_TYPE_MAP.put("boolean", Boolean.class);
        BASIC_DATA_TYPE_MAP.put("char", Character.class);
        BASIC_DATA_TYPE_MAP.put("float", Float.class);
    }
    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException, InstantiationException {
        Method get = reflect.class.getDeclaredMethod("get", int.class, int.class);
        Class<?> returnType = get.getReturnType();
        System.out.println(returnType==Integer.TYPE);
        System.out.println(returnType.isPrimitive());
        System.out.println(get.getName());
    }
    @Test
    public void test1() throws IllegalAccessException, InstantiationException {
        Class<info> infoClass = info.class;
        info info = infoClass.newInstance();
        System.out.println(info);
    }
}
