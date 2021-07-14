package com.test.classfile.attribute;

import com.test.classfile.ClassReader;

// 无法解析的属性
public class UnparsedAttribute implements AttributeInfo {
    // 属性名
    private String name;
    // 属性长度
    private int length;
    // 属性信息
    private byte[] info;

    public UnparsedAttribute(String name, int length, byte[] info) {
        this.name = name;
        this.length = length;
        this.info = info;
    }

    @Override
    public void readInfo(ClassReader classReader) {
        this.info = classReader.readBytes(this.length);
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public byte[] getInfo() {
        return info;
    }
}
