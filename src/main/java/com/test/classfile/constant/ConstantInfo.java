package com.test.classfile.constant;

/*
cp_info {
	u1 tag;
	u1 info[];
}
*/

import com.test.classfile.ClassReader;

// 常量数据结构
public interface ConstantInfo {
    // 读取常量信息，由具体常量结构实现
    public void readInfo(ClassReader classReader);
}
