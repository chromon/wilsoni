package com.test.classfile.attribute;

import com.test.classfile.ClassReader;

// Java虚拟机规范使用属性名来区别不同的属性
// 属性数据放在属性名之后的 u1 表中，这样 Java 虚拟机实现就可以跳过无法识别的属性

/*
attribute_info {
	u2 attribute_name_index;
	u4 attribute_length;
	u1 info[attribute_length];
}
 */

// 属性数据结构
public interface AttributeInfo {
    // 读取属性信息，由具体属性表结构实现
    public void readInfo(ClassReader classReader);
}
