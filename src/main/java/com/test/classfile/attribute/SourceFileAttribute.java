package com.test.classfile.attribute;

import com.test.classfile.ClassReader;
import com.test.classfile.ConstantPool;

// SourceFile 是可选定长属性，只会出现在 ClassFile 结构中，用于指出源文件名
/*
SourceFile_attribute {
	u2 attribute_name_index;
	// attribute_length 的值必须是2
	u4 attribute_length;
	u2 sourcefile_index;
}
 */
public class SourceFileAttribute implements AttributeInfo {

    private ConstantPool cp;
    // 常量池索引，指向 CONSTANT_Utf8_info 常量
    private int sourceFileIndex;

    public SourceFileAttribute(ConstantPool cp) {
        this.cp = cp;
    }

    // 读取属性索引
    @Override
    public void readInfo(ClassReader classReader) {
        this.sourceFileIndex = classReader.readU2();
    }

    // 获取文件名
    public String getFileName() {
        return this.cp.getUtf8(this.sourceFileIndex);
    }
}
