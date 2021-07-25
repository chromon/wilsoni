package com.test.dataarea.heap;

import com.test.classfile.ClassFile;
import com.test.classfile.ClassParse;
import com.test.classfile.constant.ConstantIntegerInfo;
import com.test.classpath.Classpath;
import com.test.dataarea.Slot;

import java.time.temporal.ValueRange;
import java.util.*;

/*
class names:
    - primitive types: boolean, byte, int ...
    - primitive arrays: [Z, [B, [I ...
    - non-array classes: java/lang/Object ...
    - array classes: [Ljava/lang/Object; ...
*/

// 类加载器
public class XClassLoader {

    // 依赖 Classpath 来搜索和读取 class 文件
    private Classpath classpath;
    // 记录已经加载的类数据，key 是类的完全限定名
    // 方法区是一个抽象的概念，classMap 可以看做是方法区的具体实现
    private Map<String, XClass> classMap;
    private boolean verbose;

    public XClassLoader(Classpath classpath, boolean verbose) {
        this.classpath = classpath;
        this.verbose = verbose;
        this.classMap = new HashMap<>();
    }

    // 将类数据加载到方法区
    public XClass loadClass(String name) {
        XClass clazz = this.classMap.get(name);
        if (clazz != null) {
            // 类已经加载
            return clazz;
        }

        if (name.charAt(0) == '[') {
            // 加载数组类
            return this.loadArrayClass(name);
        }

        // 类还没有被加载，调用加载
        return this.loadNonArrayClass(name);
    }

    // 加载非数组类
    // 数组类与普通类有很大不同，数组类数据并不是来自 class 文件，
    // 而是由 Java 虚拟机在运行期间生成
    private XClass loadNonArrayClass(String name) {
        byte[] data = this.readClass(name);
        XClass clazz = this.defineClass(data);
        this.link(clazz);
        if (this.verbose) {
            System.out.println("class " + name + " loaded");
        }
        return clazz;
    }

    // 加载数组类
    private XClass loadArrayClass(String name) {
        XClass clazz = new XClass();
        clazz.setAccessFlags(AccessFlag.ACC_PUBLIC);
        clazz.setName(name);
        clazz.setClassLoader(this);
        // 数组类不需要初始化
        clazz.startInit();
        clazz.setSuperClass(loadClass("java/lang/Object"));
        clazz.setSuperClassName("java/lang/Object");
        clazz.setMethods(Collections.<XMethod>emptyList());

        List<XClass> interfaces = new ArrayList<>();
        interfaces.add(loadClass("java/lang/Cloneable"));
        interfaces.add(loadClass("java/io/Serializable"));
        clazz.setInterfaces(interfaces);
        clazz.setInterfaceNames(Arrays.asList("java/lang/Cloneable", "java/io/Serializable"));
        classMap.put(name, clazz);

        return clazz;
    }

    // 查找并读取 class 文件数据
    public byte[] readClass(String name) {
        byte[] data = this.classpath.readClass(name);
        return data;
    }

    // 解析 class 文件，生成虚拟机可用的类数据
    public XClass defineClass(byte[] data) {
        // 将 class 文件数据转化成 XClass
        XClass clazz = this.parseClass(data);
        clazz.setClassLoader(this);
        // Class 结构体的 superClass 字段存放父类名，是符号引用
        // 解析父类符号引用
        this.resolveSuperClass(clazz);
        // Class结构体的 interfaces 字段存放直接接口表，是符号引用
        // 解析接口表符号引用
        this.resolveInterfaces(clazz);
        // 将生成的 class 对象添加到方法区
        this.classMap.put(clazz.getName(), clazz);
        return clazz;
    }

    // 将 class 文件数据转化成 XClass 对象
    public XClass parseClass(byte[] data) {
        // 将 class 文件数据转换为 classFile 对象
        ClassFile classFile = ClassParse.parse(data);
        return new XClass(classFile);
    }

