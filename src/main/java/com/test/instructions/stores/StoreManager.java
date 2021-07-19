package com.test.instructions.stores;

import com.sun.jdi.Value;
import com.test.dataarea.Frame;

// 存储指令工具类
public class StoreManager {

    // 把 long 型变量从操作数栈顶弹出，然后存入局部变量表
    public static void lStore(Frame frame, int index) {
        Long val = frame.getOperandStack().popLong();
        frame.getLocalVars().setLong(index, val);
    }

    // 把 int 型变量从操作数栈顶弹出，然后存入局部变量表
    public static void iStore(Frame frame, int index) {
        int val = frame.getOperandStack().popInt();
        frame.getLocalVars().setInt(index, val);
    }

    // 把 float 型变量从操作数栈顶弹出，然后存入局部变量表
    public static void fStore(Frame frame, int index) {
        float val = frame.getOperandStack().popFloat();
        frame.getLocalVars().setFloat(index, val);
    }

    // 把 double 型变量从操作数栈顶弹出，然后存入局部变量表
    public static void dStore(Frame frame, int index) {
        double val = frame.getOperandStack().popDouble();
        frame.getLocalVars().setDouble(index, val);
    }

    // 把 引用 型变量从操作数栈顶弹出，然后存入局部变量表
    public static void aStore(Frame frame, int index) {
        Object obj = frame.getOperandStack().popRef();
        frame.getLocalVars().setRef(index, obj);
    }
}
