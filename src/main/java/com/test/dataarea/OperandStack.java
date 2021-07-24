package com.test.dataarea;

import com.test.dataarea.heap.XObject;

// 操作数栈
public class OperandStack {
    // 记录栈顶位置（操作数栈的大小是编译器已经确定的）
    private int size;
    // 操作数栈
    private Slot[] operandStack;

    public OperandStack(int maxStack) {
        if (maxStack > 0) {
            this.operandStack = new Slot[maxStack];
        }
    }

    // 向操作数栈栈顶存放 int 型变量
    public void pushInt(int value) {
        Slot slot = new Slot();
        slot.setNum(value);
        this.operandStack[this.size++] = slot;
    }

    // 从操作数栈中读取 int 型变量
    public int popInt() {
        Slot slot = this.operandStack[--this.size];
        this.operandStack[size] = null;
        return slot.getNum();
    }

    // 向操作数栈栈顶存放 float 型变量
    public void pushFloat(float value) {
        Slot slot = new Slot();
        int val = Float.floatToIntBits(value);
        slot.setNum(val);
        this.operandStack[this.size++] = slot;
    }

    // 从操作数栈中读取 float 型变量
    public float popFloat() {
        int val = this.operandStack[--this.size].getNum();
        this.operandStack[this.size] = null;
        return Float.intBitsToFloat(val);
    }

    // 向操作数栈栈顶存放 long 型变量
    public void pushLong(long value) {
        int i1 = (int) value;
        int i2 = (int) (value >> 32);

        Slot slot1 = new Slot();
        slot1.setNum(i1);
        this.operandStack[this.size++] = slot1;

        Slot slot2 = new Slot();
        slot2.setNum(i2);
        this.operandStack[this.size++] = slot2;
    }

    // 从操作数栈中读取 long 型变量
    public long popLong() {
        // 注意：由于是栈，所以 long 型数据的低位在上，高位在下
        int i2 = popInt();
        int i1 = popInt();

        // & 运算是为了将 i1/i2 补全 32 个高位 0
        long l1 = (long) ((i2 & 0x000000ffffffffL) << 32);
        long l2 = i1 & 0x00000000ffffffffL;
        return l1 | l2;
    }

    // 向操作数栈栈顶存放 double 型变量
    public void pushDouble(double value) {
        long l = Double.doubleToLongBits(value);
        pushLong(l);
    }

    // 从操作数栈中读取 double 型变量
    public double popDouble() {
        long l = popLong();
        double d = Double.longBitsToDouble(l);
        return d;
    }

    // 向操作数栈栈顶存放引用型变量
    public void pushRef(XObject object) {
        Slot slot = new Slot();
        slot.setRef(object);
        this.operandStack[this.size++] = slot;
    }

    // 从操作数栈中读取引用型变量
    public XObject popRef() {
        Slot slot = this.operandStack[--this.size];
        XObject ref = (XObject) slot.getRef();
        this.operandStack[this.size] = null;
        return ref;
    }

    // 向操作数栈栈顶存放 boolean 型变量
    public void pushBoolean(boolean val) {
        if(val) {
            this.pushInt(1);
        } else {
            this.pushInt(0);
        }
    }

    // 从操作数栈中读取 boolean 型数据
    public boolean popBoolean() {
        int value = this.popInt();
        if (value == 1) {
            return true;
        }
        return false;
    }

    // 向操作数栈中添加变量
    public void pushSlot(Slot slot) {
        this.operandStack[this.size++] = slot;
    }

    // 从操作数栈中弹出变量
    public Slot popSlot() {
        return this.operandStack[--this.size];
    }

    // 返回距离操作数栈顶 n 个单元格的引用变量
    // 例如：GetRefFromTop(0) 返回操作数栈顶引用，GetRefFromTop(1) 返回从
    // 栈顶开始的倒数第二个引用
    public XObject getRefFromTop(int n) {
        return this.operandStack[this.size - 1 - n].getRef();
    }
}
