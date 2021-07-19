package com.test.instructions.stores.dstorex;

import com.test.dataarea.Frame;
import com.test.instructions.base.NoOperandsInstruction;
import com.test.instructions.stores.StoreManager;

// 存储指令把变量从操作数栈顶弹出，然后存入局部变量表

// 把 double 型变量 2 从操作数栈顶弹出，然后存入局部变量表
public class DStore2 extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        StoreManager.dStore(frame, 2);
    }
}
