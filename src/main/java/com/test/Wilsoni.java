package com.test;

import com.test.classfile.ClassFile;
import com.test.classfile.ClassParse;
import com.test.classpath.Classpath;
import org.apache.commons.cli.ParseException;

import java.util.List;

public class Wilsoni {
    public static void main(String[] args) throws ParseException {
        Cmd cmd = new Cmd();
        cmd.parseCmd(args);

        if (cmd.isVersion()) {
            System.out.println("version 0.0.1");
        } else if (cmd.isHelp()) {
            cmd.printHelpInfo();
        } else {
            startJVM(cmd);
        }
    }

    public static void startJVM(Cmd cmd) {
        Classpath cp = new Classpath();
        cp.parse(cmd.getxJre(), cmd.getClasspath());
        System.out.println("classpath: " + cp
                + ", class: " + cmd.getClassName()
                + ", " + cmd.argsToString());
        String className = cmd.getClassName().replace(".", "/");
        ClassFile cf = load(className, cp);
        printClassInfo(cf);
    }

    private static ClassFile load(String className, Classpath cp) {
        byte[] classData = cp.readClass(className);
        return ClassParse.parse(classData);
    }

    private static void printClassInfo(ClassFile cf) {
        System.out.println("version: " + cf.getMajorVersion() + "." + cf.getMinorVersion());
        System.out.println("constants count: " + cf.getConstantPoolCount());
        System.out.println("access flags: " + cf.getAccessFlag()
                + " (hex: 0x" + Integer.toHexString(cf.getAccessFlag()) + ")");
        System.out.println("this class: " + cf.getClassName());
        System.out.println("super class: " + cf.getSuperClassName());
        System.out.println("interfaces: " + cf.getInterfaceCount());
        List<String> interfaceNames = cf.getInterfaceNames();
        System.out.println("interfaces name:");
        for (int i = 0; i < cf.getInterfaceCount(); i++) {
            System.out.println("\t" + interfaceNames.get(i));
        }
        System.out.println("fields count: " + cf.getFields().size());
        System.out.println("fields: ");
        for (int i = 0; i < cf.getFields().size(); i++) {
            System.out.println("\t" + cf.getFields().get(i).getName());
        }
        System.out.println("methods count: " + cf.getMethods().size());
        for (int i = 0; i < cf.getMethods().size(); i++) {
            System.out.println("\t" + cf.getMethods().get(i).getName());
        }
    }
}
