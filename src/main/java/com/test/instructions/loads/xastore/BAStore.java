package com.test.instructions.loads.xastore;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.XArrayObject;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.NoOperandsInstruction;

// 按索引给 byte 或 boolean 数组赋值
public class BAStore extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int val = stack.popInt();
        int index = stack.popInt();
        XObject arrRef = stack.popRef();

        // 检查数组引用是否为 null
        XAStoreManager.checkNotNull(arrRef);
        byte[] bytes = XArrayObject.getBytes(arrRef);
        // 数组索引应大于等于 0 小于数组长度
        XAStoreManager.checkIndex(bytes.length, index);
        bytes[index] = (byte) val;
    }
}
