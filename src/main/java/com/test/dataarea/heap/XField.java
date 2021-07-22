package com.test.dataarea.heap;

import com.test.classfile.MemberInfo;
import com.test.classfile.attribute.ConstantValueAttribute;

import java.util.ArrayList;
import java.util.List;

// 字段
public class XField extends ClassMember {

    private int slotId;
    private int constValueIndex;

    // 根据 class 字节码文件的字段信息创建字段表
    public static List<XField> newField(
            XClass clazz, List<MemberInfo> cfFields) {
        List<XField> fields = new ArrayList<>();
        for (int i = 0; i < cfFields.size(); i++) {
            XField field = new XField();
            field.copyMemberInfo(cfFields.get(i));
            field.copyAttributes(cfFields.get(i));
            field.setClazz(clazz);
            fields.add(field);
        }
        return fields;
    }

    public void copyAttributes(MemberInfo cfField) {
        ConstantValueAttribute valAttr = cfField.getConstantValueAttribute();
        if (valAttr != null) {
            this.constValueIndex = valAttr.getConstantValueIndex();
        }
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public boolean isVolatile() {
        return 0 != (this.getAccessFlags() & AccessFlag.ACC_VOLATILE);
    }

    public boolean isTransient() {
        return 0 != (this.getAccessFlags() & AccessFlag.ACC_TRANSIENT);
    }

    public boolean isEnum() {
        return 0 != (this.getAccessFlags() & AccessFlag.ACC_ENUM);
    }

    public boolean isLongOrDouble() {
        return "J".equals(this.getDescriptor()) || "D".equals(this.getDescriptor());
    }

    public int getConstValueIndex() {
        return constValueIndex;
    }

    public void setConstValueIndex(int constValueIndex) {
        this.constValueIndex = constValueIndex;
    }
}
