package com.test.classfile.constant;

import com.test.classfile.ConstantPool;

/*
CONSTANT_Fieldref_info {
	u1 tag;
	u2 class_index;
	u2 name_and_type_index;
}
 */

// 字段符号引用，继承自 ConstantMemberrefInfo
public class ConstantFieldrefInfo extends ConstantMemberrefInfo {
    public ConstantFieldrefInfo(ConstantPool cp) {
        super(cp);
    }
}
