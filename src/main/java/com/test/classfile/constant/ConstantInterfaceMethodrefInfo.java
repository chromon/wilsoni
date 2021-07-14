package com.test.classfile.constant;

import com.test.classfile.ConstantPool;

/*
CONSTANT_InterfaceMethodref_info {
	u1 tag;
	u2 class_index;
	u2 name_and_type_index;
}
 */

// 接口方法符号引用
public class ConstantInterfaceMethodrefInfo extends ConstantMemberrefInfo {
    public ConstantInterfaceMethodrefInfo(ConstantPool cp) {
        super(cp);
    }
}
