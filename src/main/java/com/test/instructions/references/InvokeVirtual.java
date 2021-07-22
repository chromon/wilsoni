package com.test.instructions.references;

import com.test.dataarea.Frame;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.MethodRef;
import com.test.dataarea.heap.RuntimeConstantInfo;
import com.test.dataarea.heap.RuntimeConstantPool;
import com.test.instructions.base.Index16Instruction;

public class InvokeVirtual extends Index16Instruction {
    @Override
    public void Execute(Frame frame) {
        RuntimeConstantPool rcp = frame.getMethod().getClazz().getRcp();
        MethodRef ref = (MethodRef) rcp.getConstantInfo(super.getIndex()).getValue();
        if (ref.getName().equals("println")) {
            OperandStack stack = frame.getOperandStack();
            String descriptor = ref.getDescriptor();
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
            }
            stack.popRef();
        }
    }
}
