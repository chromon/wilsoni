package com.test.dataarea;

import java.util.Stack;

// 虚拟机栈
public class XStack {

    // 虚拟机栈容量
    private int maxSize;
    // 虚拟机栈大小
    private int size;
    // 栈顶栈帧
    private Frame topFrame;
    // java 栈用于模拟虚拟机栈
    private Stack<Frame> stack;

    public XStack(int maxSize) {
        this.maxSize = maxSize;
        this.stack = new Stack<>();
    }

    // 将栈帧推入栈顶
    public void pushFrame(Frame frame) {
        this.topFrame = frame;
        this.stack.push(frame);
    }

    // 弹出栈顶栈帧
    public Frame popFrame() {
        if (this.stack.isEmpty()) {
            throw new RuntimeException("jvm stack is empty");
        }
        Frame frame = this.stack.pop();

        // 更新栈顶栈帧
        if (this.stack.isEmpty()) {
            this.topFrame = null;
        } else {
            this.topFrame = stack.peek();
        }
        return frame;
    }

    // 返回栈顶栈帧
    public Frame topFrame() {
        if (stack.isEmpty()) {
            return null;
        }
        Frame frame = stack.peek();
        return frame;
    }

    // 虚拟机栈是否为空
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Frame getTopFrame() {
        return topFrame;
    }

    public void setTopFrame(Frame topFrame) {
        this.topFrame = topFrame;
    }
}
