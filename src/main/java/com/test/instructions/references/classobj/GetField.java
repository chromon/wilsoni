package com.test.instructions.references.classobj;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.*;
import com.test.instructions.base.Index16Instruction;

// getfield 指令获取对象的实例变量值，然后推入操作数栈
public class GetField extends Index16Instruction {
    @Override
    public void Execute(Frame frame) {
        XMethod method = frame.getMethod();
        XClass currentClazz = method.getClazz();
        RuntimeConstantPool rcp = currentClazz.getRcp();
        // 通过索引从当前类的运行时常量池中查找字段符号引用
        FieldRef fieldRef = (FieldRef) rcp.getConstantInfo(super.getIndex()).getValue();
        // 解析
        XField field = fieldRef.resolvedField();
        XClass clazz = field.getClazz();

        if (field.isStatic()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }

        OperandStack stack = frame.getOperandStack();
        XObject ref = stack.popRef();
        // 弹出对象引用，如果是 null，则抛出 NullPointerException
        if (ref == null) {
            throw new RuntimeException("java.lang.NullPointerException");
        }

        // 根据字段类型，获取相应的实例变量值，然后推入操作数栈
        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        Slots slots = (Slots) ref.getFields();

        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                stack.pushInt(slots.getInt(slotId));
                break;
            case 'F':
                stack.pushFloat(slots.getFloat(slotId));
                break;
            case 'J':
                stack.pushLong(slots.getLong(slotId));
                break;
            case 'D':
                stack.pushDouble(slots.getDouble(slotId));
                break;
            case 'L':
            case '[':
                stack.pushRef(slots.getRef(slotId));
                break;
        }
    }
}
