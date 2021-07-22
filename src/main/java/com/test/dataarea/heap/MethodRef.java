package com.test.dataarea.heap;

import com.test.classfile.constant.ConstantMethodrefInfo;

// 方法的符号引用（非接口方法）
public class MethodRef extends MemberRef {

    private XMethod method;

    public MethodRef(RuntimeConstantPool rcp, ConstantMethodrefInfo refInfo) {
        this.setRcp(rcp);
        this.copyMemberRefInfo(refInfo);
    }
}
