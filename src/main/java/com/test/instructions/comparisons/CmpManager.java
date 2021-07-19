package com.test.instructions.comparisons;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;

//比较工具类
public class CmpManager {

    // 由于浮点数计算有可能产生NaN（Not a Number）值，所以比较两个浮点数时，
    // 除了大于、等于、小于之外，还有第 4 种结果：无法比较。
    // fcmpg和fcmpl指令的区别就在于对第 4 种结果的定义。
    // 当两个 float 变量中至少有一个是 NaN 时，用 fcmpg 指令比较的结果是 1，
    // 而用 fcmpl 指令比较的结果是 -1。
    public static void fcmp(Frame frame, boolean flag) {
        OperandStack stack = frame.getOperandStack();
        float f2 = stack.popFloat();
        float f1 = stack.popFloat();
        if (f1 > f2) {
            stack.pushInt(1);
        } else if (f1 == f2) {
            stack.pushInt(0);
        } else if (f1 < f2) {
            stack.pushInt(-1);
        } else if (flag) {
            stack.pushInt(1);
        } else {
            stack.pushInt(-1);
        }
    }

    // 当两个 double 变量中至少有一个是 NaN 时，用 dcmpg 指令比较的结果是 1，
    // 而用 dcmpl 指令比较的结果是 -1。
    public static void dcmp(Frame frame, boolean flag) {
        OperandStack stack = frame.getOperandStack();
        double d2 = stack.popDouble();
        double d1 = stack.popDouble();
        if (d1 > d2) {
            stack.pushInt(1);
        } else if (d1 == d2) {
            stack.pushInt(0);
        } else if (d1 < d2) {
            stack.pushInt(-1);
        } else if (flag) {
            stack.pushInt(1);
        } else {
            stack.pushInt(-1);
        }
    }
}
