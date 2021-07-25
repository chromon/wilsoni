package com.test.instructions.references.array;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.ClassRef;
import com.test.dataarea.heap.RuntimeConstantPool;
import com.test.dataarea.heap.XClass;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.Index16Instruction;

// anewarray 指令用来创建引用类型数组，需要两个操作数，索引和数组长度
public class ANewArray extends Index16Instruction {

    private int index;

    @Override
    public void Execute(Frame frame) {
        RuntimeConstantPool rcp = frame.getMethod().getClazz().getRcp();
        // 通过索引从当前类的运行时常量池中找到一个类符号引用
        ClassRef classRef = (ClassRef) rcp.getConstantInfo(this.index).getValue();
        // 解析符号引用得到数组元素的类（注意：multianewarray 解析得到的是数组类）
        XClass componentClass = classRef.resolvedClass();

        // 第二个操作数是数组长度，从操作数栈中弹出
        OperandStack stack = frame.getOperandStack();
        int count = stack.popInt();
        if (count < 0) {
            throw new RuntimeException("java.lang.NegativeArraySizeException");
        }

        // 返回与类对应的数组类
        XClass arrClass = componentClass.getArrayClass();
        // 创建数组对象并推入操作数栈
        XObject arr = arrClass.newArray(count);
        stack.pushRef(arr);
    }
}
