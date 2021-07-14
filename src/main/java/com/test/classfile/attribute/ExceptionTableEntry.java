package com.test.classfile.attribute;

// 如果当字节码在第 start_pc 行到第 end_pc 行之间（不包含end_pc行）出现了类型为 catch_type
// 或者其子类的异常（catch_type 为指向一个 CONSTANT_Class_info型常量的索引），
// 则转到第 handler_pc 行继续处理。
// 当 catch_type 的值为 0 时，代表任意异常情况都需要转向到 handler_pc 处进行处理

// 异常处理表
public class ExceptionTableEntry {
    private int startPC;
    private int endPC;
    private int handlerPC;
    private int catchType;

    public ExceptionTableEntry(int startPC, int endPC, int handlerPC, int catchType) {
        this.startPC = startPC;
        this.endPC = endPC;
        this.handlerPC = handlerPC;
        this.catchType = catchType;
    }

    public int getStartPC() {
        return startPC;
    }

    public int getEndPC() {
        return endPC;
    }

    public int getHandlerPC() {
        return handlerPC;
    }

    public int getCatchType() {
        return catchType;
    }
}
