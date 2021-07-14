package com.test.classfile.attribute;

// LocalVariableTable 属性表中存放方法的局部变量信息，属于调试信息，都不是运行时必需的
/*
LocalVariableTable_attribute {
    u2 attribute_name_index;
    u4 attribute_length;
    u2 local_variable_table_length;
    {   u2 start_pc;
        u2 length;
        u2 name_index;
        u2 descriptor_index;
        u2 index;
    } local_variable_table[local_variable_table_length];
}
*/

import com.test.classfile.ClassReader;

import java.util.ArrayList;
import java.util.List;

// 方法的局部变量信息属性表
public class LocalVariableTableAttribute implements AttributeInfo {

    private List<LocalVariableTableEntry> localVariableTableEntries;

    // 读取部变量信息属性表
    @Override
    public void readInfo(ClassReader classReader) {
        int localVariableTableLength = classReader.readU2();
        this.localVariableTableEntries = new ArrayList<>();
        for (int i = 0; i < localVariableTableLength; i++) {
            int startPC = classReader.readU2();
            int length = classReader.readU2();
            int nameIndex = classReader.readU2();
            int descriptorIndex = classReader.readU2();
            int index = classReader.readU2();
            this.localVariableTableEntries.add(
                    new LocalVariableTableEntry(startPC, length,
                            nameIndex, descriptorIndex, index));
        }
    }
}
