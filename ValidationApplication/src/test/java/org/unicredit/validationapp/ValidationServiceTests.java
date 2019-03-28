package org.unicredit.validationapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.unicredit.validationapp.service.ValidationService;
import org.unicredit.validationapp.service.ValidationServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
public class ValidationServiceTests {
    private ValidationService validationService;

    @Before
    public void init() {
        this.validationService = new ValidationServiceImpl();
    }

    @Test
    public void getBirthDate_invalidDays_returnsNull() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getBirthDateMethod = this.validationService.getClass().getDeclaredMethod("getBirthDate", String.class);
        getBirthDateMethod.setAccessible(true);
        LocalDate localDate = (LocalDate) getBirthDateMethod.invoke(this.validationService, "9509401111"); // --> 40/09/1995
        Assert.assertNull(localDate);
        getBirthDateMethod.setAccessible(false);
    }
}
