package com.test.dataarea.heap;

import java.util.ArrayList;
import java.util.List;

// 方法描述信息
public class MethodDescriptor {
    // 参数类型列表
    List<String> parameterTypes = new ArrayList<String>();
    // 返回值类型
    String  returnType;

    public void addParameterType(String t) {
        this.parameterTypes.add(t);
    }

    public List<String> getParameterTypes() {
        return this.parameterTypes;
    }

    public String getReturnType() {
        return this.returnType;
    }
}
