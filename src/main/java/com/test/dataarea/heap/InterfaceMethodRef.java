package com.test.dataarea.heap;

import com.test.classfile.constant.ConstantInterfaceMethodrefInfo;

// 接口方法符号引用
public class InterfaceMethodRef extends MemberRef {

    private XMethod method;

    public InterfaceMethodRef(RuntimeConstantPool rcp,
                              ConstantInterfaceMethodrefInfo refInfo) {
        this.setRcp(rcp);
        this.setClassName(refInfo.getClassName());
        String[] nameAndType = refInfo.getNameAndDescriptor();
        this.setName(nameAndType[0]);
        this.setDescriptor(nameAndType[1]);
    }
}
