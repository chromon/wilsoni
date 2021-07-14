package com.test.classfile.attribute;

import com.test.classfile.ClassReader;

import java.util.ArrayList;
import java.util.List;

// LineNumberTable 属性表存放方法的行号信息，属于调试信息，都不是运行时必需的
/*
LineNumberTable_attribute {
	u2 attribute_name_index;
	u4 attribute_length;
	u2 line_number_table_length;
	{ 	u2 start_pc;
		u2 line_number;
	} line_number_table[line_number_table_length];
}
 */

// 方法行号信息属性表
public class LineNumberTableAttribute implements AttributeInfo {

    private List<LineNumberTableEntry> lineNumberTable;

    // 读取行号信息属性表数据
    @Override
    public void readInfo(ClassReader classReader) {
        // 行号表计数器
        int lineNumberTableLength = classReader.readU2();
        this.lineNumberTable = new ArrayList<>();
        for (int i = 0; i < lineNumberTableLength; i++) {
            int startPC = classReader.readU2();
            int lineNumber = classReader.readU2();
            this.lineNumberTable.add(
                    new LineNumberTableEntry(startPC, lineNumber));
        }
    }
}
