package com.test.instructions.comparisons.fcmp;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;
import com.test.instructions.comparisons.CmpManager;

// 比较指令可以分为两类：
// 一类将比较结果推入操作数栈顶，一类根据比较结果跳转。
// 比较指令是编译器实现 if-else、for、while 等语句的基石

// 由于浮点数计算有可能产生NaN（Not a Number）值，所以比较两个浮点数时，
// 除了大于、等于、小于之外，还有第 4 种结果：无法比较。
// fcmpg和fcmpl指令的区别就在于对第 4 种结果的定义。
// 当两个 float 变量中至少有一个是 NaN 时，用 fcmpg 指令比较的结果是 1，
// 而用 fcmpl 指令比较的结果是 -1。
public class FCmpL extends NoOperandsInstruction {
    // 当两个 float 变量中至少有一个是 NaN 时，
    // 而用 fcmpl 指令比较的结果是 -1
    @Override
    public void Execute(Frame frame) {
        CmpManager.fcmp(frame, false);
    }
}
