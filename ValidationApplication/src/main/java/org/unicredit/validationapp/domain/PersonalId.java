package org.unicredit.validationapp.domain;

import java.time.LocalDate;

public class PersonalId {
    public static final String VALID_PERSONAL_ID_MESSAGE = "Personal ID is valid!";
    public static final String INVALID_PERSONAL_ID_MESSAGE = "Personal ID is NOT valid!";

    private String code;
    private Boolean isValid;
    private char gender;
    private String city;
    private LocalDate dateOfBirth;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getValid() {
        return this.isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }
}
