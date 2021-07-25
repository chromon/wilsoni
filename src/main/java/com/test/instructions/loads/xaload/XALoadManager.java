package com.test.instructions.loads.xaload;

import com.test.dataarea.heap.XObject;

public class XALoadManager {
    // 检查对象是否为空
    public static void checkNotNull(XObject ref) {
        if(ref == null) {
            throw new RuntimeException("java.lang.NullPointerException");
        }
    }

    // 检查数组索引是否在正常取值内
    // 是否大于等于 0 且小于数组长度
    public static void checkIndex(int len, int index) {
        if(index < 0 || index > len) {
            throw new RuntimeException("java.lang.IndexOutOfBoundsException");
        }
    }
}
