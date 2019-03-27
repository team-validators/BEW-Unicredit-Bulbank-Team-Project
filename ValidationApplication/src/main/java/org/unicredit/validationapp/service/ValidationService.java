package org.unicredit.validationapp.service;

public interface ValidationService {
    Boolean ibanIsValid(String ibanCode);

    Boolean personalIdIsValid(String personalId);
}
