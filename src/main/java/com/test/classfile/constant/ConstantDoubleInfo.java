package com.test.classfile.constant;

/*
CONSTANT_Double_info {
	u1 tag;
	u4 high_bytes;
	u4 low_bytes;
}
 */

import com.test.classfile.ClassReader;

// CONSTANT_Double_info，使用 8 字节存储 IEEE754 双精度浮点数
public class ConstantDoubleInfo implements ConstantInfo {

    private double value;

    @Override
    public void readInfo(ClassReader classReader) {
        this.value = classReader.readDouble();
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
