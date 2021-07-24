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

    // logInst 参数控制是否把指令执行信息打印到控制台
    public void interpret(XMethod method, boolean logInst) {
        XThread xThread = new XThread();
        Frame frame = xThread.newFrame(method);
        xThread.pushFrame(frame);

        loop(xThread, logInst);
    }

    // 循环执行计算 PC、解码指令、执行指令三个步骤，直到遇到错误退出
    public void loop(XThread xThread, boolean logInst) {
        BytecodeReader reader = new BytecodeReader();
        while (true) {

            Frame frame = xThread.currentFrame();

            // 计算 PC
            int pc = frame.getNextPC();
            xThread.setPC(pc);

            // 解码指令
            reader.reset(frame.getMethod().getCode(), pc);
            int opcode = reader.readU1();
            opcode &= 0xFF;
            Instruction instruction = InstructionManager.newInstruction(opcode);
            instruction.FetchOperands(reader);
            frame.setNextPC(reader.getOffset());

            if (logInst) {
                // 在方法执行过程中打印指令信息
                logInstruction(frame, instruction);
            }

            // 执行指令
            // System.out.println("PC: " + pc + ", instruction: " + instruction);
            instruction.Execute(frame);
            if (xThread.isStackEmpty()) {
                break;
            }
        }
    }

    // 在方法执行过程中打印指令信息
    private void logInstruction(Frame frame, Instruction instruction) {
        XMethod method = frame.getMethod();
        String className = method.getClazz().getName();
        String methodName = method.getName();
        int pc = frame.getXThread().getPC();
        System.out.println(className + "." + methodName + " #" + pc + " " + instruction);
    }
}
