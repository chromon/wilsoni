package com.test.dataarea.heap;

import com.test.classfile.MemberInfo;

// 字段和方法都属于类的成员，有相同的信息（访问标志、名字、描述符）
public class ClassMember {
    // 访问标志
    private int accessFlags;
    // 名字（字段名或方法名）
    private String name;
    // 描述符
    private String descriptor;
    // 类对象，可以通过字段或方法访问到所属类
    private XClass clazz;

    // 从字节码文件中复制字段方法相关数据
    public void copyMemberInfo(MemberInfo memberInfo) {
        this.accessFlags = memberInfo.getAccessFlags();
        this.name = memberInfo.getName();
        this.descriptor = memberInfo.getDescriptor();
    }

    public boolean isPublic() {
        return 0 != (this.accessFlags & AccessFlag.ACC_PUBLIC);
    }

    public boolean isPrivate() {
        return 0 != (this.accessFlags & AccessFlag.ACC_PRIVATE);
    }

    public boolean isProtected() {
        return 0 != (this.accessFlags & AccessFlag.ACC_PROTECTED);
    }

    public boolean isStatic() {
        return 0 != (this.accessFlags & AccessFlag.ACC_STATIC);
    }

    public boolean isFinal() {
        return 0 != (this.accessFlags & AccessFlag.ACC_FINAL);
    }

    public boolean isSynthetic() {
        return 0 != (this.accessFlags & AccessFlag.ACC_SYNTHETIC);
    }

    // 是否是可访问的 public 或 同一个包中，或不同包的子类
    public boolean isAccessibleTo(XClass otherCls) {
        if(this.isPublic()) {
            return true;
        }

        if(this.isProtected()) {
            return this.getClazz().equals(otherCls)
                    || otherCls.isSubClassOf(this.getClazz())
                    || this.getClazz().getPackageName().equals(otherCls.getPackageName());
        }
        if(!this.isPrivate()) {
            return this.getClazz().getPackageName().equals(otherCls.getPackageName());
        }
        return this.getClazz() == otherCls;
    }

    public XClass getClazz() {
        return clazz;
    }

    public void setClazz(XClass clazz) {
        this.clazz = clazz;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }
}
