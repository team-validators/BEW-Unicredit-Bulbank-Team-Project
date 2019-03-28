package org.unicredit.validationapp.domain.errors;

public class InvalidPersonalId extends IllegalArgumentException {
    public static final String INVALID_FORMAT_MESSAGE = "Personal ID format is not valid!";
    public static final String INVALID_ID_MESSAGE = "Personal ID is invalid!";
    public static final String INVALID_DATE_OF_BIRTH = "Extracted birth date from personal ID is not valid!";

    public InvalidPersonalId(String s) {
        super(s);
    }
}
