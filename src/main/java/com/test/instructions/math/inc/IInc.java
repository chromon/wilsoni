package com.test.instructions.math.inc;

import com.test.dataarea.Frame;
import com.test.dataarea.LocalVars;
import com.test.instructions.base.BytecodeReader;
import com.test.instructions.base.NoOperandsInstruction;

// iinc 指令给局部变量表中的 int 变量增加常量值
// 局部变量表索引和常量值都有指令的操作数提供
public class IInc extends NoOperandsInstruction {

    // 索引
    private int index;
    // 增加的常量值
    private int constant;

    // 从字节码中读取操作数
    @Override
    public void FetchOperands(BytecodeReader reader) {
        this.index = reader.readU1();
        this.constant = reader.readByte();
    }

    // 从局部变量表中读取变量，加上常量值，再将结果写回到局部变量表
    @Override
    public void Execute(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        int val = localVars.getInt(this.index);
        val += constant;
        localVars.setInt(this.index, val);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getConstant() {
        return constant;
    }

    public void setConstant(int constant) {
        this.constant = constant;
    }
}
