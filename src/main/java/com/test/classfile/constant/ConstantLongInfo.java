package com.test.classfile.constant;

/*
CONSTANT_Long_info {
	u1 tag;
	u4 high_bytes;
	u4 low_bytes;
}
 */

import com.test.classfile.ClassReader;

// CONSTANT_Long_info使用 8 字节存储整数常量
public class ConstantLongInfo implements ConstantInfo {

    private long value;

    @Override
    public void readInfo(ClassReader classReader) {
        this.value = classReader.readLong();
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
