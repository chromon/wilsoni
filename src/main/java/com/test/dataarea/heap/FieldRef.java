package com.test.dataarea.heap;

import com.test.classfile.constant.ConstantFieldrefInfo;

// 字段符号引用
public class FieldRef extends MemberRef {

    private XField field;

    public FieldRef(RuntimeConstantPool rcp, ConstantFieldrefInfo refInfo) {
        this.setRcp(rcp);
        this.copyMemberRefInfo(refInfo);
    }
    
    // 解析符号引用
    public XField resolvedField() {
        if (this.field == null) {
            this.resolveFieldRef();
        }
        return this.field;
    }

    // 根据 Java 虚拟机规范 5.4.3.2 节给出了字段符号引用的解析步骤
    // 如果类 D 想通过字段符号引用访问类 C 的某个字段，
    // 首先要解析符号引用得到类 C，然后根据字段名和描述符查找字段。
    // 如果字段查找失败，则虚拟机抛出 NoSuchFieldError 异常。
    // 如果查找成功，但 D 没有足够的权限访问该字段，则虚拟机抛出 IllegalAccessError 异常
    private void resolveFieldRef() {
        XClass d = this.getRcp().getClazz();
        XClass c = this.resolvedClass();
        XField f = this.lookupField(c, this.getName(), this.getDescriptor());

        if (f == null) {
            throw new RuntimeException("java.lang.NoSuchFieldError");
        }
        if (!f.isAccessibleTo(d)) {
            throw new RuntimeException("java.lang.IllegalAccessError");
        }
        this.field = f;
    }

    // 字段查找
    // 首先在 C 的字段中查找。如果找不到，在 C 的直接接口递归应用这个查找过程。
    // 如果还找不到的话，在 C 的超类中递归应用这个查找过程。
    // 如果仍然找不到，则查找失败
    private XField lookupField(XClass c, String name, String descriptor) {
        for (XField field : c.getFields()) {
            if (field.getName().equals(name) && field.getDescriptor().equals(descriptor)) {
                return field;
            }
        }
        for (XClass clazz: c.getInterfaces()) {
            XField field = lookupField(clazz, name, descriptor);
            if (field != null) {
                return field;
            }
        }
        if (c.getSuperClass() != null) {
            return lookupField(c.getSuperClass(), name, descriptor);
        }

        return null;
    }
}
