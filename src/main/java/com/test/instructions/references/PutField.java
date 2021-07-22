package com.test.instructions.references;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.*;
import com.test.instructions.base.Index16Instruction;

// putfield 指令给实例变量赋值
public class PutField extends Index16Instruction {
    @Override
    public void Execute(Frame frame) {
        XMethod method = frame.getMethod();
        XClass currentClazz = method.getClazz();
        RuntimeConstantPool rcp = currentClazz.getRcp();
        // 通过索引从当前类的运行时常量池中查找字段符号引用
        FieldRef fieldRef = (FieldRef) rcp.getConstantInfo(super.getIndex()).getValue();
        XField field = fieldRef.resolvedField();
        XClass clazz = field.getClazz();

        // 解析后的字段必须是实例字段，否则抛出异常
        if (field.isStatic()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }

        // 如果是 final 字段，则只能在构造函数中初始化，否则抛出 IllegalAccessError
        if (field.isFinal()) {
            if (currentClazz != clazz || !"<init>".equals(method.getName())) {
                throw new RuntimeException("java.lang.IllegalAccessError");
            }
        }

        // 先根据字段类型从操作数栈中弹出相应的变量值，然后弹出
        // 对象引用。如果引用是null，需要抛出著名的空指针异常，
        // 否则通过引用给实例变量赋值
        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        OperandStack stack = frame.getOperandStack();

        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                int val = stack.popInt();
                XObject ref = stack.popRef();
                if (ref == null) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                ref.getFields().setInt(slotId, val);
                break;
            case 'F':
                float f = stack.popFloat();
                XObject rf = stack.popRef();
                if (rf == null) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                rf.getFields().setFloat(slotId, f);
                break;
            case 'J':
                long l = stack.popLong();
                XObject rl = stack.popRef();
                if (rl == null) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                rl.getFields().setLong(slotId, l);
                break;
            case 'D':
                double d = stack.popDouble();
                XObject rd = stack.popRef();
                if (rd == null) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                rd.getFields().setDouble(slotId, d);
                break;
            case 'L':
            case '[':
                XObject v = stack.popRef();
                XObject r = stack.popRef();
                r.getFields().setRef(slotId, v);
                break;
        }
    }
}
