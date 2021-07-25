package com.test.instructions.references.classobj;

import com.test.dataarea.Frame;
import com.test.dataarea.heap.*;
import com.test.instructions.base.Index16Instruction;

// new 指令，专门用于创建类实例
public class New extends Index16Instruction {

    // new 指令的操作数是一个 u2 索引，来自字节码
    @Override
    public void Execute(Frame frame) {
        RuntimeConstantPool rcp = frame.getMethod().getClazz().getRcp();
        // 通过索引可以从当前类的运行时常量池中找到一个类符号引用
        RuntimeConstantInfo constant = rcp.getConstantInfo(super.getIndex());
        ClassRef classRef = (ClassRef) constant.getValue();
        // 解析符号引用，得到类数据
        XClass clazz = classRef.resolvedClass();

        // init class
        if (!clazz.initStarted()) {
            frame.revertNextPC();
            XClass.initClass(frame.getXThread(), clazz);
            return;
        }

        // 接口和抽象类不能实例化，需要抛出异常
        if (clazz.isInterface() || clazz.isAbstract()) {
            throw new RuntimeException("java.lang.InstantiationError");
        }

        // 创建对象
        XObject obj = clazz.newObject();
        frame.getOperandStack().pushRef(obj);
    }
}
