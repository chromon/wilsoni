package com.test.instructions.math.rem;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

import java.math.BigDecimal;
import java.math.RoundingMode;

// float 类型求余
public class FRem extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        float f2 = stack.popFloat();
        float f1 = stack.popFloat();

        BigDecimal res = new BigDecimal(f1)
                .remainder(new BigDecimal(f2))
                .setScale(4, RoundingMode.HALF_UP);
        stack.pushDouble(res.floatValue());
    }
}
