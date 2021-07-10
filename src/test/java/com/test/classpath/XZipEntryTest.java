package com.test.classpath;

import org.junit.Test;

public class XZipEntryTest {

    @Test
    public void testZipEntryReadClass() {
        new XZipEntry("C:/Users/allot/Desktop/Desktop.zip").readClass("Demo.class");
    }

}
