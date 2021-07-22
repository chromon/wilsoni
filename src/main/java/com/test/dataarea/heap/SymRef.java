package com.test.dataarea.heap;

import com.test.classfile.ConstantPool;

// 类、字段、方法和接口方法的符号引用具有一定共性，抽象为类
public class SymRef {

    // 符号引用所在的运行时常量池对象
    private RuntimeConstantPool rcp;
    // 类完全限定名
    private String className;
    // 缓存解析后的 class 对象
    // 这样类符号引用只需要解析一次就可以了，后续可以直接使用缓存值
    // 对于类符号引用只要有类名，就可以解析符号引用
    // 对于字段，需要解析类符号引用得到类数据，然后用字段名和描述符查找字段数据
    // 对于方法符号引用解析过程和字段符号引用类似
    private XClass clazz;

    public XClass resolvedClass() {
        if (this.clazz == null) {
            this.resolvedClassRef();
        }
        return clazz;
    }

    // 解析类符号引用
    // 根据 Java 虚拟机规范 5.4.3.1节给出了类符号引用的解析步骤
    // 如果类 clazz 通过符号引用 N 引用类 xClass 的话，要解析 N，
    // 先用 clazz 的类加载器加载 xClass，然后检查 clazz 是否有权限访问 xClass，
    // 如果没有，则抛出 IllegalAccessError 异常
    private void resolvedClassRef() {
        XClass clazz = this.rcp.getClazz();
        XClass xClass = clazz.getClassLoader().loadClass(this.className);
        if (!xClass.isAccessibleTo(clazz)) {
            throw new RuntimeException("java.lang.IllegalAccessError");
        }
        this.clazz = xClass;
    }

    public RuntimeConstantPool getRcp() {
        return rcp;
    }

    public void setRcp(RuntimeConstantPool rcp) {
        this.rcp = rcp;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
