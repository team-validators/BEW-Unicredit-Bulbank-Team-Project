package org.unicredit.validationapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.unicredit.validationapp.domain.enums.Gender;
import org.unicredit.validationapp.service.ValidationService;
import org.unicredit.validationapp.service.ValidationServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
public class ValidationServiceTests {
    private static final String VALID_PERSONAL_ID = "8812102461";
    private static final String INVALID_DATE_PERSONAL_ID = "9509402461";


    private ValidationService validationService;

    @Before
    public void init() {
        this.validationService = new ValidationServiceImpl();
    }

    @Test
    public void isValidPersonalIdFormat_validPersonalId_returnsTrue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method isValidFormatMethod = this.validationService.getClass().getDeclaredMethod("isValidPersonalIdFormat", String.class);
        isValidFormatMethod.setAccessible(true);
        boolean actualResult = (boolean) isValidFormatMethod.invoke(this.validationService, VALID_PERSONAL_ID);
        isValidFormatMethod.setAccessible(false);

        Assert.assertTrue(actualResult);
    }

    @Test
    public void isValidPersonalId_validPersonalId_returnsTrue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method isValidPersonalIdMethod = this.validationService.getClass().getDeclaredMethod("isValidPersonalId", String.class);
        isValidPersonalIdMethod.setAccessible(true);
        boolean actualResult = (boolean) isValidPersonalIdMethod.invoke(this.validationService, VALID_PERSONAL_ID);
        isValidPersonalIdMethod.setAccessible(false);

        Assert.assertTrue(actualResult);
    }

    @Test
    public void calculateAmount_validPersonalId_returnsValidNumber() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method calculateAmountMethod = this.validationService.getClass().getDeclaredMethod("calculateAmount", String.class);
        calculateAmountMethod.setAccessible(true);
        int actualResult = (int) calculateAmountMethod.invoke(this.validationService, VALID_PERSONAL_ID);
        calculateAmountMethod.setAccessible(false);

        int expectedResult = 0; //TODO
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getBirthDate_invalidDays_returnsNull() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getBirthDateMethod = this.validationService.getClass().getDeclaredMethod("getBirthDate", String.class);
        getBirthDateMethod.setAccessible(true);
        LocalDate localDate = (LocalDate) getBirthDateMethod.invoke(this.validationService, INVALID_DATE_PERSONAL_ID); // --> 40/09/1995
        Assert.assertNull(localDate);
        getBirthDateMethod.setAccessible(false);
    }

    @Test
    public void getGender_validPersonalId_returnsMale() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getGenderMethod = this.validationService.getClass().getDeclaredMethod("getGender", String.class);
        getGenderMethod.setAccessible(true);
        Gender gender = (Gender) getGenderMethod.invoke(this.validationService, VALID_PERSONAL_ID);
        getGenderMethod.setAccessible(false);

        Assert.assertEquals(Gender.MALE, gender);
    }

    @Test
    public void getGender_validPersonalId_returnsFemale() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getGenderMethod = this.validationService.getClass().getDeclaredMethod("getGender", String.class);
        getGenderMethod.setAccessible(true);
        Gender gender = (Gender) getGenderMethod.invoke(this.validationService, VALID_PERSONAL_ID); //TODO find a valid id of a female
        getGenderMethod.setAccessible(false);

        Assert.assertEquals(Gender.FEMALE, gender);
    }

    @Test
    public void getCityName_validPersonalId_returnsRightCity() {
        //TODO
    }

    @Test
    public void getPersonalIdBirthDateString_validPersonalId() {
        //TODO
    }


}
