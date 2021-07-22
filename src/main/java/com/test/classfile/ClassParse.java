package com.test.classfile;

import java.nio.ByteOrder;

// 解析 ClassFile
// 将 classData 解析为 ClassFile
public class ClassParse {
    public static ClassFile parse(byte[] classData) {
        ClassReader classReader = new ClassReader(classData, ByteOrder.BIG_ENDIAN);
        ClassFile classFile = new ClassFile();
        classFile.read(classReader);
        return classFile;
    }
}
