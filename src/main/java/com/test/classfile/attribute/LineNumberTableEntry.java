package com.test.classfile.attribute;

// 行号表实体
public class LineNumberTableEntry {
    // 字节码行号
    private int startPC;
    // Java 源代码行号
    private int lineNumber;

    public LineNumberTableEntry(int startPC, int lineNumber) {
        this.startPC = startPC;
        this.lineNumber = lineNumber;
    }

    public int getStartPC() {
        return startPC;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
