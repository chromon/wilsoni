package com.test.instructions.loads.xastore;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.XArrayObject;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.NoOperandsInstruction;

// 按索引给 char 数组赋值
public class CAStore extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int val = stack.popInt();
        int index = stack.popInt();
        XObject arrRef = stack.popRef();

        XAStoreManager.checkNotNull(arrRef);
        char[] chars = XArrayObject.getChars(arrRef);
        XAStoreManager.checkIndex(chars.length, index);
        chars[index] = (char) val;
    }
}
