package com.test.instructions.loads.xastore;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.XArrayObject;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.NoOperandsInstruction;

// 按索引给 long 数组赋值
public class LAStore extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long val = stack.popLong();
        int index = stack.popInt();
        XObject arrRef = stack.popRef();

        XAStoreManager.checkNotNull(arrRef);
        long[] longs = XArrayObject.getLongs(arrRef);
        XAStoreManager.checkIndex(longs.length, index);
        longs[index] = val;
    }
}
