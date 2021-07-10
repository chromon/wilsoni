package com.test.classpath;

// 类路径项
// 类路径可以分为以下 3 个部分：
// 启动类路径（bootstrap classpath）：jre/lib，主要是 java 标准库
// 扩展类路径（extension classpath）：jre/lib/ext，主要是 java 扩展机制类
// 和用户类路径（user classpath）：默认是当前目录，可以使用 java -cp 命令指定
// 		主要包含类运行所依赖其他用户类以及第三方类库的路径，通常是类库，jar包等

import java.io.*;

// 类路径项
public interface Entry {
    // 寻找并加载 class 文件
    // className 为 class 文件的相对路径，使用 / 分隔，文件后缀 .class
    // 例如读取 java.lang.Object 类，传入的参数为 java/lang/Object.class
    // 返回值为读取到的字节数据
    byte[] readClass(String className);

    // 读取字节码内容到 byte 数组
    default byte[] readToBytes(InputStream is) {
        byte[] content =  new byte[0];

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            // System.out.println("Available bytes:" + fis.available());

            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = is.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            is.close();

            content = out.toByteArray();
            // System.out.println("Readed bytes count:" + content.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}