package com.test.dataarea.heap;

import javax.sql.rowset.spi.XmlReader;
import java.util.List;

// 方法查找
public class MethodLookup {

    // 在类的继承层次中查找方法
    public static XMethod lookupMethodInClass(
            XClass c, String name, String descriptor) {
        for (; c != null; c = c.getSuperClass()) {
            for (XMethod method: c.getMethods()) {
                if (method.getName().equals(name)
                        && method.getDescriptor().equals(descriptor)) {
                    return method;
                }
            }
        }
        return null;
    }

    // 在类的接口中查找方法
    public static XMethod lookupMethodInInterfaces(
            List<XClass> interfaces, String name, String descriptor) {
        for (XClass ifa: interfaces) {
            for (XMethod method: ifa.getMethods()) {
                if (method.getName().equals(name)
                        && method.getDescriptor().equals(descriptor)) {
                    return method;
                }
            }
            XMethod m = lookupMethodInInterfaces(ifa.getInterfaces(), name, descriptor);
            if (m != null) {
                return m;
            }
        }
        return null;
    }
}
