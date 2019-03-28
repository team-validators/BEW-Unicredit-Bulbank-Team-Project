package org.unicredit.validationapp.service;

import org.unicredit.validationapp.domain.view_models.IbanInformation;
import org.unicredit.validationapp.domain.view_models.PersonalIdInformation;

public interface ValidationService {
    IbanInformation ibanIsValid(String ibanCode);

    PersonalIdInformation personalIdIsValid(String personalId);
}
