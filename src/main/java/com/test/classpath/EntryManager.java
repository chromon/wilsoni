package com.test.classpath;

import java.io.File;

// Entry 实例管理（创建）
public class EntryManager {

    // 根据不同的参数创建不同类型的 Entry 实例
    public static Entry newEntry(String path) {
        // 同时指定多个目录或文件，以分隔符分隔
        // java -cp path/classes;lib/a.jar;lib/b.zip ...
        if (path.contains(File.pathSeparator)) {
            return new CompositeEntry(path);
        }

        // 使用通配符，JDK 6 开始可以使用通配符（*） 指定某个目录下所有 jar 文件
        // java -cp classes/* ...
        if (path.endsWith("*")) {
            return new WildcardEntry(path).getCe();
        }

        // zip 或 jar 文件形式类路径
        // java -cp path/lib.zip(jar) ...
        if (path.endsWith(".jar") || path.endsWith(".JAR")
                || path.endsWith(".zip") || path.endsWith(".ZIP")) {
            return new XZipEntry(path);
        }

        // 目录形式类路径
        // java -cp path/classes ...
        return new DirEntry(path);
    }
}
