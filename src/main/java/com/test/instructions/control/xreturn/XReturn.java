package com.test.instructions.control.xreturn;

// 返回指令

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;

// 没有返回值
public class XReturn extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        frame.getXThread().popFrame();
    }
}
