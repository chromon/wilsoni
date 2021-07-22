package com.test.dataarea.heap;

import com.test.dataarea.heap.Slots;
import com.test.dataarea.heap.XClass;

// 对象
public class XObject {

    // 对象所属的 Class
    private XClass clazz;
    // 对象的实例变量
    private Slots fields;

    public XObject(XClass clazz) {
        this.clazz = clazz;
        this.fields = new Slots(clazz.getInstanceSlotCount());
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
        return fields;
    }

    public void setFields(Slots fields) {
        this.fields = fields;
    }
}
