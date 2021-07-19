package com.test.instructions.stores.lstorex;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;
import com.test.instructions.stores.StoreManager;

// 存储指令把变量从操作数栈顶弹出，然后存入局部变量表

// 把 long 型变量 1 从操作数栈顶弹出，然后存入局部变量表
public class LStore1 extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        StoreManager.lStore(frame, 1);
    }
}
