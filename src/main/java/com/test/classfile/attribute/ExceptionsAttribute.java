package com.test.classfile.attribute;

import com.test.classfile.ClassReader;

import java.util.List;

// Exceptions 是变长属性，记录方法抛出的异常表
/*
Exceptions_attribute {
	u2 attribute_name_index;
	u4 attribute_length;
	u2 number_of_exceptions;
	u2 exception_index_table[number_of_exceptions];
}
 */

public class ExceptionsAttribute implements AttributeInfo {

    private List<Integer> exceptionIndexTable;

    @Override
    public void readInfo(ClassReader classReader) {
        this.exceptionIndexTable = classReader.readU2s();
    }

    public List<Integer> getExceptionIndexTable() {
        return exceptionIndexTable;
    }
}
