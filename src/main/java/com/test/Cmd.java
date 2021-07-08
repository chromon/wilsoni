package com.test;

import org.apache.commons.cli.*;

public class Cmd {

    // 帮助信息
    private boolean help;
    // 版本号
    private boolean version;
    // 类路径
    private String classpath;
    // jre 路径
    private String xJre;
    // 参数数组
    private String[] args;
    private String className;

    private Options options;

    public void parseCmd(String[] args) {
        options = new Options();
        options.addOption("h", "help", false, "Print this usage information");
        options.addOption("v", "verbose", false, "Print out VERBOSE information" );
        options.addOption("cp", "classpath", true, "class path");
        options.addOption("Xjre", "Xjre", true, "jre path");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            int count = 0;

            if (cmd.hasOption("h")) {
                this.help = true;
                count++;
            }

            if (cmd.hasOption("v")) {
                this.version = true;
                count++;
            }

            if (cmd.hasOption("cp")) {
                this.classpath = cmd.getOptionValue("cp");
                count += 2;
            }

            if (cmd.hasOption("Xjre")) {
                this.xJre = cmd.getOptionValue("Xjre");
                count += 2;
            }

            if (count > 0 && args.length > count) {
                int argsCount = args.length - count;
                if(argsCount < 1) {
                    throw new RuntimeException("argument count must not less than one");
                }

                if (argsCount > 0) {
                    this.className = args[count];
                }

                String[] arguments = new String[argsCount - 1];
                System.arraycopy(args, count + 1, arguments, 0, args.length - count - 1);

                this.args = arguments;
            }
        } catch (ParseException e) {
            System.out.println("Unexpected exception:" + e.getMessage());
        }
    }

    // 打印帮助信息
    public void printHelpInfo() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("cmd", options);
    }

    public boolean isHelp() {
        return help;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public String getxJre() {
        return xJre;
    }

    public void setxJre(String xJre) {
        this.xJre = xJre;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String argsToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("args[");
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if (i != args.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");

        return sb.toString();
    }
}
