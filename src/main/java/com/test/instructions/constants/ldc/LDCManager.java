package com.test.instructions.constants.ldc;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.RuntimeConstantPool;
import com.test.dataarea.heap.StringPool;
import com.test.dataarea.heap.XClass;
import com.test.dataarea.heap.XObject;

public class LDCManager {
    public static void ldc(Frame frame, int index) {
        OperandStack stack = frame.getOperandStack();
        RuntimeConstantPool rcp = frame.getMethod().getClazz().getRcp();
        XClass clazz = frame.getMethod().getClazz();

        Object constant = rcp.getConstantInfo(index).getValue();
        if(constant instanceof Integer) {
            stack.pushInt((Integer) constant);
        } else if(constant instanceof Float) {
            stack.pushFloat((Float) constant);
        } else if(constant instanceof Long) {
            stack.pushLong((Long) constant);
        } else if(constant instanceof Double) {
            stack.pushDouble((Double) constant);
        } else if(constant instanceof String) {
            String str = (String) constant;
            XObject strObj = StringPool.getStringObject(clazz.getClassLoader(), str);
            stack.pushRef(strObj);
//        } else if(constant instanceof ClassRef) {
//            ClassRef ref = (ClassRef) constant;
//            VMObject classObj = ref.resolvedClass().getjClass();
//            stack.pushRef(classObj);
//        } else if(constant instanceof ConstantFieldRefInfo) {
//            stack.pushRef(constant);
//        } else if(constant instanceof ConstantMethodRefInfo) {
//            stack.pushRef(constant);
//        } else if(constant instanceof ConstantInterfaceMethodRefInfo) {
//            stack.pushRef(constant);
        } else {
//            stack.pushRef((XObject) constant);
            throw new RuntimeException("todo ldc");
        }
    }
}
