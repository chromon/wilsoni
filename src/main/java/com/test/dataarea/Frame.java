package com.test.dataarea;

// 虚拟机栈栈帧（包括局部变量表、操作数栈、方法返回值和动态链接）
public class Frame {

    // 局部变量表指针
    private LocalVars localVars;
    // 操作数栈指针
    private OperandStack operandStack;

    public Frame(int maxLocals, int maxStack) {
        this.localVars = new LocalVars(maxLocals);
        this.operandStack = new OperandStack(maxStack);
    }

    public LocalVars getLocalVars() {
        return localVars;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }
}
