package com.test.dataarea.heap;

import com.test.dataarea.heap.Slots;
import com.test.dataarea.heap.XClass;

// 对象
public class XObject {

    // 对象所属的 Class
    private XClass clazz;
    // 对象的实例变量
    // private Slots fields;
    // 表示数组，对于普通对象来说，data 字段中存放的仍然还是 Slots 变量
    // 但是对于数组，可以在其中放各种类型的数组
    private Object data;

    public XObject(XClass clazz) {
        this.clazz = clazz;
        this.data = new Slots(clazz.getInstanceSlotCount());
    }

    public boolean isInstanceOf(XClass clazz) {
        return clazz.isAssignableFrom(this.clazz);
    }

    public XClass getClazz() {
        return clazz;
    }

    public void setClazz(XClass clazz) {
        this.clazz = clazz;
    }

    public Slots getFields() {
        return (Slots) this.data;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public XObject[] getRefs() {
        return (XObject[]) this.data;
    }
}
