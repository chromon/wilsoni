package com.test.classfile.constant;

import com.test.classfile.ConstantPool;

/*
CONSTANT_Methodref_info {
	u1 tag;
	u2 class_index;
	u2 name_and_type_index;
}
 */

// 普通（非接口）方法符号引用
public class ConstantMethodrefInfo extends ConstantMemberrefInfo {
    public ConstantMethodrefInfo(ConstantPool cp) {
        super(cp);
    }
}
