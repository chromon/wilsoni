package com.test.classpath;

import java.io.File;
import java.io.IOException;

// 通配符路径，使用通配符，JDK 6 开始可以使用通配符（*） 指定某个目录下所有 jar 文件
// java -cp classes/* ...
// WildcardEntry 实际上是一个 CompositeEntry
public class WildcardEntry {

    private CompositeEntry ce = new CompositeEntry();

    public WildcardEntry(String path) {
        // 删除路径末尾通配符 *
        String baseDir = path.substring(0, path.length() - 1);
        File file = new File(baseDir);
        File[] files = file.listFiles();
        for (File f: files) {
            // 通配符类路径不能递归匹配子目录下的 JAR 文件
            if (f.isFile()) {
                // 根据后缀名选出 JAR 文件
                if (f.getAbsolutePath().endsWith(".jar")
                        || f.getAbsolutePath().endsWith(".JAR")) {
                    try {
                        XZipEntry zipEntry = new XZipEntry(f.getCanonicalPath());
                        ce.entries.add(zipEntry);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public CompositeEntry getCe() {
        return ce;
    }
}
