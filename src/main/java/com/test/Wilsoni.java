package com.test;

import com.test.classfile.ClassFile;
import com.test.classfile.ClassParse;
import com.test.classfile.MemberInfo;
import com.test.classpath.Classpath;
import com.test.dataarea.Frame;
import com.test.dataarea.LocalVars;
import com.test.dataarea.OperandStack;
import com.test.dataarea.heap.XClass;
import com.test.dataarea.heap.XClassLoader;
import com.test.dataarea.heap.XMethod;
import com.test.interpreter.Interpreter;
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
//        System.out.println("classpath: " + cp
//                + ", class: " + cmd.getClassName()
//                + ", " + cmd.argsToString());
        String className = cmd.getClassName().replace(".", "/");

        XClassLoader loader = new XClassLoader(cp, cmd.isVerboseClass());
        XClass mainClass = loader.loadClass(className);

        XMethod mainMethod = mainClass.getMainMethod();
        if (mainMethod != null) {
            new Interpreter().interpret(mainMethod, cmd.isVerboseInst());
        } else {
            throw new RuntimeException("main method not found in class: "
                    + cmd.getClassName());
        }
    }

    private static ClassFile load(String className, Classpath cp) {
        byte[] classData = cp.readClass(className);
        return ClassParse.parse(classData);
    }

    private static MemberInfo getMainMethod(ClassFile cf) {
        for (MemberInfo m : cf.getMethods()) {
            if (m.getName().equals("main") && m.getDescriptor().equals("([Ljava/lang/String;)V")) {
                return m;
            }
        }
        return null;
    }

    private static void testLocalVars(LocalVars localVars) {
        localVars.setInt(0, 100);
        localVars.setInt(1, -100);
        localVars.setLong(2, 2997924580L);
        localVars.setLong(4, -2997924580L);
        localVars.setFloat(6, 3.1415926F);
        localVars.setDouble(7, 2.71828182845);
        localVars.setRef(9, null);
        System.out.println(localVars.getInt(0));
        System.out.println(localVars.getInt(1));
        System.out.println(localVars.getLong(2));
        System.out.println(localVars.getLong(4));
        System.out.println(localVars.getFloat(6));
        System.out.println(localVars.getDouble(7));
        System.out.println(localVars.getRef(9));
    }

    private static void testOperandStack(OperandStack operandStack) {
        operandStack.pushInt(100);
        operandStack.pushInt(-100);
        operandStack.pushLong(2997924580L);
        operandStack.pushLong(-2997924580L);
        operandStack.pushFloat(3.1415926F);
        operandStack.pushDouble(2.71828182845);
        operandStack.pushRef(null);
        System.out.println(operandStack.popRef());
        System.out.println(operandStack.popDouble());
        System.out.println(operandStack.popFloat());
        System.out.println(operandStack.popLong());
        System.out.println(operandStack.popLong());
        System.out.println(operandStack.popInt());
        System.out.println(operandStack.popInt());
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
