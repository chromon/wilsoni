package com.test.instructions.control.xreturn;

import com.test.dataarea.Frame;
import com.test.dataarea.XThread;
import com.test.instructions.base.NoOperandsInstruction;

// 返回 int 类型
public class IReturn extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        XThread thread = frame.getXThread();
        Frame currentFrame = thread.popFrame();
        Frame invokerFrame = thread.topFrame();
        int val = currentFrame.getOperandStack().popInt();
        invokerFrame.getOperandStack().pushInt(val);
    }
}
