package com.test.classfile.constant;

import com.test.classfile.ClassReader;
import com.test.classfile.ConstantPool;

/*
CONSTANT_Class_info {
	u1 tag;
	u2 name_index;
}
 */

// CONSTANT_Class_info 常量表示类或者接口的符号引用
public class ConstantClassInfo implements ConstantInfo {

    private int nameIndex;
    private ConstantPool cp;

    public ConstantClassInfo(ConstantPool cp) {
        this.cp = cp;
    }

    // 读取类或者接口的符号引用
    @Override
    public void readInfo(ClassReader classReader) {
        this.nameIndex = classReader.readU2();
    }

    // 按照类或者接口的符号引用在常量池中查找名字
    public String getName() {
        return this.cp.getUtf8(this.nameIndex);
    }

    public int getNameIndex() {
        return nameIndex;
    }
}
