package com.example.scanner;

public class ItemClass {
    private String serialNo;
    private String code;

    public ItemClass(String serialNo, String code) {
        this.serialNo = serialNo;
        this.code = code;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public String getCode() {
        return code;
    }
}
