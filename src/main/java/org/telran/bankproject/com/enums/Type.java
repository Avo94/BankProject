package org.telran.bankproject.com.enums;

public enum Type {

    STANDARD,
    PREMIUM,
    SUCCESSFUL,
    FAILED;

    public double getRate() {
        return ((ordinal() * 5) + 3.0);
    }

    public int getLimit() {
        return (ordinal() * 1000000);
    }
}