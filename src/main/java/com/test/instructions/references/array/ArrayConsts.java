package com.test.instructions.references.array;

// newarray 指令用来创建基本类型数组，包括 boolean[]、byte[]、
// char[]、short[]、int[]、long[]、float[] 和 double[] 8 种

// newarray 指令需要两个操作数。第一个操作数是一个 uint8 整数，
// 在字节码中紧跟在指令操作码后面，表示要创建哪种类型的数组。
// Java 虚拟机规范把这个操作数叫作 atype，并且规定了它的有效值

// 数组类型常量
public class ArrayConsts {
    public static final int AT_BOOLEAN = 4;
    public static final int AT_CHAR = 5;
    public static final int AT_FLOAT = 6;
    public static final int AT_DOUBLE = 7;
    public static final int AT_BYTE = 8;
    public static final int AT_SHORT = 9;
    public static final int AT_INT = 10;
    public static final int AT_LONG = 11;
}
