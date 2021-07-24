package com.test.instructions.base;

// Java 虚拟机规范一共提供了 4 条方法调用指令
// invokestatic、invokespecial、invokeinterface、invokevirtual
// 四条方法调用指令内容基本相同：
// 定位到需要调用的方法之后，Java 虚拟机要给这个方法创建
// 一个新的帧并把它推入 Java 虚拟机栈顶，然后传递参数

import com.test.dataarea.Frame;
import com.test.dataarea.Slot;
import com.test.dataarea.XThread;
import com.test.dataarea.heap.XMethod;

// 方法调用指令抽象
public class MethodInvoke {
    // 调用方法
    public static void invokeMethod(Frame frame, XMethod method) {
        // 创建新的栈帧并推入 Java 虚拟机栈
        XThread thread = frame.getXThread();
        Frame newFrame = thread.newFrame(method);
        thread.pushFrame(newFrame);

        // 方法参数在局部变量表中占用 slot 数量
        // 不一定是局部变量表中的参数个数，double 和 long 占两个
        // 另外对于实例方法，Java 编译器会在参数列表的前面添加一个参数 this 引用
        int argSlotCount = method.getArgSlotCount();
        if (argSlotCount > 0) {
            for (int i = argSlotCount - 1; i >= 0; i--) {
                Slot slot = frame.getOperandStack().popSlot();
                newFrame.getLocalVars().setSlot(i, slot);
            }
        }

        // mock
        if (method.isNative()) {
            if (method.getName().equals("registerNatives")) {
                thread.popFrame();
            } else {
                throw new RuntimeException("native method: "
                        + method.getClazz().getName() + " ," + method.getName()
                        + " ," + method.getDescriptor());
            }
        }
    }
}
