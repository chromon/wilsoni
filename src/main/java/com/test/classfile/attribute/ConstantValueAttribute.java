package com.test.classfile.attribute;

import com.test.classfile.ClassReader;

// ConstantValue 是定长属性，只会出现在 field_info 结构中，用于表示常量表达式的值
/*
ConstantValue_attribute {
	u2 attribute_name_index;
	// 值必须是 2
	u4 attribute_length;
	u2 constantvalue_index;
}
 */

public class ConstantValueAttribute implements AttributeInfo {

    // 常量池索引，但具体指向哪种常量因字段类型而异
    // 如：long 字段类型对应常量类型 CONSTANT_Long_info
    private int constantValueIndex;

    @Override
    public void readInfo(ClassReader classReader) {
        this.constantValueIndex = classReader.readU2();
    }

    // 获取常量表达式的索引值
    public int getConstantValueIndex() {
        return constantValueIndex;
    }
}
