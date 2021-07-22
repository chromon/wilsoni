package com.test.dataarea;

// 局部变量表是按索引访问的，所以可以想象成一个数组
// 根据 Java 虚拟机规范，这个数组的每个元素至少可以容纳一个 int 或引用值，
// 两个连续的元素可以容纳一个 long 或 double 值

import com.test.dataarea.heap.XObject;

// 变量槽（局部变量表数组单元）
public class Slot {
    // 存放整数
    private int num;
    // 存放引用
    private XObject ref;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public XObject getRef() {
        return ref;
    }

    public void setRef(XObject ref) {
        this.ref = ref;
    }
}
