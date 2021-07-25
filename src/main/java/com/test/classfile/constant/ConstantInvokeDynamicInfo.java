package com.test.classfile.constant;

import com.test.classfile.ClassReader;

// 18 表示一个动态方法调用点
public class ConstantInvokeDynamicInfo implements ConstantInfo{

    private int bootstrapMethodAttrIndex;
    private int nameAndTypeIndex;

    @Override
    public void readInfo(ClassReader classReader) {
        this.bootstrapMethodAttrIndex = classReader.readU2();
        this.nameAndTypeIndex = classReader.readU2();
    }

    public int getBootstrapMethodAttrIndex() {
        return bootstrapMethodAttrIndex;
    }

    public void setBootstrapMethodAttrIndex(int bootstrapMethodAttrIndex) {
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    public void setNameAndTypeIndex(int nameAndTypeIndex) {
        this.nameAndTypeIndex = nameAndTypeIndex;
    }
}
