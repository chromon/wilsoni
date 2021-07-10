package com.test.classpath;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

// zip 或 jar 文件形式类路径
// java -cp path/lib.zip(jar) ...
public class XZipEntry implements Entry {

    // zip 或 jar 文件的绝对路径
    private String absPath;

    public XZipEntry(String path) {
        try {
            // 将 path 路径转为 绝对路径
            this.absPath = new File(path).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从 zip 或 jar 文件中读取 class 文件
    @Override
    public byte[] readClass(String className) {
        File file = new File(this.absPath);
        byte[] content = null;

        try {
            ZipFile zipFile = new ZipFile(file);
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            ZipInputStream zin = new ZipInputStream(in);
            ZipEntry zipEntry;

            while ((zipEntry = zin.getNextEntry()) != null) {
                if (!zipEntry.isDirectory() && zipEntry.getName().equals(className)) {
                    InputStream is = zipFile.getInputStream(zipEntry);

                    // 读取字节码文件到 byte 数组
                    content = readToBytes(is);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public String toString() {
        return "ZipEntry{" +
                "absPath='" + absPath + '\'' +
                '}';
    }
}
