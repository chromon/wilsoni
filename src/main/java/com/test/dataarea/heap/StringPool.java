package com.test.dataarea.heap;

import java.util.HashMap;
import java.util.Map;

// 在 class 文件中，字符串是以 MUTF8 格式保存
// 在 String 对象内部，字符串又是以 UTF16 格式保存的

public class StringPool {

    // 使用 map 表示字符串常量池
    public static final Map<String, XObject> internalStringsMap = new HashMap<>();

    public static XObject getStringObject(XClassLoader classLoader, String str) {
        if(internalStringsMap.containsKey(str)) {
            return internalStringsMap.get(str);
        }

        char[] chars = str.toCharArray();
        XObject obj = new XObject(classLoader.loadClass("java/lang/String"));
        obj.setData(chars);

        XObject strObj = classLoader.loadClass("java/lang/String").newObject();
        strObj.setRefVar("value", "[C", obj);
        internalStringsMap.put(str, strObj);
        return strObj;
    }
}
