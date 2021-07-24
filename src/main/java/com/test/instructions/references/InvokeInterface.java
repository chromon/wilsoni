package com.test.instructions.references;

import com.test.dataarea.Frame;
import com.test.dataarea.heap.*;
import com.test.instructions.base.BytecodeReader;
import com.test.instructions.base.Instruction;
import com.test.instructions.base.MethodInvoke;

// invokeinterface 指令针对接口类型的引用调用方法

// 当通过 invokeinterface 指令调用接口方法时，
// 因为 this 引用可以指向任何实现了该接口的类的实例，所以无法使用 vtable 技术

// 和其他三条方法调用指令略有不同，
// 在字节码中，invokeinterface 指令的操作码后面跟着 4 字节而非 2 字节。
// 前两字节的含义和其他指令相同，是个 uint16 运行时常量池索引。
// 第 3 字节的值是给方法传递参数需要的 slot 数，其含义和给 Method 定义
// 的 argSlotCount 字段相同
// 第 4 字节是留给 Oracle 的某些 Java 虚拟机实现用的，它的值必须是 0。
// 该字节的存在是为了保证 Java 虚拟机可以向后兼容
public class InvokeInterface implements Instruction {

    private int index;
    // private int count;
    // private int zero;

    @Override
    public void FetchOperands(BytecodeReader reader) {
        this.index = reader.readShort();
        // count
        reader.readByte();
        // zero
        reader.readByte();
    }

    @Override
    public void Execute(Frame frame) {
        // 从运行时常量池中拿到并解析接口方法符号引用
        RuntimeConstantPool rcp = frame.getMethod().getClazz().getRcp();
        InterfaceMethodRef methodRef = (InterfaceMethodRef) rcp.getConstantInfo(this.index).getValue();
        XMethod resolvedMethod = methodRef.resolvedInterfaceMethod();
        // 如果解析后的方法是静态方法或私有方法，则抛出异常
        if (resolvedMethod.isStatic() || resolvedMethod.isPrivate()) {
            throw new RuntimeException("java.lnag.IncompatibleClassChangeError");
        }

        // 从操作数栈中弹出 this 引用
        XObject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (ref == null) {
            throw new RuntimeException("java.lang.NullPointerException");
        }

        // 如果引用所指对象的类没有实现解析出来的接口，则抛出异常
        if (!ref.getClazz().isImplements(methodRef.resolvedClass())) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }

        // 查找最终要调用的方法
        XMethod methodToBeInvoked = MethodLookup.lookupMethodInClass(
                ref.getClazz(), methodRef.getName(), methodRef.getDescriptor());
        // 如果找不到要调用的方法或方法时抽象的则抛出异常
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new RuntimeException("java.lang.AbstractMethodError");
        }
        // 如果找到的要调用的方法不是 public 则抛出异常
        if (!methodToBeInvoked.isPublic()) {
            throw new RuntimeException("java.lang.IllegalAccessError");
        }

        MethodInvoke.invokeMethod(frame, methodToBeInvoked);

    }
}
