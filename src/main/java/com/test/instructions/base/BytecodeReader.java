package com.test.instructions.base;


import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

// 字节码读取
public class BytecodeReader {

    // java nio 字节缓冲区
    private ByteBuffer buf;
    private int offset;

    public BytecodeReader() {}

    public BytecodeReader(byte[] data, ByteOrder order) {
        // 初始化 ByteBuffer 容器，对 byte 数组进行封装
        // position 默认为 0
        this.buf = ByteBuffer.wrap(data)
                .asReadOnlyBuffer()
                .order(order);
    }

    // 8-bit signed int
    public byte readByte() {
        byte b = buf.get(offset);
        offset++;
        return b;
    }

    // 16-bit signed int
    public short readShort() {
        short d = buf.getShort(offset);
        offset += 2;
        return d;
    }

    // 读取 u1 类型数据: 8-bit unsigned int
    private int readUnsignedByte() {
        int d = (int)(buf.get(offset));
        offset++;
        return d;
    }

    // 读取 u2 数据类型: 16-bit unsigned int
    private int readUnsignedShort() {
        int d = (int)(buf.getShort(offset));
        offset += 2;
        return d;
    }

    // 读取 u4 数据类型: 32-bit unsigned int
    private int readUnsignedInt() {
        int d =  (int)(buf.getInt(offset));
        offset += 4;
        return d;
    }

    // 读取 32 为 float 有符号数据
    public float readFloat() {
        float d = buf.getFloat(offset);
        offset += 4;
        return d;
    }

    // 读取 64 位 double 有符号数据
    public double readDouble() {
        double d = buf.getDouble(offset);
        offset += 8;
        return d;
    }

    // 读取 u8 数据类型: 64-bit signed int
    public long readLong() {
        long d = buf.getLong(offset);
        offset += 8;
        return d;
    }

    // 读取 u2 表，表的大小由开头的 u2 数据指出
    private int[] readUnsignedShorts() {
        int n = this.readUnsignedShort();
        int[] s = new int[n];
        for (int i: s) {
            s[i] = this.readUnsignedShort();
        }
        return s;
    }

    // 读取 u1 长度数据
    public int readU1() {
        return this.readUnsignedByte();
    }

    // 读取 u2 长度数据
    public int readU2() {
        return this.readUnsignedShort();
    }

    // 读取多个 u2， 不包括计数器
    public List<Integer> readU2s(int count) {
        List<Integer> list = new ArrayList<>();
        if (count < 1) {
            return list;
        }

        for (int i = 0; i < count; i++) {
            list.add(this.readU2());
        }
        return list;
    }

    // 读取多个 u2，包括 计数器
    public List<Integer> readU2s() {
        int count = this.readU2();
        List<Integer> list = new ArrayList<>();
        if (count < 1) {
            return list;
        }

        for (int i = 0; i < count; i++) {
            list.add(this.readU2());
        }
        return list;
    }

    // 读取 u4 长度数据并转为 16 进制字符串，用于读取魔数
    public String readU4() {
        byte[] d = this.readBytes(4);
        return this.bytes2hex(d);
    }

    // 读取 u4 长度数据
    public int readU4Int() {
        return this.readUnsignedInt();
    }

    public int[] readU4Ints(int n) {
        int[] ints = new int[n];
        for (int i = 0; i < n; i++) {
            ints[i] = this.readU4Int();
        }
        return ints;
    }

    // tableswitch 指令操作码的后面有 0 ~ 3 字节的 padding，
    // 以保证 defaultOffset 在字节码中的地址是 4 的倍数
    // 所以需要跳过余数个 byte
    public void skipPadding() {
        while(offset % 4 != 0) {
            readByte();
        }
    }

    // 读取指定数量的字节
    public byte[] readBytes(int n) {
        byte[] bytes = new byte[n];
        buf.position(offset);
        buf.get(bytes, 0, n);
        offset += n;
        return bytes;
    }

    // 将 byte 数组转为 16 进制字符串
    public String bytes2hex(byte[] bytes) {
        BigInteger bigInteger = new BigInteger(1, bytes);
        return bigInteger.toString(16).toUpperCase();
    }

    // 获取当前的下标位置，表示进行下一个读写操作时的起始位置
    public int getPosition() {
        return buf.position();
    }

    // 从索引位置开始获取一个 byte
    public byte getByte(int index) {
        return buf.get(index);
    }

    // 从索引位置开始获取一个 short
    public short getShort(int index) {
        return buf.getShort(index);
    }

    // 重置
    public void reset(byte[] buf, int offset) {
        this.buf = ByteBuffer.allocate(buf.length);
        this.buf.put(buf);
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }
}