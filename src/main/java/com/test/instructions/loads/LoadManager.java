package com.test.instructions.loads;

import com.test.dataarea.Frame;

// 加载指令工具类
public class LoadManager {

    // 从局部变量表中获取变量 int 型变量，压入操作数栈顶
    public static void iload(Frame frame, int index) {
        int value = frame.getLocalVars().getInt(index);
        frame.getOperandStack().pushInt(value);
    }

    // 从局部变量表获取 float 变量，然后推入操作数栈顶
    public static void fload(Frame frame, int index) {
        float val = frame.getLocalVars().getFloat(index);
        frame.getOperandStack().pushFloat(val);
    }

    // 从局部变量表中获取变量 double 型变量，压入操作数栈顶
    public static void dload(Frame frame, int index) {
        double val = frame.getLocalVars().getDouble(index);
        frame.getOperandStack().pushDouble(val);
    }

    // 从局部变量表获取 long 变量，然后推入操作数栈顶
    public static void lload(Frame frame, int index) {
        long val = frame.getLocalVars().getLong(index);
        frame.getOperandStack().pushLong(val);
    }

    // 从局部变量表获取引用变量，然后推入操作数栈顶
    public static void aload(Frame frame, int index) {
        Object obj = frame.getLocalVars().getRef(index);
        frame.getOperandStack().pushRef(obj);
    }
}
