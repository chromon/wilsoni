package com.test.classfile.constant;

import com.test.classfile.ClassReader;
import com.test.classfile.ConstantPool;

/*
CONSTANT_String_info {
	u1 tag;
	u2 string_index;
}
 */

// CONSTANT_String_info 常量表示 java.lang.String 字面量
// CONSTANT_String_info 本身并不存放字符串数据，只存放常量池索引，
// 这个索引指向一个 CONSTANT_Utf8_info 常量
public class ConstantStringInfo implements ConstantInfo {

    // 字符串常量所以
    private int stringIndex;
    // 常量池对象
    private ConstantPool cp;

    public ConstantStringInfo(ConstantPool cp) {
        this.cp = cp;
    }

    @Override
    public void readInfo(ClassReader classReader) {
        this.stringIndex = classReader.readU2();
    }

    public int getStringIndex() {
        return stringIndex;
    }

    public void setStringIndex(int stringIndex) {
        this.stringIndex = stringIndex;
    }

    // 获取常量索引对应的字符串
    public String getStringValue() {
        return cp.getUtf8(this.stringIndex);
    }
}
