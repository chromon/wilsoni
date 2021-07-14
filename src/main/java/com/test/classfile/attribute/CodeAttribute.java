package com.test.classfile.attribute;

import com.test.classfile.ClassReader;
import com.test.classfile.ConstantPool;

import java.util.ArrayList;
import java.util.List;

// Code 是变长属性，只存在于 method_info 结构中。Code 属性中存放字节码等方法相关信息
/*
Code_attribute {
	u2 attribute_name_index;
	u4 attribute_length;
	// 操作数栈的最大深度
	u2 max_stack;
	// 局部变量表大小
	u2 max_locals;
	u4 code_length;
	u1 Code[code_length];
	u2 exception_table_length;
	{ 	u2 start_pc;
		u2 end_pc;
		u2 handler_pc;
		u2 catch_type;
	} exception_table[exception_table_length];
	u2 attributes_count;
	attribute_info attributes[attributes_count];
}
 */

// Code 属性中存放字节码等方法相关信息
public class CodeAttribute implements AttributeInfo {

    // 常量池对象
    private ConstantPool cp;
    // 操作数栈最大深度
    private int maxStack;
    // 局部变量表大小
    private int maxLocals;
    // 字节码
    private byte[] code;
    // 异常处理表
    private List<ExceptionTableEntry> exceptionTable;
    // 属性表
    private List<AttributeInfo> attributes;

    public CodeAttribute(ConstantPool cp) {
        this.cp = cp;
    }

    // 读取属性信息
    @Override
    public void readInfo(ClassReader classReader) {
        this.maxStack = classReader.readU2();
        this.maxLocals = classReader.readU2();
        // 字节码长度
        int codeLength = classReader.readU4Int();
        this.code = classReader.readBytes(codeLength);
        this.exceptionTable = readExceptionTable(classReader);
        this.attributes = new AttributeInfoManager().readAttributes(classReader, this.cp);
    }

    // 读取异常表
    private List<ExceptionTableEntry> readExceptionTable(ClassReader reader) {
        int exceptionTableLength = reader.readU2();
        List<ExceptionTableEntry> exceptionTableEntryList = new ArrayList<>();
        for (int i = 0; i < exceptionTableLength; i++) {
            int startPC = reader.readU2();
            int endPC = reader.readU2();
            int handlerPC = reader.readU2();
            int catchType = reader.readU2();

            ExceptionTableEntry et = new ExceptionTableEntry(
                    startPC, endPC, handlerPC, catchType);
            exceptionTableEntryList.add(et);
        }
        return exceptionTableEntryList;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public byte[] getCode() {
        return code;
    }

    public List<ExceptionTableEntry> getExceptionTable() {
        return exceptionTable;
    }
}
