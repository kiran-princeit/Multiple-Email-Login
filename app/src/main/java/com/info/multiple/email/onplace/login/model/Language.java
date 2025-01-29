package com.info.multiple.email.onplace.login.model;

public class Language {
    private String code;
    private String name;


    private int flags;

    public Language(String code, String name, int flags) {
        this.code = code;
        this.name = name;
        this.flags = flags;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getFlags() {
        return flags;
    }
}
