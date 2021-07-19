package com.test.instructions.math.rem;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

import java.math.BigDecimal;
import java.math.RoundingMode;

// double 类型求余
public class DRem extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        double d2 = stack.popDouble();
        double d1 = stack.popDouble();
        // remainder() 用于计算两个 BigDecimal 的余数，非模运算，可能为负
        BigDecimal res = new BigDecimal(d1)
                .remainder(new BigDecimal(d2))
                .setScale(4, RoundingMode.HALF_UP);
        stack.pushDouble(res.doubleValue());
    }
}
