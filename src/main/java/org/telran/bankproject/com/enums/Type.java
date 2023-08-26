package org.telran.bankproject.com.enums;

public enum Type {

    STANDARD,
    PREMIUM,
    SUCCESSFUL,
    FAILED;

    public double getRate() {
        return ((ordinal() + 1) * 8);
    }

    public int getLimit() {
        return ((ordinal() + 1) * 500000);
    }
}