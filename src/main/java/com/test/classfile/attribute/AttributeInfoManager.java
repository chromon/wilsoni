package com.test.classfile.attribute;

import com.test.classfile.ClassReader;
import com.test.classfile.ConstantPool;

import java.util.ArrayList;
import java.util.List;

/*
attribute_info {
	u2 attribute_name_index;
	u4 attribute_length;
	u1 info[attribute_length];
}
 */

public class AttributeInfoManager {
    // 读取单一属性信息
    public AttributeInfo readAttribute(ClassReader reader, ConstantPool cp) {
        // 读取属性名索引
        int attrNameIndex = reader.readU2();
        // 由属性名索引在常量池中查找属性名字符串
        String attrName = cp.getUtf8(attrNameIndex);
        // 读取属性长度
        int attrLen = reader.readU4Int();
        // 构建属性实例
        AttributeInfo attrInfo = this.newAttributeInfo(attrName, attrLen, cp);
        attrInfo.readInfo(reader);
        return attrInfo;
    }

    // 读取属性表信息
    public List<AttributeInfo> readAttributes(ClassReader reader, ConstantPool cp) {
        // 读取属性表计数器
        int attrCount = reader.readU2();
        List<AttributeInfo> attrInfoList = new ArrayList<>();
        for (int i = 0; i < attrCount; i++) {
            attrInfoList.add(this.readAttribute(reader, cp));
        }
        return attrInfoList;
    }

    // 创建具体属性实例
    // Java 虚拟机规范预定义了 23 种属性
    // 按照用途，23 种预定义属性可以分为三组：
    // 第一组属性是实现 Java 虚拟机所必需的，共有 5 种
    // 第二组属性是 Java 类库所必需的共有 12 种
    // 第三组属性主要提供给工具使用，共有 6 种。第三组属性是可选的，也就是说可以不出现在 class 文件中
    private AttributeInfo newAttributeInfo(String attrName, int attrLen, ConstantPool cp) {
        switch (attrName) {
            case "Code":
                // 变长属性，只存在于 method_info 结构中，用于存放字节码等方法相关信息
                return new CodeAttribute(cp);
            case "ConstantValue":
                // 定长属性，只会出现在 field_info 中，用于表示常量表达式的值
                return new ConstantValueAttribute();
            case "Deprecated":
                // 属性用于指出类、接口、字段或方法已不建议使用，仅起到标记作用，不包含任何数据
                return new DeprecatedAttribute();
            case "Exceptions":
                // 变长属性，记录方法抛出的异常表
                return new ExceptionsAttribute();
            case "LineNumberTable":
                // 异常堆栈中显示方法行号
                return new LineNumberTableAttribute();
            case "LocalVariableTable":
                // 该属性表中存放方法的局部变量信息
                return new LocalVariableTableAttribute();
            case "SourceFile":
                // 可选定长属性，只会出现在 ClassFile 结构中，用于指出源文件名
                return new SourceFileAttribute(cp);
            case "Synthetic":
                // 属性用来标记源文件中不存在、由编译器生成的类成员
                return new SyntheticAttribute();
            default:
                // 无法解析的属性
                return new UnparsedAttribute(attrName, attrLen, null);
        }
    }
}
