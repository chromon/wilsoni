package com.test;

import org.apache.commons.cli.ParseException;

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
        System.out.println("classpath: " + cmd.getClasspath()
                + ", class: " + cmd.getClassName()
                + ", " + cmd.argsToString());
    }
}
