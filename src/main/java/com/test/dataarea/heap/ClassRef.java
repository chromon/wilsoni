package com.test.dataarea.heap;

import com.test.classfile.constant.ConstantClassInfo;

// 类符号引用
public class ClassRef extends SymRef {
    // 根据 class 字节码文件中存储的类常量创建 ClassRef 实例
    public ClassRef(RuntimeConstantPool rcp, ConstantClassInfo classInfo) {
        this.setRcp(rcp);
        this.setClassName(classInfo.getName());
    }
}
