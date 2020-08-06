package com.zsx.enumeration;

public enum ErrorLevel {

    WARNING("warn"),
    ERROR("error"),
    FATAL("fatal");

    private final String value;

    ErrorLevel(String value) {
        this.value = value;
    }

    public static ErrorLevel get(String value) {
        for (ErrorLevel level : ErrorLevel.values()) {
            if (level.value.equals(value)) {
                return level;
            }
        }
        return WARNING;
    }

}
