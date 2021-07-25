package com.test.instructions.loads.xastore;

// <t>astore 系列指令按索引给数组元素赋值

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.XArrayObject;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.NoOperandsInstruction;

// 按索引给 引用 数组赋值
public class AAStore extends NoOperandsInstruction {
    @Override
    public void Execute(Frame frame) {
        // aastore 指令的三个操作数分别是：要赋给数组元素的值、数组
        // 索引、数组引用，依次从操作数栈中弹出
        OperandStack stack = frame.getOperandStack();
        XObject ref = stack.popRef();
        int index = stack.popInt();
        XObject arrRef = stack.popRef();

        // 检查数组引用是否为 null
        XAStoreManager.checkNotNull(arrRef);
        XObject[] refs = XArrayObject.getRefs(arrRef);
        // 数组索引应大于等于 0 小于数组长度
        XAStoreManager.checkIndex(refs.length, index);
        // 按索引给数组元素赋值
        refs[index] = ref;
    }
}
