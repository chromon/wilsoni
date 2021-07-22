package com.test.dataarea;

import com.test.dataarea.heap.XMethod;

// 虚拟机栈栈帧（包括局部变量表、操作数栈、方法返回值和动态链接）
public class Frame {

    // 局部变量表指针
    private LocalVars localVars;
    // 操作数栈指针
    private OperandStack operandStack;
    // 线程
    private XThread xThread;
    private int nextPC;
    // 通过 frame 得到当前类的运行时常量池
    private XMethod method;

    public Frame(XThread xThread, XMethod method) {
        this.xThread = xThread;
        this.method = method;
        this.localVars = new LocalVars(method.getMaxLocals());
        this.operandStack = new OperandStack(method.getMaxStack());
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

    public XMethod getMethod() {
        return method;
    }
}
