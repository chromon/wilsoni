package com.test.instructions.constants.ldc;

import com.test.dataarea.Frame;
import com.test.instructions.base.Index16Instruction;

// ldc 系列指令从运行时常量池中加载常量值，并把它推入操作数栈。
// ldc 系列指令属于常量类指令，共 3 条。
// 其中 ldc 和 ldc_w 指令用于加载 int、float 和字符串常量，
// java.lang.Class 实例或者 MethodType 和 MethodHandle 实例。
// ldc2_w 指令用于加载 long 和 double 常量。ldc 和 ldc_w 指令的区别仅在于操作数的宽度
public class LDCW extends Index16Instruction {
    @Override
    public void Execute(Frame frame) {
        LDCManager.ldc(frame, super.getIndex());
    }
}
