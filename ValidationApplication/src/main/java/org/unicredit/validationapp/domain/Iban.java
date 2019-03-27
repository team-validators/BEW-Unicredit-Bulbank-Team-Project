package org.unicredit.validationapp.domain;

public class Iban {
    public static final String VALID_IBAN_MESSAGE = "IBAN code is valid!";
    public static final String INVALID_IBAN_MESSAGE = "IBAN code is invalid!";

    private String code;
    private Boolean isValid;

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
