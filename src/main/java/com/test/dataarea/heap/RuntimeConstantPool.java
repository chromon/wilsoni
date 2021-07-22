package com.test.dataarea.heap;

import com.test.classfile.ConstantPool;
import com.test.classfile.constant.*;

import java.util.ArrayList;
import java.util.List;

// 运行时常量池主要存放两类信息：字面量（literal）和符号引用（symbolic reference）
// 字面量包括整数、浮点数和字符串字面量；
// 符号引用包括类符号引用、字段符号引用、方法符号引用和接口方法符号引用。
public class RuntimeConstantPool {

    // 类对象
    private XClass clazz;
    // 常量列表
    private List<RuntimeConstantInfo> consts;

    // 将 class 字节码文件中的常量池（ConstantPool -> ConstantInfo）
    // 转换成运行时常量池（RuntimeConstantPool -> RuntimeConstantInfo）
    // 即核心逻辑：ConstantInfo[] 转为 RuntimeConstantInfo
    public RuntimeConstantPool(XClass clazz, ConstantPool cp) {
        // class 字节码文件中常量池大小
        int cpCount = cp.constantInfos.size();
        // 创建运行时常量池常量数组
        this.consts = new ArrayList<>();
        this.clazz = clazz;

        this.consts.add(null);

        for (int i = 1; i < cpCount; i++) {
            // 字节码常量池中常量信息
            ConstantInfo cpInfo = cp.constantInfos.get(i);
            if (cpInfo instanceof ConstantIntegerInfo) {
                // int 常量信息
                ConstantIntegerInfo info = (ConstantIntegerInfo) cpInfo;
                RuntimeConstantInfo rcInfo = new RuntimeConstantInfo();
                rcInfo.setValue(info.getValue());
                this.consts.add(rcInfo);
            } else if (cpInfo instanceof ConstantFloatInfo) {
                // float 常量信息
                ConstantFloatInfo info = (ConstantFloatInfo) cpInfo;
                RuntimeConstantInfo rcInfo = new RuntimeConstantInfo();
                rcInfo.setValue(info.getValue());
                this.consts.add(rcInfo);
            } else if (cpInfo instanceof ConstantLongInfo) {
                // long 常量信息
                // 注：long 常量占两个槽位
                ConstantLongInfo info = (ConstantLongInfo) cpInfo;
                RuntimeConstantInfo rcInfo = new RuntimeConstantInfo();
                rcInfo.setValue(info.getValue());
                this.consts.add(rcInfo);
                this.consts.add(null);
            } else if (cpInfo instanceof ConstantDoubleInfo) {
                // double 常量信息
                // 注：double 常量占两个槽位
                ConstantDoubleInfo info = (ConstantDoubleInfo) cpInfo;
                RuntimeConstantInfo rcInfo = new RuntimeConstantInfo();
                rcInfo.setValue(info.getValue());
                this.consts.add(rcInfo);
                this.consts.add(null);
            } else if (cpInfo instanceof ConstantStringInfo) {
                // 字符串常量信息
                ConstantStringInfo info = (ConstantStringInfo) cpInfo;
                // class 文件字节码常量索引
                int index = info.getStringIndex();
                String str = cp.getUtf8(index);
                if (str == null) {
                    str = "";
                }
                RuntimeConstantInfo rcInfo = new RuntimeConstantInfo();
                rcInfo.setValue(str);
                this.consts.add(rcInfo);
            } else if (cpInfo instanceof ConstantClassInfo) {
                // 类符号引用
                ConstantClassInfo info = (ConstantClassInfo) cpInfo;
                ClassRef ref = new ClassRef(this, info);
                RuntimeConstantInfo rcInfo = new RuntimeConstantInfo();
                rcInfo.setValue(ref);
                this.consts.add(rcInfo);
            } else if (cpInfo instanceof ConstantFieldrefInfo) {
                // 字段符号引用
                ConstantFieldrefInfo info = (ConstantFieldrefInfo) cpInfo;
                FieldRef ref = new FieldRef(this, info);
                RuntimeConstantInfo rcInfo = new RuntimeConstantInfo();
                rcInfo.setValue(ref);
                this.consts.add(rcInfo);
            } else if (cpInfo instanceof ConstantMethodrefInfo) {
                // 方法符号引用
                ConstantMethodrefInfo info = (ConstantMethodrefInfo) cpInfo;
                MethodRef ref = new MethodRef(this, info);
                RuntimeConstantInfo rcInfo = new RuntimeConstantInfo();
                rcInfo.setValue(ref);
                this.consts.add(rcInfo);
            } else if (cpInfo instanceof ConstantInterfaceMethodrefInfo) {
                // 接口方法符号引用
                ConstantInterfaceMethodrefInfo info = (ConstantInterfaceMethodrefInfo) cpInfo;
                InterfaceMethodRef ref = new InterfaceMethodRef(this, info);
                RuntimeConstantInfo rcInfo = new RuntimeConstantInfo();
                rcInfo.setValue(ref);
                this.consts.add(rcInfo);
            } else {
                // TODO
            }
        }
    }

    public XClass getClazz() {
        return clazz;
    }

    public RuntimeConstantInfo getConstantInfo(int index) {
        if (this.consts.get(index) != null) {
            return this.consts.get(index);
        }
        throw new RuntimeException("no constants at index " + index);
    }
}
