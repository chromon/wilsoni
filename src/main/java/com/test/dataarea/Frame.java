package com.test.dataarea;

// 虚拟机栈栈帧（包括局部变量表、操作数栈、方法返回值和动态链接）
public class Frame {

    // 局部变量表指针
    private LocalVars localVars;
    // 操作数栈指针
    private OperandStack operandStack;
    // 线程
    private XThread xThread;
    private int nextPC;

    public Frame(XThread xThread, int maxLocals, int maxStack) {
        this.xThread = xThread;
        this.localVars = new LocalVars(maxLocals);
        this.operandStack = new OperandStack(maxStack);
    }

    public LocalVars getLocalVars() {
        return localVars;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }

    public XThread getXThread() {
        return xThread;
    }

    public void setXThread(XThread xThread) {
        this.xThread = xThread;
    }

    public int getNextPC() {
        return nextPC;
    }

    public void setNextPC(int nextPC) {
        this.nextPC = nextPC;
    }
}
