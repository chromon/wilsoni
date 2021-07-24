package com.test.dataarea.heap;

import com.test.classfile.constant.ConstantInterfaceMethodrefInfo;

// 接口方法符号引用
public class InterfaceMethodRef extends MemberRef {

    private XMethod method;

    public InterfaceMethodRef(RuntimeConstantPool rcp,
                              ConstantInterfaceMethodrefInfo refInfo) {
        this.setRcp(rcp);
        this.setClassName(refInfo.getClassName());
        String[] nameAndType = refInfo.getNameAndDescriptor();
        this.setName(nameAndType[0]);
        this.setDescriptor(nameAndType[1]);
    }

    // 解析接口方法的符号引用
    public XMethod resolvedInterfaceMethod() {
        if (this.method == null) {
            this.resolveInterfaceMethodRef();
        }
        return this.method;
    }

    // 解析接口方法的符号引用具体实现
    private void resolveInterfaceMethodRef() {
        XClass d = this.getRcp().getClazz();
        // 先解析符号引用得到接口 c
        XClass c = this.resolvedClass();

        // 如果 c 不是接口抛出异常
        if (!c.isInterface()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }

        // 根据方法名和描述符查找方法
        XMethod method = this.lookupInterfaceMethod(c, this.getName(), this.getDescriptor());
        if (method == null) {
            // 查找不到对应的方法则抛出异常
            throw new RuntimeException("java.lang.NoSuchMethodError");
        }

        // 检查类 d 是否有权限访问该方法
        if (!method.isAccessibleTo(d)) {
            throw new RuntimeException("java.lang.IllegalAccessError");
        }
        this.method = method;
    }

    // 根据方法名和描述符查找方法
    private XMethod lookupInterfaceMethod(
            XClass ifa, String name, String descriptor) {
        for (XMethod method: ifa.getMethods()) {
            // 如果能在接口中找到方法，直接返回方法
            if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                return method;
            }
        }
        // 接口中查找不到方法就在父接口中查找
        return MethodLookup.lookupMethodInInterfaces(ifa.getInterfaces(), name, descriptor);
    }
}
