package com.test.classfile.constant;

import com.test.classfile.ClassReader;

// 15 表示方法句柄
public class ConstantMethodHandleInfo implements ConstantInfo {

    private int referenceKind;
    private int referenceIndex;

    @Override
    public void readInfo(ClassReader classReader) {
        this.referenceKind = classReader.readByte();
        this.referenceIndex = classReader.readU2();
    }

    public int getReferenceKind() {
        return referenceKind;
    }

    public void setReferenceKind(int referenceKind) {
        this.referenceKind = referenceKind;
    }

    public int getReferenceIndex() {
        return referenceIndex;
    }

    public void setReferenceIndex(int referenceIndex) {
        this.referenceIndex = referenceIndex;
    }
}
