package com.test.classfile.constant;

import com.test.classfile.ClassReader;

// 16 标志方法类型
public class ConstantMethodTypeInfo implements ConstantInfo{

    private int descriptorIndex;

    @Override
    public void readInfo(ClassReader classReader) {
        this.descriptorIndex = classReader.readU2();
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    public void setDescriptorIndex(int descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }
}