    // 解析父类符号引用
    public void resolveSuperClass(XClass clazz) {
        // 除 java.lang.Object 以外，所有的类都有且仅有一个父类
        if (!clazz.getName().equals("java/lang/Object")) {
            // 非 Object 类需要递归调用 LoadClass 加载父类
            clazz.setSuperClass(clazz.getClassLoader().loadClass(clazz.getSuperClassName()));
        }
    }

    // 解析直接接口表符号引用
    public void resolveInterfaces(XClass clazz) {
        int interfaceCount = clazz.getInterfaceNames().size();
        List<XClass> interfaces = new ArrayList<>();
        if (interfaceCount > 0) {
            for (int i = 0; i < interfaceCount; i++) {
                interfaces.add(clazz.getClassLoader().loadClass(
                                clazz.getInterfaceNames().get(i)));
            }
        }
        clazz.setInterfaces(interfaces);
    }

    // 链接：分为验证和准备两个阶段
    public void link(XClass clazz) {
        // 验证
        this.verify(clazz);
        // 准备
        this.prepare(clazz);
    }

    // 验证阶段
    private void verify(XClass clazz) {
        // TODO
    }

    // 准备阶段
    private void prepare(XClass clazz) {
        // 计算实例字段个数，并编号
        this.calcInstanceFieldSlotIds(clazz);
        // 计算静态字段的个数，并编号
        this.calcStaticFieldSlotIds(clazz);
        // 给类变量分配空间，并赋初值
        this.allocAndInitStaticVars(clazz);
    }

    // 计算实例字段个数，并编号
    private void calcInstanceFieldSlotIds(XClass clazz) {
        int slotId = 0;
        if (clazz.getSuperClass() != null) {
            slotId = clazz.getSuperClass().getInstanceSlotCount();
        }
        for (XField field: clazz.getFields()) {
            if (!field.isStatic()) {
                field.setSlotId(slotId);
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.setInstanceSlotCount(slotId);
    }

    // 计算静态字段的个数，并编号
    public void calcStaticFieldSlotIds(XClass clazz) {
        int slotId = 0;
        if (clazz.getSuperClass() != null) {
            slotId = clazz.getSuperClass().getInstanceSlotCount();
        }
        for (XField field: clazz.getFields()) {
            if (field.isStatic()) {
                field.setSlotId(slotId);
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.setStaticSlotCount(slotId);
    }

    // 给类变量分配空间，并赋初值
    private void allocAndInitStaticVars(XClass clazz) {
        clazz.setStaticVars(new Slots(clazz.getStaticSlotCount()));
        for (XField field: clazz.getFields()) {
            if (field.isStatic() && field.isFinal()) {
                this.initStaticFinalVar(clazz, field);
            }
        }
    }

    // 如果静态变量属于基本类型或 String 类型，有 final 修饰符，
    // 且它的值在编译期已知，则该值存储在 class 文件常量池中
    // 从常量池中加载常量值，然后给静态变量赋值
    private void initStaticFinalVar(XClass clazz, XField field) {
        Slots slots = clazz.getStaticVars();
        RuntimeConstantPool rcp = clazz.getRcp();
        int cpIndex = field.getConstValueIndex();
        int slotId = field.getSlotId();
        String descriptor = field.getDescriptor();

        if (cpIndex > 0) {
            if (Arrays.asList("Z", "B", "C", "S", "I").contains(descriptor)) {
                int val = (int) rcp.getConstantInfo(cpIndex).getValue();
                slots.setInt(slotId, val);
            } else if ("J".equals(descriptor)) {
                long val = (long) rcp.getConstantInfo(cpIndex).getValue();
                slots.setLong(slotId, val);
            } else if ("F".equals(descriptor)) {
                float val = (float) rcp.getConstantInfo(cpIndex).getValue();
                slots.setFloat(slotId, val);
            } else if ("D".equals(descriptor)) {
                double val = (double) rcp.getConstantInfo(cpIndex).getValue();
                slots.setDouble(slotId, val);
            } else if ("Ljava/lang/String;".equals(descriptor)) {
                String str = (String) rcp.getConstantInfo(cpIndex).getValue();
                XObject obj = StringPool.getStringObject(clazz.getClassLoader(), str);
                slots.setRef(slotId, obj);
            }
        }
    }


}
