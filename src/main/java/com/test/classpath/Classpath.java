package com.test.classpath;

// 类路径项
// 类路径可以分为以下 3 个部分：
// 启动类路径（bootstrap classpath）：jre/lib，主要是 java 标准库
// 扩展类路径（extension classpath）：jre/lib/ext，主要是 java 扩展机制类
// 和用户类路径（user classpath）：默认是当前目录，可以使用 java -cp 命令指定
// 		主要包含类运行所依赖其他用户类以及第三方类库的路径，通常是类库，jar包等

import java.io.File;
import java.nio.file.Paths;

// 类路径
public class Classpath {
    // 启动类路径
    Entry bootClasspath;
    // 扩展类路径
    Entry extClasspath;
    // 用户类路径
    Entry userClasspath;

    // 解析类路径
    public void parse(String jre, String cp) {
        // 使用 -Xjre 选项解析启动类路径和扩展类路径
        this.parseBootAndExtClasspath(jre);
        // 使用 -classpath/-cp 选项解析用户类路径
        this.parseUserClasspath(cp);
    }

    // 解析启动类路径和扩展类路径
    private void parseBootAndExtClasspath(String jre) {
        // 获取 jre 目录
        String jreDir = this.getJreDir(jre);
        // jre/lib/*
        String jreLibPath = jreDir + File.separator + "lib" + File.separator + "*";
        this.bootClasspath = new WildcardEntry(jreLibPath).getCe();
        // jre/lib/ext/*
        String jreExtPath = jreDir + File.separator + "lib" + File.separator + "ext" + File.separator + "*";
        this.extClasspath = new WildcardEntry(jreExtPath).getCe();
    }

    // 解析用户类路径
    private void parseUserClasspath(String cp) {
        if (cp == null) {
            cp = ".";
        }
        this.userClasspath = EntryManager.newEntry(cp);
    }

    // 如果没有提供 -classpath/-cp 选项，则使用当前目录作为用户路径
    // ReadClass 方法依次从启动类路径、扩展类路径和用户类路径搜索 class 文件
    public byte[] readClass(String className) {
        className = className + ".class";
        byte[] content = null;

        if ((content = this.bootClasspath.readClass(className)) != null) {
            return content;
        }
        if ((content = this.extClasspath.readClass(className)) != null) {
            return content;
        }
        return this.userClasspath.readClass(className);
    }

    // 获取 jre 目录
    // 优先使用输入的 -Xjre 选项作为 jre 目录，没有则在当前目录下寻找
    // 如果还没有则尝试使用 JAVA_HOME 环境变量
    private String getJreDir(String jre) {
        if (jre != "" && new File(jre).exists()) {
            return jre;
        }
        if (new File("./jre").exists()) {
            return "./jre";
        }
        String javaHome = System.getenv("JAVA_HOME");
        if (javaHome != "") {
            return Paths.get(javaHome, jre).toString();
        }

        return "error: can not find jre folder";
    }

    @Override
    public String toString() {
        return "Classpath{" +
                "userClasspath=" + userClasspath.toString() +
                '}';
    }
}
