package com.test.dataarea.heap;

import com.test.classfile.MemberInfo;
import com.test.classfile.attribute.CodeAttribute;

import java.util.ArrayList;
import java.util.List;

import static com.test.dataarea.heap.AccessFlag.ACC_ABSTRACT;
import static com.test.dataarea.heap.AccessFlag.ACC_NATIVE;

// 方法信息
public class XMethod extends ClassMember {
    // 操作数栈大小
    private int maxStack;
    // 局部变量表大小
    private int maxLocals;
    // 方法字节码
    private byte[] code;
    // 方法参数个数
    private int argSlotCount;

    // 根据 class 字节码文件中的方法信息创建 Method 表
    public static List<XMethod> newMethods(
            XClass clazz, List<MemberInfo> cfMethods) {
        List<XMethod> methods = new ArrayList<>();
        for (int i = 0; i < cfMethods.size(); i++) {
            XMethod method = new XMethod();
            method.setClazz(clazz);
            method.copyMemberInfo(cfMethods.get(i));
            method.copyAttribute(cfMethods.get(i));
            method.calcArgSlotCount();
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

    // 计算参数个数
    public void calcArgSlotCount() {
        // 分解方法描述符，返回 MethodDescriptor 结构体
        MethodDescriptorParser parser = new MethodDescriptorParser();
        MethodDescriptor desc = parser.parse(this.getDescriptor());
        for (String paramType: desc.parameterTypes) {
            this.argSlotCount++;
            if (paramType.equals("J") || paramType.equals("D")) {
                this.argSlotCount++;
            }
        }
        // 非静态方法多一个 this
        if (!this.isStatic()) {
            this.argSlotCount++;
        }
    }

    public boolean isAbstract() {
        return 0 != (this.getAccessFlags() & AccessFlag.ACC_ABSTRACT);
    }

    public boolean isNative() {
        return 0 != (this.getAccessFlags() & AccessFlag.ACC_NATIVE);
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

    public int getArgSlotCount() {
        return argSlotCount;
    }
}
