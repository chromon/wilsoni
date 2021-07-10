package com.test.classpath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// 多类路径
// java -cp path/classes;lib/a.jar;lib/b.zip ...
public class CompositeEntry implements Entry {

    public List<Entry> entries = new ArrayList<>();

    public CompositeEntry() {}

    public CompositeEntry(String pathList) {
        String[] paths = pathList.split(File.pathSeparator);
        // 将路径列表按分隔符分割成多个小路径，并将小路径转换为具体的实例
        for (String path : paths) {
            Entry entry = EntryManager.newEntry(path);
            entries.add(entry);
        }
    }

    // 读取 class 文件
    @Override
    public byte[] readClass(String className) {
        // 依次调用每个子路径的 readClass() 方法，成功读取 class 数据时返回
        for (Entry entry : entries) {
            byte[] content = entry.readClass(className);
            if (content != null) {
                return content;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String prefix = "CompositeEntry{"
                + "entries= [";
        String suffix = "]}";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entries.size(); i++) {
            sb.append(entries.get(i).toString());
            if (i != entries.size() - 1) {
                sb.append(",");
            }
        }

        return prefix + sb.toString() + suffix;
    }
}
