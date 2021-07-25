package com.test.instructions.loads.xaload;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.XArrayObject;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.NoOperandsInstruction;

// 从数组中加载 double 类型
public class DALoad extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int index = stack.popInt();
        XObject arrRef = stack.popRef();
        XALoadManager.checkNotNull(arrRef);
        double[] doubles = XArrayObject.getDoubles(arrRef);
        XALoadManager.checkIndex(doubles.length, index);
        stack.pushDouble(doubles[index]);
    }
}
