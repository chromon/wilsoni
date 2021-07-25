package com.test.instructions.loads.xaload;

// <t>aload 系列指令按索引取数组元素值，然后推入操作数栈

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.XArrayObject;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.NoOperandsInstruction;

// 从数组中加载引用类型
public class AALoad extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        // 从操作数栈中弹出第一个操作数：数组索引，
        // 然后弹出第二个操作数：数组引用
        OperandStack stack = frame.getOperandStack();
        int index = stack.popInt();
        XObject arrRef = stack.popRef();

        // 检查数组引用是否为 null
        XALoadManager.checkNotNull(arrRef);
        XObject[] refs = XArrayObject.getRefs(arrRef);
        // 检查数组索引是否大于等于 0 且小于数组长度
        XALoadManager.checkIndex(refs.length, index);
        // 按索引取出数组元素，推入操作数栈顶
        stack.pushRef(refs[index]);
    }
}
