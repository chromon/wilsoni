package com.test.instructions.references.invoke;

import com.test.dataarea.Frame;
import com.test.dataarea.heap.MethodRef;
import com.test.dataarea.heap.RuntimeConstantPool;
import com.test.dataarea.heap.XClass;
import com.test.dataarea.heap.XMethod;
import com.test.instructions.base.Index16Instruction;
import com.test.instructions.base.MethodInvoke;

// invokestatic 指令，调用静态方法

// 调用类的静态方法
public class InvokeStatic extends Index16Instruction {
    @Override
    public void Execute(Frame frame) {
        RuntimeConstantPool rcp = frame.getMethod().getClazz().getRcp();
        MethodRef methodRef = (MethodRef) rcp.getConstantInfo(this.getIndex()).getValue();
        // 解析方法
        XMethod resolvedMethod = methodRef.resolvedMethod();
        if (!resolvedMethod.isStatic()) {
            // 必须是静态方法
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }

        XClass clazz = resolvedMethod.getClazz();
        // 不能是类初始化方法
        // 类初始化方法只能由 Java 虚拟机调用，不能使用 invokestatic 指令调用
        if (!clazz.initStarted()) {
            frame.revertNextPC();
            XClass.initClass(frame.getXThread(), clazz);
            return;
        }

        if (!resolvedMethod.isStatic()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }
        MethodInvoke.invokeMethod(frame, resolvedMethod);
    }
}
