package com.test.dataarea;

import com.test.dataarea.heap.XObject;

// 局部变量表
public class LocalVars {

    // 局部变量表是按索引访问的，所以可以想象成一个数组
    private Slot[] localVars;

    public LocalVars(int maxLocals) {
        if (maxLocals > 0) {
            this.localVars = new Slot[maxLocals];
        }
    }

    // 向局部变量表中存放 int 型数据
    public void setInt(int index, int value) {
        Slot slot = new Slot();
        slot.setNum(value);
        this.localVars[index] = slot;
    }

    // 从局部变量表中读取 int 型数据
    //（byte、short 和 char 类型可以转为 int 类型存取）
    public int getInt(int index) {
        return this.localVars[index].getNum();
    }

    // 向局部变量表中存放 float 型数据
    public void setFloat(int index, float value) {
        Slot slot = new Slot();
        int val = Float.floatToIntBits(value);
        slot.setNum(val);
        this.localVars[index] = slot;
    }

    // 从局部变量表中读取 float 型数据
    public float getFloat(int index) {
        int value = this.localVars[index].getNum();
        return Float.intBitsToFloat(value);
    }

    // 向局部变量表中存放 long 型数据
    // long 类型数据可以拆分成两个 int 型数据
    public void setLong(int index, long value) {
        int i1 = (int) value;
        int i2 = (int) (value >> 32);

        Slot slot1 = new Slot();
        slot1.setNum(i1);
        this.localVars[index] = slot1;

        Slot slot2 = new Slot();
        slot2.setNum(i2);
        this.localVars[++index] = slot2;
    }

    // 从局部变量表中读取 long 型数据
    public long getLong(int index) {
        int i1 = this.getInt(index);
        int i2 = this.getInt(++index);

        // & 运算是为了将 i1/i2 补全 32 个高位 0
        long l1 = (long) ((i2 & 0x000000ffffffffL) << 32);
        long l2 = i1 & 0x00000000ffffffffL;
        return l1 | l2;
    }

    // 向局部变量表中存放 double 型数据
    public void setDouble(int index, double value) {
        long l = Double.doubleToLongBits(value);
        this.setLong(index, l);
    }

    // 从局部变量表中读取 long 型数据
    public double getDouble(int index) {
        long l = this.getLong(index);
        double d = Double.longBitsToDouble(l);
        return d;
    }

    // 向局部变量表中存放引用型数据
    public void setRef(int index, XObject obj) {
        Slot slot = new Slot();
        slot.setRef(obj);
        this.localVars[index] = slot;
    }

    // 从局部变量表中读取引用型数据
    public XObject getRef(int index) {
        Slot slot = this.localVars[index];
        if (slot == null) {
            return null;
        }
        return slot.getRef();
    }

    // 向局部变量表中存放 boolean 型数据
    public void setBoolean(int index, boolean value) {
        if (value) {
            this.setInt(index, 1);
        } else {
            this.setInt(index, 0);
        }
    }

    // 从局部变量表中读取 long 型数据
    public boolean getBoolean(int index) {
        int value = this.getInt(index);
        if (value == 1) {
            return true;
        }
        return false;
    }

    public void setSlot(int index, Slot slot) {
        this.localVars[index] = slot;
    }
}
