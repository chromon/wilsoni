package com.test.dataarea.heap;

import com.test.classfile.ClassFile;
import com.test.classfile.ConstantPool;

import java.io.File;
import java.util.List;

// 方法区内的类信息
public class XClass {

    // 访问标志
    private int accessFlags;
    // 类名，完全限定名如 java/lang/Object
    private String name;
    // 父类名，完全限定名
    private String superClassName;
    // 接口名，完全限定名
    private List<String> interfaceNames;
    // 运行时常量池
    private RuntimeConstantPool rcp;
    // 字段表
    private List<XField> fields;
    // 方法表
    private List<XMethod> methods;
    // 类加载器
    private XClassLoader classLoader;
    // 父类对象
    private XClass superClass;
    // 接口指针
    private List<XClass> interfaces;
    // 实例变量占据的空间大小
    private int instanceSlotCount;
    // 类变量占据空间大小
    private int staticSlotCount;
    // 静态变量
    private Slots staticVars;

    public XClass(ClassFile cf) {
        this.accessFlags = cf.getAccessFlag();
        this.name = cf.getClassName();
        this.superClassName = cf.getSuperClassName();
        this.interfaceNames = cf.getInterfaceNames();
        this.rcp = new RuntimeConstantPool(this, cf.getConstantPool());
        this.fields = XField.newField(this, cf.getFields());
        this.methods = XMethod.newMethods(this, cf.getMethods());
    }

    // 判断访问标志 public 是否被设置
    public boolean isPublic() {
        return 0 != (this.accessFlags & AccessFlag.ACC_PUBLIC);
    }

    // 判断访问标志 final 是否被设置
    public boolean isFinal() {
        return 0 != (this.accessFlags & AccessFlag.ACC_FINAL);
    }

    // 判断访问标志 super 是否被设置
    public boolean isSuper() {
        return 0 != (this.accessFlags & AccessFlag.ACC_SUPER);
    }

    // 判断访问标志 interface 是否被设置
    public boolean isInterface() {
        return 0 != (this.accessFlags & AccessFlag.ACC_INTERFACE);
    }

    // 判断访问标志 abstract 是否被设置
    public boolean isAbstract() {
        return 0 != (this.accessFlags & AccessFlag.ACC_ABSTRACT);
    }

    // 判断访问标志 synthetic 是否被设置
    public boolean isSynthetic() {
        return 0 != (this.accessFlags & AccessFlag.ACC_SYNTHETIC);
    }

    // 判断访问标志 annotation 是否被设置
    public boolean isAnnotation() {
        return 0 != (this.accessFlags & AccessFlag.ACC_ANNOTATION);
    }

    // 判断访问标志 enum 是否被设置
    public boolean isEnum() {
        return 0 != (this.accessFlags & AccessFlag.ACC_ENUM);
    }

    public boolean isAccessibleTo(XClass other) {
        return this.isPublic() || this.getPackageName().equals(other.getPackageName());
    }

    public boolean isSubClassOf(XClass other) {
        for(XClass cls = this.getSuperClass(); cls != null; cls = cls.getSuperClass()) {
            if(cls != null && cls.equals(other)) {
                return true;
            }
        }

        return false;
    }

    public String getPackageName() {
        int index = this.name.lastIndexOf(File.separator);
        if(index > 0) {
            return this.name.substring(0, index);
        }
        return null;
    }

    // 新建对象
    public XObject newObject() {
        return new XObject(this);
    }

    // 是否实现了接口 iface
    public boolean isImplements(XClass iface) {
        for(XClass clz = this; clz != null; clz = clz.getSuperClass()) {
            List<XClass> ifaces = clz.getInterfaces();
            for(XClass i : ifaces) {
                if(iface.equals(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSubInterfaceOf(XClass iface) {
        for(XClass superInterface : getInterfaces()) {
            if(superInterface.equals(iface) || superInterface.isSubInterfaceOf(iface)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAssignableFrom(XClass other) {
        XClass s = other;
        XClass t = this;

        if (s.equals(t)) {
            return true;
        }

        // s is class
        if(!t.isInterface()) {
            // t is not interface
            return s.isSubClassOf(t);
        } else {
            // t is interface
            return s.isImplements(t);
        }

    }

    // 获取主类 main 方法
    public XMethod getMainMethod() {
        return this.getStaticMethod("main", "([Ljava/lang/String;)V");
    }

    // 通过方法名和描述非获取静态方法
    public XMethod getStaticMethod(String name, String descriptor) {
        for (XMethod method : this.getMethods()) {
            if (method.isStatic() && method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                return method;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
    }

    public XClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(XClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public XClass getSuperClass() {
        return superClass;
    }

    public void setSuperClass(XClass superClass) {
        this.superClass = superClass;
    }

    public List<String> getInterfaceNames() {
        return interfaceNames;
    }

    public void setInterfaceNames(List<String> interfaceNames) {
        this.interfaceNames = interfaceNames;
    }

    public List<XClass> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<XClass> interfaces) {
        this.interfaces = interfaces;
    }

    public int getInstanceSlotCount() {
        return instanceSlotCount;
    }

    public void setInstanceSlotCount(int instanceSlotCount) {
        this.instanceSlotCount = instanceSlotCount;
    }

    public int getStaticSlotCount() {
        return staticSlotCount;
    }

    public void setStaticSlotCount(int staticSlotCount) {
        this.staticSlotCount = staticSlotCount;
    }

    public Slots getStaticVars() {
        return staticVars;
    }

    public void setStaticVars(Slots staticVars) {
        this.staticVars = staticVars;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    public RuntimeConstantPool getRcp() {
        return rcp;
    }

    public void setRcp(RuntimeConstantPool rcp) {
        this.rcp = rcp;
    }

    public List<XField> getFields() {
        return fields;
    }

    public void setFields(List<XField> fields) {
        this.fields = fields;
    }

    public List<XMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<XMethod> methods) {
        this.methods = methods;
    }
}
