package com.test.classfile.constant;

import com.test.classfile.ClassReader;

import java.io.*;

// 字符串在 class 文件中是以 MUTF-8（Modified UTF-8）方式编码的

/*
CONSTANT_Utf8_info {
	u1 tag;
	u2 length;
	u1 bytes[length];
}
 */

// CONSTANT_Utf8_info 常量里放的是 MUTF-8 编码的字符串
public class ConstantUtf8Info implements ConstantInfo{

    private int length;
    private String str;

    @Override
    public void readInfo(ClassReader classReader) {
        this.length = classReader.readU2();
        if (this.length > 0) {
            byte[] bytes = classReader.readBytes(length);
            try {
                this.str = decodeMutf8(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 将 byte[] 解码为 MUTF-8 编码方式
    public String decodeMutf8(byte[] bytes) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length + 2);
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeShort(bytes.length);
        dos.write(bytes);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        DataInputStream dis = new DataInputStream(bais);
        return dis.readUTF();
    }

    public int getLength() {
        return length;
    }

    public String getStr() {
        return str;
    }
}
