package com.test.instructions.stores.fstorex;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;
import com.test.instructions.stores.StoreManager;

// 存储指令把变量从操作数栈顶弹出，然后存入局部变量表

// 把 float 型变量 3 从操作数栈顶弹出，然后存入局部变量表
public class FStore3 extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        StoreManager.fStore(frame, 3);
    }
}
