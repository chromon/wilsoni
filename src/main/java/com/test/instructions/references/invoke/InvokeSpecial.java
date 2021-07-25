package com.test.instructions.references.invoke;

import com.test.dataarea.Frame;
import com.test.dataarea.heap.*;
import com.test.instructions.base.Index16Instruction;
import com.test.instructions.base.MethodInvoke;

// invokespecial 指令用来调用无须动态绑定的实例方法，
// 包括构造函数、私有方法和通过 super 关键字调用的超类方法
public class InvokeSpecial extends Index16Instruction {
    @Override
    public void Execute(Frame frame) {
        XClass currentClass = frame.getMethod().getClazz();
        RuntimeConstantPool rcp = currentClass.getRcp();
        // 方法符号引用
        MethodRef methodRef = (MethodRef) rcp.getConstantInfo(this.getIndex()).getValue();
        // 解析符号引用得到类和方法
        XClass resolvedClass = methodRef.resolvedClass();
        XMethod resolvedMethod = methodRef.resolvedMethod();
        // 解析出的方法是构造方法时，声明该方法的类必须是解析出来的类
        if (resolvedMethod.getName().equals("<init>")
                && resolvedMethod.getClazz() != resolvedClass) {
            throw new RuntimeException("java.lang.NoSuchMethodError");
        }
        // 如果解析出来的方法是静态方法则抛出异常
        if (resolvedMethod.isStatic()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }

        // 从操作数栈中弹出 this 引用
        // GetRefFromTop 返回距离操作数栈顶 n 个单元格的引用变量
        XObject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (ref == null) {
            throw new RuntimeException("java.lang.NullPointerException");
        }

        // 确保 protected 方法只能被声明该方法的类或子类调用
        if (resolvedMethod.isProtected()
                && resolvedMethod.getClazz().isSuperClassOf(currentClass)
                && !resolvedMethod.getClazz().getPackageName().equals(currentClass.getPackageName())
                && ref.getClazz() != currentClass
                && !ref.getClazz().isSubClassOf(currentClass)) {
            throw new RuntimeException("java.lang.IllegalAccessError");
        }

        // 如果调用的超类中的函数，但不是构造函数，且当前类的 ACC_SUPER 标志被设置，
        // 需要一个额外的过程查找最终要调用的方法；
        // 否则前面从方法符号引用中解析出来的方法就是要调用的方法
        XMethod methodToBeInvoked = resolvedMethod;
        if (currentClass.isSuper()
                && resolvedClass.isSuperClassOf(currentClass)
                && !resolvedMethod.getName().equals("<init>")) {
            methodToBeInvoked = MethodLookup.lookupMethodInClass(
                    currentClass.getSuperClass(), methodRef.getName(), methodRef.getDescriptor());
        }

        // 如果查找过程失败，或者找到的方法是抽象的，抛出 AbstractMethodError 异常
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new RuntimeException("java.lang.AbstractMethodError");
        }

        // 调用方法
        MethodInvoke.invokeMethod(frame, methodToBeInvoked);

    }
}
