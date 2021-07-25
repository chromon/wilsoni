package com.test.instructions.references.array;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.ClassRef;
import com.test.dataarea.heap.RuntimeConstantPool;
import com.test.dataarea.heap.XClass;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.BytecodeReader;
import com.test.instructions.base.Instruction;

// multianewarray 指令创建多维数组
public class MultiANewArray implements Instruction {
    // 索引值，通过这个索引可以从运行时常量池中找到一个类符号引用，
    // 解析这个引用就可以得到多维数组类
    private int index;
    // 表示数组维度
    private byte dimensions;

    @Override
    public void FetchOperands(BytecodeReader reader) {
        // 读取两个操作数，这两个操作数在字节码中紧跟在指令操作码后面
        this.index = reader.readU2();
        this.dimensions = reader.readByte();
    }

    @Override
    public void Execute(Frame frame) {
        // 通过索引从运行时常量池中找到类符号引用，解析引用得到多维数组类
        RuntimeConstantPool rcp = frame.getMethod().getClazz().getRcp();
        ClassRef classRef = (ClassRef) rcp.getConstantInfo(this.index).getValue();
        // 在 anewarray 指令中，解析类符号引用后得到的是数组元素的类，
        // 而这里解析出来的直接就是数组类
        XClass arrClass = classRef.resolvedClass();

        OperandStack stack = frame.getOperandStack();
        // 从操作数栈中弹出 n 个整数，分别代表每一个维度的数组长度
        int[] counts = this.popAndCheckCounts(stack, (int) this.dimensions);
        // 根据数组类、数组维度和各个维度的数组长度创建多维数组
        XObject arr = this.newMultiDimensionalArray(counts, arrClass);
        stack.pushRef(arr);
    }

    // 从操作数栈中弹出n个整数，分别代表每一个维度的数组长度
    private int[] popAndCheckCounts(OperandStack stack, int dimensions) {
        int[] counts = new int[dimensions];
        // 从操作数栈中弹出 n 个 int 值，并且确保它们都大于等于 0。
        // 如果其中任何一个小于 0，则抛出异常
        for(int i = counts.length - 1; i >= 0;i--) {
            counts[i] = stack.popInt();
            if(counts[i] < 0) {
                throw new RuntimeException("java.lang.NagativeArraySizeException");
            }
        }
        return counts;
    }

    // 根据数组类、数组维度和各个维度的数组长度创建多维数组
    private XObject newMultiDimensionalArray(int[] counts, XClass arrClass) {
        int count = counts[0];
        XObject arr = arrClass.newArray(count);

        if (counts.length > 1) {
            XObject[] refs = arr.getRefs();
            for (int i = 0; i < refs.length; i++) {
                int[] inner = new int[counts.length - 1];
                System.arraycopy(counts, 1, inner, 0, counts.length - 1);
                refs[i] = newMultiDimensionalArray(inner, arrClass.getComponentClass());
            }
        }
        return arr;
    }
}
