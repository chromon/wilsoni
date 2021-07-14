package com.test.classfile.constant;

import com.test.classfile.ClassReader;

/*
CONSTANT_Float_info {
	u1 tag;
	u4 bytes;
}
 */

// 读取 float 类型数据
public class ConstantFloatInfo implements ConstantInfo {

    private float value;

    @Override
    public void readInfo(ClassReader classReader) {
        this.value = classReader.readFloat();
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}