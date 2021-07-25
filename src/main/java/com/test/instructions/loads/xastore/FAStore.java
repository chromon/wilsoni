package com.test.instructions.loads.xastore;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.XArrayObject;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.NoOperandsInstruction;

// 按索引给 float 数组赋值
public class FAStore extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        float val = stack.popFloat();
        int index = stack.popInt();
        XObject arrRef = stack.popRef();

        XAStoreManager.checkNotNull(arrRef);
        float[] floats = XArrayObject.getFloats(arrRef);
        XAStoreManager.checkIndex(floats.length, index);
        floats[index] = val;
    }
}
