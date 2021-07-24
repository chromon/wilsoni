package com.test.instructions.control.xreturn;

import com.test.dataarea.Frame;
import com.test.dataarea.XThread;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.NoOperandsInstruction;

// 返回引用
public class AReturn extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        XThread thread = frame.getXThread();
        Frame currentFrame = thread.popFrame();
        Frame invokerFrame = thread.topFrame();
        XObject ref = currentFrame.getOperandStack().popRef();
        invokerFrame.getOperandStack().pushRef(ref);

    }
}
