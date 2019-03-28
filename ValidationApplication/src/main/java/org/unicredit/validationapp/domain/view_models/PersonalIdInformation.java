package org.unicredit.validationapp.domain.view_models;

import org.unicredit.validationapp.domain.enums.Gender;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PersonalIdInformation {
    public static final String VALID_PERSONAL_ID_MESSAGE = "Personal ID is valid!";
    public static final String INVALID_PERSONAL_ID_MESSAGE = "Personal ID is NOT valid!";

    private String code;
    private Gender gender;
    private String city;
    private LocalDate dateOfBirth;
    private Integer numberOfBoysBornBefore;
    private Integer numberOfGirlsBornBefore;
    private Set<String> errors;

    public PersonalIdInformation() {
        this.errors = new HashSet<>();
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getNumberOfBoysBornBefore() {
        return this.numberOfBoysBornBefore;
    }

    public void setNumberOfBoysBornBefore(Integer numberOfBoysBornBefore) {
        this.numberOfBoysBornBefore = numberOfBoysBornBefore;
    }

    public Integer getNumberOfGirlsBornBefore() {
        return this.numberOfGirlsBornBefore;
    }

    public void setNumberOfGirlsBornBefore(Integer numberOfGirlsBornBefore) {
        this.numberOfGirlsBornBefore = numberOfGirlsBornBefore;
    }

    public Set<String> getErrors() {
        return Collections.unmodifiableSet(this.errors);
    }

    public void setErrors(Set<String> errors) {
        this.errors = errors;
    }
    
    public void addErrorMessage(String errorMessage) {
        this.errors.add(errorMessage);
    }
}
