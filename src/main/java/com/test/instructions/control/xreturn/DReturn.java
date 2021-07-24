package com.test.instructions.control.xreturn;

import com.test.dataarea.Frame;
import com.test.dataarea.XThread;
import com.test.instructions.base.NoOperandsInstruction;

// 返回 double 类型
public class DReturn extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        XThread thread = frame.getXThread();
        Frame currentFrame = thread.popFrame();
        Frame invokerFrame = thread.topFrame();
        double val = currentFrame.getOperandStack().popDouble();
        invokerFrame.getOperandStack().pushDouble(val);
    }
}
