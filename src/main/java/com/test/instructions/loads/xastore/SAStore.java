package com.test.instructions.loads.xastore;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.XArrayObject;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.NoOperandsInstruction;

// 按索引给 short 数组赋值
public class SAStore extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int val = stack.popInt();
        int index = stack.popInt();
        XObject arrRef = stack.popRef();

        XAStoreManager.checkNotNull(arrRef);
        short[] shorts = XArrayObject.getShorts(arrRef);
        XAStoreManager.checkIndex(shorts.length, index);
        shorts[index] = (short) val;
    }
}
