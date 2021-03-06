package com.test.dataarea.heap;

import com.test.classfile.ClassFile;
import com.test.dataarea.Frame;
import com.test.dataarea.XThread;

import java.io.File;
import java.util.ArrayList;
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
    // 是否初始化
    private boolean initStarted;

    public XClass() {}

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

    // 判断是否是子类，判断 S 是否是 T 的子类，实际上也就是判断 T 是否是 S 的（直接或间接）超类
    public boolean isSubClassOf(XClass other) {
        for(XClass cls = this.getSuperClass(); cls != null; cls = cls.getSuperClass()) {
            if(cls != null && cls.equals(other)) {
                return true;
            }
        }

        return false;
    }

    // 判断是否是否父类
    public boolean isSuperClassOf(XClass other) {
        return other.isSubClassOf(this);
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

    public boolean isJlObject() {
        return name.equals("java/lang/Object");
    }

    public boolean isSuperInterfaceOf(XClass iface) {
        return iface.isSubInterfaceOf(this);
    }

    public boolean isJlCloneable() {
        return name.equals("java/lang/Cloneable");
    }

    public boolean isJioSerializable() {
        return name.equals("java/io/Serializable");
    }

    public boolean isAssignableFrom(XClass other) {
        XClass s = other;
        XClass t = this;

        if(s.equals(t)){
            return true;
        }

        if(!s.isArray()){
            if(!s.isInterface()) {
                // s is class
                if(!t.isInterface()) {
                    // t is not interface
                    return s.isSubClassOf(t);
                } else {
                    // t is interface
                    return s.isImplements(t);
                }
            } else {
                // s is interface
                if(!t.isInterface()) {
                    // t is not interface
                    return t.isJlObject();
                } else {
                    // t is interface
                    return t.isSuperInterfaceOf(s);
                }
            }
        } else {
            // s is array
            if(!t.isArray()) {
                if(!t.isInterface()) {
                    // t is class
                    return t.isJlObject();
                } else {
                    // t is interface
                    return t.isJlCloneable() || t.isJioSerializable();
                }
            } else {
                // t is array
                XClass sc = s.getComponentClass();
                XClass tc = t.getComponentClass();
                return sc == tc || tc.isAssignableFrom(sc);
            }
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

    public boolean initStarted() {
        return this.initStarted;
    }

    public void startInit() {
        this.initStarted = true;
    }

    // 初始化类
    public static void initClass(XThread thread, XClass clazz) {
        clazz.startInit();
        // 执行类的初始化方法
        scheduleClinit(thread, clazz);
        initSuperClass(thread, clazz);
    }

    // 执行类的初始化方法
    private static void scheduleClinit(XThread thread, XClass clazz) {
        XMethod clinit = clazz.getClinitMethod();
        if (clinit != null) {
            // 执行 <clinit>
            Frame frame = thread.newFrame(clinit);
            thread.pushFrame(frame);
        }
    }

    private XMethod getClinitMethod() {
        return this.getStaticMethod("<clinit>", "()V");
    }

    private static void initSuperClass(XThread thread, XClass clazz) {
        if (!clazz.isInterface()) {
            XClass superClass = clazz.getSuperClass();
            if (superClass != null && !superClass.initStarted()) {
                initClass(thread, superClass);
            }
        }
    }

    public boolean isArray() {
        return this.name.charAt(0) == '[';
    }

    // 创建数组对象
    public XObject newArray(int count) {
        if (!this.isArray()) {
            throw new RuntimeException("not array class: " + this.getName());
        }

        XObject obj = new XObject(this);

        if("[Z".equals(name)) {
            obj.setData(new int[count]);
        } else if("[B".equals(name)) {
            obj.setData(new byte[count]);
        } else if("[C".equals(name)) {
            obj.setData(new char[count]);
        } else if("[S".equals(name)) {
            obj.setData(new short[count]);
        } else if("[I".equals(name)) {
            obj.setData(new int[count]);
        } else if("[J".equals(name)) {
            obj.setData(new long[count]);
        } else if("[F".equals(name)) {
            obj.setData(new float[count]);
        } else if("[D".equals(name)) {
            obj.setData(new double[count]);
        } else {
            obj.setData(new XObject[count]);
        }

        return obj;
    }

    // 返回与类对应的数组类
    public XClass getArrayClass() {
        // 根据类名得到数组类名
        String arrayClassName = ClassNameManager.getArrayClassName(this.getName());
        // 调用类加载器加载数组类
        return this.getClassLoader().loadClass(arrayClassName);
    }

    // 返回数组类的元素类型
    public XClass getComponentClass() {
        // 根据数组类名推测出数组元素类名
        String componentClassName = ClassNameManager.getComponentClassName(this.getName());
        // 用类加载器加载元素类
        return this.getClassLoader().loadClass(componentClassName);
    }

    public List<XField> getField(boolean publicOnly) {
        List<XField> fields = new ArrayList<>();
        if(publicOnly) {
            for(XClass cls = this; cls != null; cls = cls.getSuperClass()) {
                for(XField field : cls.getFields()) {
                    if(field.isPublic()) {
                        fields.add(field);
                    }
                }
            }
            return fields;
        } else {
            return getFields();
        }
    }

    public XField getField(String name, String descriptor, boolean isStatic) {
        for(XClass cls = this; cls != null; cls = cls.getSuperClass()) {
            for(XField field : cls.getFields()) {
                if(field.isStatic() == isStatic && name.equals(field.getName()) && descriptor.equals(field.getDescriptor())) {
                    return field;
                }
            }
        }
        return null;
    }
}
