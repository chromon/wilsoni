package com.test.classpath;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// 目录形式类路径
// java -cp path/classes ...
public class DirEntry implements Entry {

    // 目录的绝对路径
    private String absDir;

    public DirEntry(String path) {
        try {
            // 将 path 路径转为 绝对路径
            this.absDir = new File(path).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取 class 文件
    @Override
    public byte[] readClass(String className) {
        File file = new File(this.absDir, className);
        long fileSize = file.length();

        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big to read");
            return null;
        }

        byte[] buffer = null;
        try {
            buffer = readToBytes(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return buffer;
    }

    @Override
    public String toString() {
        return "DirEntry{" +
                "absDir='" + absDir + '\'' +
                '}';
    }
}
