package com.test.interpreter;

import com.test.classfile.MemberInfo;
import com.test.classfile.attribute.CodeAttribute;
import com.test.dataarea.Frame;
import com.test.dataarea.XThread;
import com.test.dataarea.heap.XMethod;
import com.test.instructions.InstructionManager;
import com.test.instructions.base.BytecodeReader;
import com.test.instructions.base.Instruction;

// 解释器
public class Interpreter {

    public void interpret(XMethod method) {
        // Code属性，进一步获得执行方法所需的局部变量表和操作数栈空间，以及方法的字节码
//        CodeAttribute codeAttribute = memberInfo.getCodeAttribute();
//        int maxLocals = codeAttribute.getMaxLocals();
//        int maxStack = codeAttribute.getMaxStack();
//        byte[] byteCode = codeAttribute.getCode();

        XThread xThread = new XThread();
        Frame frame = xThread.newFrame(method);
        xThread.pushFrame(frame);

        loop(xThread, method.getCode());
    }

    // 循环执行计算 PC、解码指令、执行指令三个步骤，直到遇到错误退出
    public void loop(XThread xThread, byte[] bytecode) {
        Frame frame = xThread.popFrame();
        BytecodeReader reader = new BytecodeReader();
        while (true) {
            // 计算 PC
            int pc = frame.getNextPC();
            xThread.setPC(pc);

            // 解码指令
            reader.reset(bytecode, pc);
            int opcode = reader.readU1();
            opcode &= 0xFF;
            Instruction instruction = InstructionManager.newInstruction(opcode);
            instruction.FetchOperands(reader);
            frame.setNextPC(reader.getOffset());

            // 执行指令
            System.out.println("PC: " + pc + ", instruction: " + instruction);
            instruction.Execute(frame);
        }
    }
}
