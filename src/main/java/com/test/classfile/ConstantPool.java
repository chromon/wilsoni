package com.test.classfile;

// 常量池：class 文件中的常量池
// 注：应与运行时数据区中 heap 中的常量池相区分

// 常量池里面存放着各种的常量信息，包括数字和字符串常量、类和接口名、字段和方法名等

// 常量池中的常量分为两类：
// 字面量（literal）：包括数字常量和字符串常量
// 符号引用（symbolic reference）：包括类和接口名、字段和方法信息等
// 除了字面量，其他常量都是通过索引直接或间接指向 CONSTANT_Utf8_info 常量

import com.test.classfile.constant.ConstantClassInfo;
import com.test.classfile.constant.ConstantInfo;
import com.test.classfile.constant.ConstantNameAndTypeInfo;
import com.test.classfile.constant.ConstantUtf8Info;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

// 常量池实际上也是一个表，有三点需要特别注意：
// 1. 表头给出的常量池计数器大小比实际大 1。
//    假设表头给出的值是 n，那么常量池的实际大小是 n – 1
// 2. 有效的常量池索引是 1 ~ n – 1。0 是无效索引，表示不指向任何常量
// 3. CONSTANT_Long_info 和 CONSTANT_Double_info 各占两个位置。即，如果常量池中
//    存在这两种常量，实际的常量数量比 n – 1 还要少，而且 1 ~ n – 1 的某些数也会变成无效索引
public class ConstantPool {

    public List<ConstantInfo> constantInfos;

    public ConstantPool() {
        this.constantInfos = new ArrayList<>();
        // 索引 0 是无效索引，表示不指向任何常量
        this.constantInfos.add(null);
    }

    // 按常量池索引查找常量
    public ConstantInfo getConstantInfo(int index) {
        ConstantInfo cpi = this.constantInfos.get(index);
        if (cpi != null) {
            return cpi;
        }
        throw new RuntimeException("invalid constant pool index");
    }

    // 从常量池查找字段或方法的名字和描述符
    public String[] getNameAndType(int index) {
        ConstantInfo cpi = this.constantInfos.get(index);
        if (!(cpi instanceof ConstantNameAndTypeInfo)) {
            throw new RuntimeException("constantInfo class not match, class index: " + index);
        }

        ConstantNameAndTypeInfo cnt = (ConstantNameAndTypeInfo) cpi;
        ConstantUtf8Info utf8Info = (ConstantUtf8Info) this.constantInfos.get(cnt.getNameIndex());
        String name = utf8Info.getStr();
        utf8Info = (ConstantUtf8Info) this.constantInfos.get(cnt.getDescriptorIndex());
        String type = utf8Info.getStr();
        return new String[]{name, type};
    }

    // 根据索引从常量池中查找类名
    public String getClassName(int index) {
        ConstantClassInfo classInfo = (ConstantClassInfo) this.getConstantInfo(index);
        int nameIndex = classInfo.getNameIndex();
        return this.getUtf8(nameIndex);
    }

    // 从常量池中查找 UTF-8 的字符串
    public String getUtf8(int index) {
        ConstantUtf8Info cui = (ConstantUtf8Info) this.getConstantInfo(index);
        return cui.getStr();
    }
}
