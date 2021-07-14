package com.test.classfile;

// Java 虚拟机规范描述的 class 文件格式结构体
/*
ClassFile {
    u4             magic;
    u2             minor_version;
    u2             major_version;
    u2             constant_pool_count;
    cp_info        constant_pool[constant_pool_count-1];
    u2             access_flags;
    u2             this_class;
    u2             super_class;
    u2             interfaces_count;
    u2             interfaces[interfaces_count];
    u2             fields_count;
    field_info     fields[fields_count];
    u2             methods_count;
    method_info    methods[methods_count];
    u2             attributes_count;
    attribute_info attributes[attributes_count];
}
*/

import com.test.classfile.attribute.AttributeInfo;
import com.test.classfile.attribute.AttributeInfoManager;
import com.test.classfile.constant.ConstantDoubleInfo;
import com.test.classfile.constant.ConstantInfo;
import com.test.classfile.constant.ConstantInfoManager;
import com.test.classfile.constant.ConstantLongInfo;

import java.util.ArrayList;
import java.util.List;

// class 文件结构，包含 Java 虚拟机规范定义的 class 文件格式
public class ClassFile {

    // u4：魔数，识别 class 文件格式
    private String magicNumber;
    // u2：副版本号
    private int minorVersion;
    // u2：主版本号
    private int majorVersion;
    // u2：常量池计数器
    private int constantPoolCount;
    // 常量池表
    private ConstantPool constantPool;
    // u2：访问标识，指出 class 文件定义的是类还是接口，访问级别是 public 还是 private 等
    private int accessFlag;
    // u2：当前类索引
    private int thisClass;
    // u2：父类索引
    private int superClass;
    // u2：接口计数器
    private int interfaceCount;
    // 接口索引集合
    private List<Integer> interfaces;
    // 字段表，存储字段信息
    private List<MemberInfo> fields;
    // 方法表, 存储方法信息
    private List<MemberInfo> methods;
    // 属性表
    private List<AttributeInfo> attributes;

    // 依次调用其他方法解析 class 文件
    public void read(ClassReader reader) {
        this.readAndCheckMagic(reader);
        this.readAndCheckVersion(reader);
        this.constantPool = this.readConstantPool(reader);
        this.accessFlag = reader.readU2();
        this.thisClass = reader.readU2();
        this.superClass = reader.readU2();
        this.interfaceCount = reader.readU2();
        this.interfaces = reader.readU2s(this.interfaceCount);
        this.fields = this.readMembers(reader);
        this.methods = this.readMembers(reader);
        this.attributes = new AttributeInfoManager().readAttributes(reader, this.constantPool);
    }

    // 读取魔数 0xCAFEBABE
    private void readAndCheckMagic(ClassReader reader) {
        this.magicNumber = reader.readU4();
    }

    // 读取版本号
    private void readAndCheckVersion(ClassReader reader) {
        this.minorVersion = reader.readU2();
        this.majorVersion = reader.readU2();

        // JDK 1.2 ~ 12
        if (this.majorVersion >= 46 && this.majorVersion <= 56) {
            if (this.minorVersion != 0) {
                throw new RuntimeException("java.lang.UnsupportedClassVersionError");
            }
        }
    }

    // 获取完整常量池信息
    private ConstantPool readConstantPool(ClassReader reader) {
        int cpCount = reader.readU2();
        this.constantPoolCount = cpCount;

        ConstantPool cp = new ConstantPool();
        ConstantInfoManager cim = new ConstantInfoManager();

        for (int i = 1; i < cpCount; i++) {
            // System.out.print(i + ":");
            ConstantInfo cpi = cim.readConstantInfo(reader, cp);
            if (cpi instanceof ConstantLongInfo || cpi instanceof ConstantDoubleInfo) {
                // CONSTANT_Long_info 和 CONSTANT_Double_info 各占两个位置
                i++;
                cp.constantInfos.add(cpi);
                cp.constantInfos.add(null);
            } else {
                cp.constantInfos.add(cpi);
            }
        }
        return cp;
    }

    // 获取字段表信息
    private List<MemberInfo> readMembers(ClassReader reader) {
        // 字段计数器
        int memberCount = reader.readU2();
        List<MemberInfo> list = new ArrayList<>();
        for (int i = 0; i < memberCount; i++) {
            list.add(new MemberInfo(reader, this.constantPool));
        }
        return list;
    }

    // 读取访问标识
    // 类访问标志是一个 16 位的 bitmask，指出 class 文件定义的是类还是接口，
    // 访问级别是 public 还是 private 等
    public int getAccessFlag() {
        return accessFlag;
    }

    // 获取副版本号
    public int getMinorVersion() {
        return minorVersion;
    }

    // 获取主版本号
    public int getMajorVersion() {
        return majorVersion;
    }

    // 获取常量池对象
    public ConstantPool getConstantPool() {
        return constantPool;
    }

    // 获取字段表
    public List<MemberInfo> getFields() {
        return fields;
    }

    // 获取方法表
    public List<MemberInfo> getMethods() {
        return methods;
    }

    // 通过类索引从常量池中查找类名
    public String getClassName() {
        return this.constantPool.getClassName(thisClass);
    }

    // 从常量池中查找父类名（类似于完全限定名，但是将点换成了斜线）
    public String getSuperClassName() {
        if (this.superClass > 0) {
            // 除 Object 类之外，其他类都有父类，所以 superClass 只在 Object.class 中是 0
            // 在其他 class 文件中必须是有效的常量池索引
            return this.constantPool.getClassName(superClass);
        }
        // 只有 java.lang.Object，没有其他父类
        return "";
    }

    // 从常量池中查找接口名
    public List<String> getInterfaceNames() {
        List<String> interfaceNames = new ArrayList<>();
        for (int i : this.interfaces) {
            interfaceNames.add(this.constantPool.getClassName(i));
        }
        return interfaceNames;
    }

    public int getConstantPoolCount() {
        return constantPoolCount;
    }

    public int getInterfaceCount() {
        return interfaceCount;
    }

    // 打印 class 文件信息
    private void printClassInfo() {
        System.out.println("version: " + this.getMajorVersion() + "." + this.getMinorVersion());
        System.out.println("constants count: " + this.getConstantPoolCount());
        System.out.println("access flags: 0x" + Integer.toHexString(this.getAccessFlag())
                + " (dec: " + this.getAccessFlag() + ")");
        System.out.println("this class: " + this.getClassName());
        System.out.println("super class: " + this.getSuperClassName());
        System.out.println("interfaces: " + this.getInterfaceCount());
        List<String> interfaceNames = this.getInterfaceNames();
        System.out.println("interfaces name:");
        for (int i = 0; i < this.getInterfaceCount(); i++) {
            System.out.println("\t" + interfaceNames.get(i));
        }
        System.out.println("fields count: " + this.getFields().size());
        System.out.println("fields: ");
        for (int i = 0; i < this.getFields().size(); i++) {
            System.out.println("\t" + this.getFields().get(i).getName());
        }
        System.out.println("methods count: " + this.getMethods().size());
        for (int i = 0; i < this.getMethods().size(); i++) {
            System.out.println("\t" + this.getMethods().get(i).getName());
        }
    }
}
