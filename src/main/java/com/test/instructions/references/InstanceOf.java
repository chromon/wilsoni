package com.test.instructions.references;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.ClassRef;
import com.test.dataarea.heap.RuntimeConstantPool;
import com.test.dataarea.heap.XClass;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.Index16Instruction;

// 判断对象是否是某个类的实例（或者对象的类是否实现了某个接口），并将结果推入操作数栈
public class InstanceOf extends Index16Instruction {
    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        // 对象引用
        XObject ref = stack.popRef();
        if(ref == null) {
            // 如果是 null，则把 false 推入操作数栈（即 instanceof 判断结果为 false）
            stack.pushBoolean(false);
            return;
        }

        // 通过索引从当前类的运行时常量池中查找类符号引用
        RuntimeConstantPool rcp = frame.getMethod().getClazz().getRcp();
        ClassRef classRef = (ClassRef) rcp.getConstantInfo(getIndex()).getValue();
        // 解析类符号引用
        XClass clazz = classRef.resolvedClass();
        if(ref.isInstanceOf(clazz)) {
            stack.pushBoolean(true);
        } else {
            stack.pushBoolean(false);
        }
    }
}
