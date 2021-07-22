package com.test.instructions.references;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.ClassRef;
import com.test.dataarea.heap.RuntimeConstantPool;
import com.test.dataarea.heap.XClass;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.Index16Instruction;

// checkcast 指令和 instanceof 指令很像
// 区别在于：
//	instanceof 指令会改变操作数栈（弹出对象引用，推入判断结果）
//	checkcast 则不改变操作数栈（如果判断失败，直接抛出 ClassCastException异常）
public class CheckCast extends Index16Instruction {
    @Override
    public void Execute(Frame frame) {
        // 先从操作数栈中弹出对象引用，再推回去，这样就不会改变操作数栈的状态
        OperandStack stack = frame.getOperandStack();
        XObject ref = stack.popRef();
        stack.pushRef(ref);
        if (ref == null) {
            // 如果引用是 null，则指令执行结束
            // 也就是说，null 引用可以转换成任何类型
            return;
        }

        RuntimeConstantPool rcp = frame.getMethod().getClazz().getRcp();
        ClassRef classRef = (ClassRef) rcp.getConstantInfo(super.getIndex()).getValue();
        // 解析类符号引用
        XClass clazz = classRef.resolvedClass();
        if (!ref.isInstanceOf(clazz)) {
            // 判断对象是否是类的实例。如果是的话，指令执行结束，否则抛出异常
            throw new RuntimeException("java.lang.ClassCastException");
        }
    }
}
