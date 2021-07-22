package com.test.dataarea.heap;

import com.test.classfile.MemberInfo;
import com.test.classfile.attribute.CodeAttribute;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

// 方法信息
public class XMethod extends ClassMember {
    // 操作数栈大小
    private int maxStack;
    // 局部变量表大小
    private int maxLocals;
    // 方法字节码
    private byte[] code;

    // 根据 class 字节码文件中的方法信息创建 Method 表
    public static List<XMethod> newMethods(
            XClass clazz, List<MemberInfo> cfMethods) {
        List<XMethod> methods = new ArrayList<>();
        for (int i = 0; i < cfMethods.size(); i++) {
            XMethod method = new XMethod();
            method.setClazz(clazz);
            method.copyMemberInfo(cfMethods.get(i));
            method.copyAttribute(cfMethods.get(i));
            methods.add(method);
        }
        return methods;
    }

    // 复制 class 字节码文件中的 code 属性表信息
    public void copyAttribute(MemberInfo cfMethod) {
        CodeAttribute codeAttribute = cfMethod.getCodeAttribute();
        if (codeAttribute != null) {
            this.maxStack = codeAttribute.getMaxStack();
            this.maxLocals = codeAttribute.getMaxLocals();
            this.code = codeAttribute.getCode();
        }
    }

    public int getMaxStack() {
        return maxStack;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public void setMaxLocals(int maxLocals) {
        this.maxLocals = maxLocals;
    }

    public byte[] getCode() {
        return code;
    }

    public void setCode(byte[] code) {
        this.code = code;
    }
}
