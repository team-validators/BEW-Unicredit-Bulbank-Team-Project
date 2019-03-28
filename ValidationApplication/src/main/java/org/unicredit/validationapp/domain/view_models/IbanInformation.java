package org.unicredit.validationapp.domain.view_models;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class IbanInformation {
    public static final String VALID_IBAN_MESSAGE = "IBAN code is valid!";
    public static final String INVALID_IBAN_MESSAGE = "IBAN code is invalid!";

    private String code;
    private Set<String> errors;

    public IbanInformation() {
        this.errors = new HashSet<>();
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<String> getErrors() {
        return Collections.unmodifiableSet(this.errors);
    }

    public void setErrors(Set<String> errors) {
        this.errors = errors;
    }

    public void addError(String error) {
        this.errors.add(error);
    }
}
