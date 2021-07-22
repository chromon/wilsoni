package com.test.instructions.references;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.*;
import com.test.instructions.base.Index16Instruction;

// pushstatic 和 getstatic 指令用于存取静态变量

// 给类的静态变量赋值
public class PutStatic extends Index16Instruction {
    @Override
    public void Execute(Frame frame) {
        XMethod method = frame.getMethod();
        XClass currentClazz = method.getClazz();
        RuntimeConstantPool rcp = currentClazz.getRcp();
        // 通过索引从当前类的运行时常量池中查找字段符号引用
        FieldRef fieldRef = (FieldRef) rcp.getConstantInfo(super.getIndex()).getValue();
        // 解析符号引用，得到类的静态变量
        XField field = fieldRef.resolvedField();
        XClass clazz = field.getClazz();

        // TODO init class

        // 解析后字段是实例字段而非静态字段，则抛出异常
        if (!field.isStatic()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }

        // 如果是 final 字段，则实际操作的是静态常量，只能在类初始化方法中给它赋值
        if (field.isFinal()) {
            if (currentClazz != clazz || "<init>".equals(method.getName())) {
                throw new RuntimeException("java.lang.IllegalAccessError");
            }
        }

        // 根据字段类型从操作数栈中弹出相应的值，赋给静态变量
        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        OperandStack stack = frame.getOperandStack();
        Slots slots = clazz.getStaticVars();

        // 根据字段类型从操作数栈中弹出相应的值，然后赋给静态变量
        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                slots.setInt(slotId, stack.popInt());
                break;
            case 'F':
                slots.setFloat(slotId, stack.popFloat());
                break;
            case 'J':
                slots.setLong(slotId, stack.popLong());
                break;
            case 'D':
                slots.setDouble(slotId, stack.popDouble());
                break;
            case 'L':
            case '[':
                slots.setRef(slotId, stack.popRef());
                break;
        }
    }
}
