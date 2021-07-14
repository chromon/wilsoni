package com.test.classfile.constant;

import com.test.classfile.ClassReader;

/*
CONSTANT_Integer_info {
	u1 tag;
	u4 bytes;
}
*/

// CONSTANT_Integer_info 使用 4 字节存储整数常量
// 但实际上比 int 更小的 boolean、byte、short 和 char 类型的常量
// 也放在 CONSTANT_Integer_info 中
public class ConstantIntegerInfo implements ConstantInfo {

    private int value;

    @Override
    public void readInfo(ClassReader classReader) {
        this.value = classReader.readU4Int();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
