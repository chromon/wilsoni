package com.test.dataarea.heap;

import com.test.classfile.constant.ConstantMethodrefInfo;

// 方法的符号引用（非接口方法）
public class MethodRef extends MemberRef {

    private XMethod method;

    public MethodRef(RuntimeConstantPool rcp, ConstantMethodrefInfo refInfo) {
        this.setRcp(rcp);
        this.copyMemberRefInfo(refInfo);
    }

    // 解析方法符号引用
    public XMethod resolvedMethod() {
        if (this.method == null) {
            // 符号引用还没有被解析过，调用相关方法进行解析
            this.resolveMethodRef();
        }
        return this.method;
    }

    // 解析非接口方法符号引用
    private void resolveMethodRef() {
        // 类 d 想通过方法符号引用访问类 c 的某个方法
        XClass d = this.getRcp().getClazz();
        // 先解析符号引用得到类 c
        XClass c = this.resolvedClass();

        // 如果 c 是接口则抛出异常
        if (c.isInterface()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }

        // 根据方法名和描述符查找方法
        XMethod method = this.lookupMethod(c, this.getName(), this.getDescriptor());
        if (method == null) {
            // 查找不到对应方法则抛出异常
            throw new RuntimeException("java.lang.NoSuchMethodError");
        }

        // 检查类 d 是否有权限访问该方法
        if (!method.isAccessibleTo(d)) {
            throw new RuntimeException("java.lang.IllegalAccessError");
        }

        this.method = method;
    }

    // 根据方法名和描述符查找方法
    private XMethod lookupMethod(XClass c, String name, String descriptor) {
        // 从 c 的继承层次中查找
        XMethod method = MethodLookup.lookupMethodInClass(c, name, descriptor);
        if (method == null) {
            // 如果继承层次中找不到就到接口中去找
            method = MethodLookup.lookupMethodInInterfaces(c.getInterfaces(), name, descriptor);
        }
        return method;
    }
}
