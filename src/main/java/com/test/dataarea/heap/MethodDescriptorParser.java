package com.test.dataarea.heap;

// 解析方法描述符
public class MethodDescriptorParser {

    private String raw;
    private int offset;
    private MethodDescriptor parsed;

    public MethodDescriptor parse(String descriptor) {
        this.raw = descriptor;
        this.parsed = new MethodDescriptor();
        startParams();
        parseParamTypes();
        endParams();
        parseReturnType();
        finish();
        return this.parsed;
    }

    private void finish() {
        if(this.offset != this.raw.length()) {
            error();
        }
    }

    private char readUint8() {
        char c = this.raw.charAt(this.offset);
        this.offset++;
        return c;
    }

    private void unreadUint8() {
        this.offset--;
    }

    private void error() {
        throw new RuntimeException("BAD descriptor: " + raw);
    }

    private void startParams() {
        if(readUint8() != '(') {
            error();
        }
    }

    private void endParams() {
        if(readUint8() != ')') {
            error();
        }
    }

    private String parseFieldType() {
        char c = readUint8();
        switch (c) {
            case 'B':
                return "B";
            case 'C':
                return "C";
            case 'D':
                return "D";
            case 'F':
                return "F";
            case 'I':
                return "I";
            case 'J':
                return "J";
            case 'S':
                return "S";
            case 'Z':
                return "Z";
            case 'L':
                return parseObjectType();
            case '[':
                return parseArrayType();
            default:
                unreadUint8();
                return "";
        }
    }

    private String parseObjectType() {
        String unread = raw.substring(this.offset);
        int semicolonIndex = unread.indexOf(';');
        if(semicolonIndex == -1 ){
            error();
            return "";
        } else {
            int objStart = this.offset - 1;
            int objEnd = this.offset + semicolonIndex + 1;
            this.offset = objEnd;
            String descriptor = raw.substring(objStart,objEnd);
            return descriptor;
        }
    }

    private String parseArrayType() {
        int arrStart = this.offset - 1;
        parseFieldType();
        int arrEnd = this.offset;
        String descriptor = this.raw.substring(arrStart, arrEnd);
        return descriptor;
    }

    private void parseReturnType() {
        if(readUint8() == 'V'){
            this.parsed.returnType = "V";
            return;
        }

        unreadUint8();
        String t = parseFieldType();
        if(!"".equals(t)) {
            this.parsed.returnType = t;
            return;
        }
        error();
    }

    public void parseParamTypes() {
        for(;;){
            String t = parseFieldType();
            if(!"".equals(t)){
                this.parsed.addParameterType(t);
            } else {
                break;
            }
        }
    }
}