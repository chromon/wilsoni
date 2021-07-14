package com.test.classfile.constant;

/*
CONSTANT_NameAndType_info {
	u1 tag;
	u2 name_index;
	u2 descriptor_index;
}
 */

// CONSTANT_NameAndType_info 给出字段或方法的名称和描述符
// CONSTANT_Class_info 和 CONSTANT_NameAndType_info 加在可以唯一确定一个字段或者方法

// Java 虚拟机规范定义了一种简单的语法来描述字段和方法，可以根据下面的规则生成描述符
// 1. 类型描述符。
// 	① 基本类型 byte、short、char、int、long、float 和 double 的描述符是单个字母，
// 	分别对应 B、S、C、I、J、F 和 D。注意，long 的描述符是 J 而不是L
// 	② 引用类型的描述符是 L+ 类的完全限定名 +分号
// 	③ 数组类型的描述符是 [+ 数组元素类型描述符
// 2. 字段描述符就是字段类型的描述符。
// 3. 方法描述符是（分号分隔的参数类型描述符）+ 返回值类型描述符，其中 void 返回值由单个字母 V 表示

import com.test.classfile.ClassReader;

// 字段或方法名由 name_index 给出，字段或方法的描述符由 descriptor_index 给出
// name_index 和 descriptor_index都是常量池索引，指向 CONSTANT_Utf8_info 常量
public class ConstantNameAndTypeInfo implements ConstantInfo {

    private int nameIndex;
    private int descriptorIndex;

    @Override
    public void readInfo(ClassReader classReader) {
        this.nameIndex = classReader.readU2();
        this.descriptorIndex = classReader.readU2();
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }
}
