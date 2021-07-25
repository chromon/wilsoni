package com.test.instructions.references.array;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.XClass;
import com.test.dataarea.heap.XClassLoader;
import com.test.dataarea.heap.XObject;
import com.test.instructions.base.BytecodeReader;
import com.test.instructions.base.Instruction;

// newarray 指令用来创建基本类型数组，包括 boolean[]、byte[]、
// char[]、short[]、int[]、long[]、float[] 和 double[] 8 种

// newarray 指令需要两个操作数。第一个操作数是一个 uint8 整数，
// 在字节码中紧跟在指令操作码后面，表示要创建哪种类型的数组。
// Java 虚拟机规范把这个操作数叫作 atype，并且规定了它的有效值
public class NewArray implements Instruction {

    private byte aType;

    @Override
    public void FetchOperands(BytecodeReader reader) {
        this.aType = reader.readByte();
    }

    @Override
    public void Execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        // newarray 指令的第二个操作数是 count，从操作数栈中弹出，表示数组长度
        int count = stack.popInt();
        // 如果数组长度小于 0 抛异常
        if (count < 0) {
            throw new RuntimeException("java.lang.NegativeArraySizeException");
        }

        // 根据 atype 值使用当前类的类加载器加载数组类，
        // 然后创建数组对象并推入操作数栈
        XClassLoader classLoader = frame.getMethod().getClazz().getClassLoader();
        // 根据 atype 值使用当前类的类加载器加载数组类
        // 然后创建数组对象并推入操作数栈
        XClass arrClass = this.getPrimitiveArrayClass(classLoader, this.aType);
        XObject arr = arrClass.newArray(count);
        stack.pushRef(arr);
    }

    // 根据 atype 值使用当前类的类加载器加载数组类
    private XClass getPrimitiveArrayClass(XClassLoader classLoader, byte aType) {
        switch (aType) {
            case ArrayConsts.AT_BOOLEAN:
                return classLoader.loadClass("[Z");
            case ArrayConsts.AT_BYTE:
                return classLoader.loadClass("[B");
            case ArrayConsts.AT_CHAR:
                return classLoader.loadClass("[C");
            case ArrayConsts.AT_SHORT:
                return classLoader.loadClass("[S");
            case ArrayConsts.AT_INT:
                return classLoader.loadClass("[I");
            case ArrayConsts.AT_FLOAT:
                return classLoader.loadClass("[F");
            case ArrayConsts.AT_LONG:
                return classLoader.loadClass("[L");
            case ArrayConsts.AT_DOUBLE:
                return classLoader.loadClass("[D");
            default:
                throw new RuntimeException("invalid array type: " + aType);
        }
    }
}
