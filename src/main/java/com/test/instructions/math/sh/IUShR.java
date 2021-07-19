package com.test.instructions.math.sh;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.instructions.base.NoOperandsInstruction;

// 位移指令，分为左移和右移两种，右移指令又可以分为算术右移（有符号右移）
// 和逻辑右移（无符号右移）两种。算术右移和逻辑位移的区别仅在于符号位的扩展
// int x = -1;
// println(Integer.toBinaryString(x));      // 11111111111111111111111111111111
// println(Integer.toBinaryString(x >> 8)); // 11111111111111111111111111111111
// println(Integer.toBinaryString(x >>> 8));// 00000000111111111111111111111111

// int 无符号右移（逻辑右移）
public class IUShR extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int i2 = stack.popInt();
        int i1 = stack.popInt();

        // 因为 int 变量只有 32 位，所以 i2 只取前五个比特就足够表示位移位数了
        // 与 31（0x1f） 做与运算，得出移位位数
        int s = i2 & 0x1f;
        int res = i1 >>> s;
        stack.pushInt(res);
    }
}
