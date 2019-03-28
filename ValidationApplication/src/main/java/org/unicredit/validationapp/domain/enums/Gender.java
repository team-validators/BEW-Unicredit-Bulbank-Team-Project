package org.unicredit.validationapp.domain.enums;

public enum Gender {
    MALE, FEMALE;

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1);
    }
}
