package com.test.dataarea.heap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClassNameManager {

    public static Map<String, String> primitiveMap = new HashMap<String, String>();

    static {
        primitiveMap.put("void", "V");
        primitiveMap.put("boolean", "Z");
        primitiveMap.put("byte", "B");
        primitiveMap.put("char", "C");
        primitiveMap.put("short", "S");
        primitiveMap.put("int", "I");
        primitiveMap.put("float", "F");
        primitiveMap.put("long", "J");
        primitiveMap.put("double", "D");
    }

    // 根据类名得到数组类名
    public static String getArrayClassName(String name) {
        return "[" + toDescriptor(name);
    }

    // 把类名转变成类型描述符
    // [XXX => [XXX
    // int  => I
    // XXX  => LXXX;
    public static String toDescriptor(String name) {
        // 如果是数组名，描述符就是类名，直接返回
        if (name.charAt(0) == '[') {
            return name;
        }

        // 如果是基本类型名，返回对应的类型描述符
        if(primitiveMap.containsKey(name)) {
            return primitiveMap.get(name);
        }

        // 普通的类名在前面加上 [;
        return "L" + name + ";";
    }

    // 根据数组类名推测出数组元素类名
    // [[XXX -> [XXX
    // [LXXX; -> XXX
    // [I -> int
    public static String getComponentClassName(String className) {
        // 数组类名以方括号开头，把它去掉就是数组元素的类型描述符，
        // 然后把类型描述符转成类名即可
        if(className.charAt(0) == '[') {
            String componentTypeDescriptor = className.substring(1);
            return ClassNameManager.toClassName(componentTypeDescriptor);
        }

        throw new RuntimeException("Not array class: " + className);
    }

    // 把类型描述符转成类名
    // [XXX  => [XXX
    // LXXX; => XXX
    // I     => int
    private static String toClassName(String descriptor) {
        if (descriptor.charAt(0) == '[') {
            // 数组
            return descriptor;
        }
        if (descriptor.charAt(0) == 'L') {
            // 对象
            return descriptor.substring(1, descriptor.length() - 1);
        }
        Set<String> keys = primitiveMap.keySet();
        for(String cls : keys) {
            if(primitiveMap.get(cls).equals(descriptor)) {
                return cls;
            }
        }
        throw new RuntimeException("Invalid descriptor:" + descriptor);
    }
}
