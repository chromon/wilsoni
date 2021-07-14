package com.test.classfile.constant;

import com.test.classfile.ClassReader;
import com.test.classfile.ConstantPool;

public class ConstantInfoManager {

    // 读取常量信息
    public ConstantInfo readConstantInfo(ClassReader classReader, ConstantPool cp) {
        int tag = classReader.readU1();
        // System.out.println(tag);
        ConstantInfo cpi = this.newConstantInfo(tag, cp);
        cpi.readInfo(classReader);
        return cpi;
    }

    // 根据 tag 创建具体的常量对象
    private ConstantInfo newConstantInfo(int tag, ConstantPool cp) {
        switch (tag) {
            case ConstantTag.CONSTANT_Integer:
                // 3 整型字面量
                return new ConstantIntegerInfo();
            case ConstantTag.CONSTANT_Float:
                // 4 浮点型字面量
                return new ConstantFloatInfo();
            case ConstantTag.CONSTANT_Long:
                // 5 长整型字面量
                return new ConstantLongInfo();
            case ConstantTag.CONSTANT_Double:
                // 6 双精度浮点型字面量
                return new ConstantDoubleInfo();
            case ConstantTag.CONSTANT_Utf8:
                // 1 UTF-8 编码的字符串
                return new ConstantUtf8Info();
            case ConstantTag.CONSTANT_String:
                // 8 字符串类型字面量
                return new ConstantStringInfo(cp);
            case ConstantTag.CONSTANT_Class:
                // 7 类或接口的符号引用
                return new ConstantClassInfo(cp);
            case ConstantTag.CONSTANT_Fieldref:
                // 9 字段的符号引用
                return new ConstantFieldrefInfo(cp);
            case ConstantTag.CONSTANT_Methodref:
                // 10 类中方法的符号引用
                return new ConstantMethodrefInfo(cp);
            case ConstantTag.CONSTANT_InterfaceMethodref:
                // 11 接口中方法的符号引用
                return new ConstantInterfaceMethodrefInfo(cp);
            case ConstantTag.CONSTANT_NameAndType:
                // 12 字段或方法的符号引用
                return new ConstantNameAndTypeInfo();
//            case ConstantTag.CONSTANT_MethodType:
//                // 16 标志方法类型
//                return new ConstantMethodTypeInfo();
//            case ConstantTag.CONSTANT_MethodHandle:
//                // 15 表示方法句柄
//                return new ConstantMethodHandleInfo();
//            case ConstantTag.CONSTANT_InvokeDynamic:
//                // 18 表示一个动态方法调用点
//                return new ConstantInvokeDynamicInfo();
            default:
                throw new RuntimeException("java.lang.ClassFormatError: constant pool tag " + tag);
        }
    }
}
