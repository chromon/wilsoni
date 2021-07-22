package com.test.dataarea.heap;

import com.test.classfile.constant.ConstantMemberrefInfo;

// 存放字段和方法符号引用共有的信息
public class MemberRef extends SymRef {

    private String name;
    private String descriptor;

    // 从 class 字节码文件内存储的字段或方法常量中提取数据
    public void copyMemberRefInfo(ConstantMemberrefInfo refInfo) {
        this.setClassName(refInfo.getClassName());
        String[] nameAndType = refInfo.getNameAndDescriptor();
        this.name = nameAndType[0];
        this.descriptor = nameAndType[1];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }
}
