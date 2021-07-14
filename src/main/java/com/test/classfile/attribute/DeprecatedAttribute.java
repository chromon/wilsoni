package com.test.classfile.attribute;

import com.test.classfile.ClassReader;

// Deprecated 是最简单的属性，仅起标记作用，不包含任何数据
/*
Deprecated_attribute {
	u2 attribute_name_index;
	u4 attribute_length;
}
 */
public class DeprecatedAttribute implements AttributeInfo {
    @Override
    public void readInfo(ClassReader classReader) {}
}
