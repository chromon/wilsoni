package com.test.classfile.attribute;

// 局部变量表实体
public class LocalVariableTableEntry {
    // 变量在字节码中的生命周期起始的偏移位置
    private int startPC;
    // startPC + length 表示变量在字节码中的生命周期结束的偏移位置
    private int length;
    // 变量名称索引
    private int nameIndex;
    // 局部变量类型描述
    private int descriptorIndex;
    // 变量在局部变量表中的槽位索引（槽位可复用）
    private int index;

    public LocalVariableTableEntry(int startPC, int length,
                                   int nameIndex, int descriptorIndex,
                                   int index) {
        this.startPC = startPC;
        this.length = length;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.index = index;
    }

    public int getStartPC() {
        return startPC;
    }

    public int getLength() {
        return length;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    public int getIndex() {
        return index;
    }
}
