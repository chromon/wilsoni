package com.test.instructions.stores.astorex;

import com.test.dataarea.Frame;
import com.test.instructions.base.Index8Instruction;
import com.test.instructions.stores.StoreManager;

// 存储指令把变量从操作数栈顶弹出，然后存入局部变量表

// 把 引用 型变量从操作数栈顶弹出，然后存入局部变量表
public class AStore extends Index8Instruction {
    @Override
    public void Execute(Frame frame) {
       StoreManager.aStore(frame, super.getIndex());
    }
}
