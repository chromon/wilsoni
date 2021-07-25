package com.test.instructions.references.array;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.XArrayObject;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.NoOperandsInstruction;

// arraylength 指令用于获取数组长度
public class ArrayLength extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        // arraylength 指令只需要一个操作数，即从操作数栈顶弹出的数组引用
        XObject arrRef = stack.popRef();
        if (arrRef == null) {
            throw new RuntimeException("java.lang.NullPointerException");
        }

        // 将数组长度推入操作数栈顶
        int arrLen = XArrayObject.getArrayLength(arrRef);
        stack.pushInt(arrLen);
    }
}
