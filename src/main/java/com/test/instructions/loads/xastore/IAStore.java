package com.test.instructions.loads.xastore;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.XArrayObject;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.NoOperandsInstruction;

// 按索引给 int 数组赋值
public class IAStore extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int val = stack.popInt();
        int index = stack.popInt();
        XObject arrRef = stack.popRef();

        XAStoreManager.checkNotNull(arrRef);
        int[] ints = XArrayObject.getInts(arrRef);
        XAStoreManager.checkIndex(ints.length, index);
        ints[index] = val;
    }
}
