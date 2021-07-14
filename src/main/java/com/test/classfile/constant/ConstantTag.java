package com.test.classfile.constant;

// 常量是 Java 虚拟机规范严格规定的，共 14 种
// 定义 tag 常量值，用于区分常量类型，长度 u1
public class ConstantTag {
    // 表示类或接口的符号引用
    public static final int CONSTANT_Class = 7;
    // 字段符号引用类型常量
    public static final int CONSTANT_Fieldref = 9;
    // 非接口方法符号引用类型常量
    public static final int CONSTANT_Methodref = 10;
    // 接口方法符号引用类型常量
    public static final int CONSTANT_InterfaceMethodref = 11;
    // 表示 java.lang.String 字面量
    public static final int CONSTANT_String = 8;
    // 整数类型常量
    public static final int CONSTANT_Integer = 3;
    // 单精度浮点数类型常量
    public static final int CONSTANT_Float = 4;
    // 长整形类型常量
    public static final int CONSTANT_Long = 5;
    // 双精度浮点数类型常量
    public static final int CONSTANT_Double = 6;
    // 字段或方法的名称和描述符
    // 与 CONSTANT_Class 可以唯一确定一个字段或方法
    public static final int CONSTANT_NameAndType = 12;
    // MUTF-8 编码字符串类型常量
    public static final int CONSTANT_Utf8 = 1;
    // 以下三个常量是 JDK 7 之后添加到 class 文件中的，目的是支持 invokedynamic 指令
    public static final int CONSTANT_MethodHandle = 15;
    public static final int CONSTANT_MethodType = 16;
    public static final int CONSTANT_InvokeDynamic = 18;
}
