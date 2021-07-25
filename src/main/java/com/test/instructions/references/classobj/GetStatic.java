package com.test.instructions.references.classobj;

// pushstatic 和 getstatic 指令用于存取静态变量

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.*;
import com.test.instructions.base.Index16Instruction;

// getstatic 指令和 putstatic 正好相反，
// getstatic 取出类的某个静态变量值，然后推入栈顶
public class GetStatic extends Index16Instruction {
    @Override
    public void Execute(Frame frame) {
        RuntimeConstantPool rcp = frame.getMethod().getClazz().getRcp();
        // 通过索引从当前类的运行时常量池中查找字段符号引用
        FieldRef fieldRef = (FieldRef) rcp.getConstantInfo(super.getIndex()).getValue();
        // 解析符号引用，得到类的静态变量
        XField field = fieldRef.resolvedField();
        XClass clazz = field.getClazz();

        // init class
        if (!clazz.initStarted()) {
            frame.revertNextPC();
            XClass.initClass(frame.getXThread(), clazz);
            return;
        }

        // 如果解析后的字段不是静态字段则抛出异常
        if (!field.isStatic()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }

        // 根据字段类型，从静态变量中取出相应的值，然后推入操作数栈顶
        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        OperandStack stack = frame.getOperandStack();
        Slots slots = clazz.getStaticVars();

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
