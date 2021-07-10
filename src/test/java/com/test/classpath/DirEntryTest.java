package com.test.classpath;

import org.junit.Test;

public class DirEntryTest {

    @Test
    public void testDirEntryReadClass() {
        new DirEntry("F:/com/test").readClass("Demo.class");
    }
}
