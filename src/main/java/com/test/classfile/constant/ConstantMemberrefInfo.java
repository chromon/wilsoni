package com.test.classfile.constant;

// CONSTANT_Fieldref_info 表示字段符号引用
// CONSTANT_Methodref_info 表示普通（非接口）方法符号引用
// CONSTANT_InterfaceMethodref_info 表示接口方法符号引用

// 以上三种结构完全相同，例如 CONSTANT_Fieldref_info 如下
/*
CONSTANT_Fieldref_info {
	u1 tag;
	u2 class_index;
	u2 name_and_type_index;
}
 */

import com.test.classfile.ClassReader;
import com.test.classfile.ConstantPool;

// 字段和方法的常用信息抽象
public class ConstantMemberrefInfo implements ConstantInfo {

    public ConstantPool cp;
    // 常量池索引指向 CONSTANT_Class_info
    private int classIndex;
    // 常量池索引指向 CONSTANT_NameAndType_info
    private int nameAndTypeIndex;

    public ConstantMemberrefInfo(ConstantPool cp) {
        this.cp = cp;
    }

    // 分别从常量池中读取索引数据
    @Override
    public void readInfo(ClassReader classReader) {
        this.classIndex = classReader.readU2();
        this.nameAndTypeIndex = classReader.readU2();
    }

    // 由索引查询类名数据
    public String getClassName() {
        return this.cp.getClassName(this.classIndex);
    }

    // 获取从常量池查找字段或方法的名字和描述符
    public String[] getNameAndDescriptor() {
        return this.cp.getNameAndType(this.nameAndTypeIndex);
    }
}
