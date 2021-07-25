package com.test.instructions.loads.xaload;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.XArrayObject;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.NoOperandsInstruction;

// 从数组中加载 byte 或 boolean 类型
public class BALoad extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        // 从操作数栈中弹出第一个操作数：数组索引，
        // 然后弹出第二个操作数：数组引用
        OperandStack stack = frame.getOperandStack();
        int index = stack.popInt();
        XObject arrRef = stack.popRef();

        // 检查数组索引是否大于等于 0 且小于数组长度
        XALoadManager.checkNotNull(arrRef);
        byte[] bytes = XArrayObject.getBytes(arrRef);
        XALoadManager.checkIndex(bytes.length, index);
        stack.pushInt(bytes[index]);
    }
}
