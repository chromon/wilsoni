package com.test.classfile;

import com.test.classpath.Classpath;
import org.junit.Test;

import java.math.BigInteger;
import java.nio.ByteOrder;

public class ClassFileTest {

    @Test
    public void readAndCheckMagicTest() {
        Classpath cp = new Classpath();
        cp.parse("C:/Program Files/AdoptOpenJDK/jdk-8.0.292.10-hotspot/jre/", null);
        byte[] classData = cp.readClass("java/lang/Object");
//        BigInteger bigInteger = new BigInteger(1, classData);
//        System.out.println(bigInteger.toString(16).toUpperCase());
//
        ClassReader classReader = new ClassReader(classData, ByteOrder.BIG_ENDIAN);
//        System.out.println(ClassFileTest.bytes2hex(classReader.readBytes(4)));
//        classReader.readU2();
//        System.out.println(classReader.readU2());
        System.out.println(classReader.readU4());
        System.out.println(classReader.readU2());
        System.out.println(classReader.readU1());
        System.out.println(classReader.readU1());
    }

    public static String bytes2hex(byte[] bytes) {
        BigInteger bigInteger = new BigInteger(1, bytes);
        return bigInteger.toString(16).toUpperCase();
    }

}
