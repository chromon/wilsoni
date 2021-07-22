package com.test.dataarea;

import com.test.dataarea.heap.XMethod;

// 线程
public class XThread {
    // pc 计数器
    private int pc;
    // 虚拟机栈
    private XStack stack;

    public XThread() {
        // 创建虚拟机栈实例，并指定最大栈帧数
        stack = new XStack(1024);
    }

    // 压栈
    public void pushFrame(Frame frame) {
        this.stack.pushFrame(frame);
    }

    // 弹栈
    public Frame popFrame() {
        return this.stack.popFrame();
    }

    // 返回栈顶元素
    public Frame currentFrame() {
        return this.stack.topFrame();
    }

    // 返回栈顶元素
    public Frame topFrame() {
        return this.stack.topFrame();
    }

    public int getPC() {
        return pc;
    }

    public void setPC(int pc) {
        this.pc = pc;
    }

    public Frame newFrame(XMethod method) {
        return new Frame(this, method);
    }
}
