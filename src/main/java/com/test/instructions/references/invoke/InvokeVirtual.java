package com.test.instructions.references.invoke;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.*;
import com.test.instructions.base.Index16Instruction;
import com.test.instructions.base.MethodInvoke;

// 当 Java 虚拟机通过 invokevirtual 调用方法时，
// this 引用指向某个类（或其子类）的实例。因为类的继承层次是固定的，
// 所以虚拟机可以使用一种叫作 vtable（Virtual Method Table）的技术加速方法查找

// 动态绑定
public class InvokeVirtual extends Index16Instruction {
    @Override
    public void Execute(Frame frame) {
        // 当前类
        XClass currentClass = frame.getMethod().getClazz();
        // 当前常量池
        RuntimeConstantPool rcp = currentClass.getRcp();
        // 方法符号引用
        MethodRef methodRef = (MethodRef) rcp.getConstantInfo(this.getIndex()).getValue();
        // 解析符号引用得到方法
        XMethod resolvedMethod = methodRef.resolvedMethod();
        // 如果解析出来的方法是静态方法则抛出异常
        if (resolvedMethod.isStatic()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }

        // 从操作数栈中弹出 this 引用
        XObject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (ref == null) {
            // mock
            if (methodRef.getName().equals("println")) {
                _println(frame.getOperandStack(), methodRef.getDescriptor());
                return;
            }
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

        // 从对象的类中查找到真正要调用的方法
        XMethod methodToBeInvoked = MethodLookup.lookupMethodInClass(ref.getClazz(), methodRef.getName(), methodRef.getDescriptor());
        // 如果找不到或找到的是抽象方法则抛出异常
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new RuntimeException("java.lang.AbstractMethodError");
        }

        // 调用方法
        MethodInvoke.invokeMethod(frame, methodToBeInvoked);
    }

    private void _println(OperandStack stack, String descriptor) {
        if("(Z)V".equals(descriptor)) {
            System.out.println(stack.popInt());
        } else if("(C)V".equals(descriptor)) {
            System.out.println((char)stack.popInt());
        } else if("(B)V".equals(descriptor)) {
            System.out.println(stack.popInt());
        } else if("(S)V".equals(descriptor)) {
            System.out.println(stack.popInt());
        } else if("(I)V".equals(descriptor)) {
            System.out.println(stack.popInt());
        } else if("(J)V".equals(descriptor)) {
            System.out.println(stack.popLong());
        } else if("(F)V".equals(descriptor)) {
            System.out.println(stack.popFloat());
        } else if("(D)V".equals(descriptor)) {
            System.out.println(stack.popDouble());
        } else if("()V".equals(descriptor)) {
            System.out.print("void");
        } else if("(Ljava/lang/String;)V".equals(descriptor)) {
//            XObject obj = stack.popRef();
//            XObject strObj = obj.getRefVar("value", "[C");
//            char[] chars = (char[]) strObj.getData();
//            String str = new String(chars);
//            System.out.print(str);
        }
        stack.popRef();
    }
}
