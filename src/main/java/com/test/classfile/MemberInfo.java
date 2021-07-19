package com.test.classfile;

// 字段表和方法表，分别存储字段和方法信息。字段和方法的基本结构大致相同，差别仅在于属性表
// 下面是 Java 虚拟机规范给出的字段和方法结构定义
/*
field_info {
    u2             access_flags;
    u2             name_index;
    u2             descriptor_index;
    u2             attributes_count;
    attribute_info attributes[attributes_count];
}
method_info {
    u2             access_flags;
    u2             name_index;
    u2             descriptor_index;
    u2             attributes_count;
    attribute_info attributes[attributes_count];
}
*/

import com.test.classfile.attribute.AttributeInfo;
import com.test.classfile.attribute.AttributeInfoManager;
import com.test.classfile.attribute.CodeAttribute;

import java.util.List;

// 字段和方法表信息
public class MemberInfo {

    // 常量池对象
    public ConstantPool cp;
    // 访问标志（常量池索引）
    private int accessFlags;
    // 字段名或方法名（常量池索引）
    private int nameIndex;
    // 字段或方法描述符（常量池索引）
    private int descriptorIndex;
    // 字段属性表
    private List<AttributeInfo> attributes;

    public MemberInfo(ClassReader reader, ConstantPool cp) {
        this.cp = cp;
        this.accessFlags = reader.readU2();
        this.nameIndex = reader.readU2();
        this.descriptorIndex = reader.readU2();
        this.attributes = new AttributeInfoManager().readAttributes(reader, cp);
    }

    // 获取字段或方法名
    public String getName() {
        return this.cp.getUtf8(this.nameIndex);
    }

    // 获取字段或方法描述
    public String getDescriptor() {
        return this.cp.getUtf8(this.descriptorIndex);
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    // 获取 Code 属性
    public CodeAttribute getCodeAttribute() {
        for (AttributeInfo attribute: attributes) {
            if (attribute instanceof CodeAttribute) {
                return (CodeAttribute) attribute;
            }
        }
        return null;
    }
}
