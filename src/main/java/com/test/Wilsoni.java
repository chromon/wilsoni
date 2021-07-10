package com.test;

import com.test.classpath.Classpath;
import org.apache.commons.cli.ParseException;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

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
        byte[] classData = cp.readClass(className);
        if (classData == null) {
            System.out.println("could not find or load main class " + cmd.getClassName());
            return;
        }

        BigInteger bigInteger = new BigInteger(1, classData);
        System.out.println(bigInteger.toString(16).toUpperCase());
    }
}
