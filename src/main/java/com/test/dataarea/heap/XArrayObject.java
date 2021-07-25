package com.test.dataarea.heap;

// 分别针对引用类型数组和 7 种基本类型数组返回具体的数组数据
public class XArrayObject {

    public static byte[] getBytes(XObject obj) {
        return (byte[]) obj.getData();
    }

    public static short[] getShorts(XObject obj) {
        return (short[]) obj.getData();
    }

    public static int[] getInts(XObject obj) {
        return (int[]) obj.getData();
    }

    public static long[] getLongs(XObject obj) {
        return (long[]) obj.getData();
    }

    public static double[] getDoubles(XObject obj) {
        return (double[]) obj.getData();
    }

    public static float[] getFloats(XObject obj) {
        return (float[]) obj.getData();
    }

    public static XObject[] getRefs(XObject obj) {
        return (XObject[]) obj.getData();
    }

    public static char[] getChars(XObject obj) {
        return (char[]) obj.getData();
    }

    public static int getArrayLength(XObject obj) {
        if(obj.getData() instanceof byte[]) {
            return ((byte[])(obj.getData())).length;
        } else if(obj.getData() instanceof short[]) {
            return ((short[])(obj.getData())).length;
        } else if(obj.getData() instanceof double[]) {
            return ((double[])(obj.getData())).length;
        } else if(obj.getData() instanceof char[]) {
            return ((char[])(obj.getData())).length;
        } else if(obj.getData() instanceof int[]) {
            return ((int[])(obj.getData())).length;
        } else if(obj.getData() instanceof float[]) {
            return ((float[])(obj.getData())).length;
        } else if(obj.getData() instanceof long[]) {
            return ((long[])(obj.getData())).length;
        } else if(obj.getData() instanceof XObject[]) {
            return ((XObject[])(obj.getData())).length;
        } else {
            throw new RuntimeException("Invalid array type: " + obj.getClazz());
        }
    }
}
