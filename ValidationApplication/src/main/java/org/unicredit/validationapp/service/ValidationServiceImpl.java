package org.unicredit.validationapp.service;

import org.springframework.stereotype.Service;
import org.unicredit.validationapp.domain.enums.Gender;
import org.unicredit.validationapp.domain.view_models.IbanInformation;
import org.unicredit.validationapp.domain.view_models.PersonalIdInformation;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationServiceImpl implements ValidationService {
    public static final int BULGARIAN_IBAN_CODE_LENGTH = 22;
    public static final String IBAN_REGEX = "^[A-Z]{2}[0-9]{2}[A-Z]{4}[0-9]{6}[A-Z0-9]{8}$";

    public static final String PERSONAL_ID_REGEX = "^[0-9]{10}$";
    private static Set<String> VALID_NATIONALITIES;

    public static Set<String> GET_VALID_NATIONALITIES() {
        if (VALID_NATIONALITIES == null) {
            VALID_NATIONALITIES = new HashSet<>();
            VALID_NATIONALITIES.add("BG");
        }

        return VALID_NATIONALITIES;
    }

    public static final char[] WEIGHTS = {2, 4, 8, 5, 10, 9, 7, 3, 6};

    public static final String DATE_SEPARATOR = "/";
    public static final String DATE_FORMAT = String.format("dd%sMM%syyyy", DATE_SEPARATOR, DATE_SEPARATOR);

    public static final String INVALID_FORMAT_MESSAGE = "Personal ID format is not valid!";
    public static final String INVALID_ID_MESSAGE = "Personal ID is invalid!";
    public static final String INVALID_DATE_OF_BIRTH = "Extracted birth date from personal ID is not valid!";

    @Override
    public IbanInformation ibanIsValid(String ibanCode) {
        IbanInformation ibanInformation = new IbanInformation();
        ibanInformation.setCode(ibanCode);

        String reformatedIbanCode = this.reformatIban(ibanCode);

        if (!this.isValidIbanFormat(reformatedIbanCode)) {
            ibanInformation.addError("Invalid format!");
            return ibanInformation;
        }

        if (!this.isValidNationality(reformatedIbanCode)) {
            ibanInformation.addError("Invalid nationality!");
            return ibanInformation;
        }

        if (!this.isValidIbanControlNumber(reformatedIbanCode)) {
            ibanInformation.addError("Invalid control number!");
            return ibanInformation;
        }

        return ibanInformation;
    }

    @Override
    public PersonalIdInformation personalIdIsValid(String personalId) {
        PersonalIdInformation result = new PersonalIdInformation();
        result.setCode(personalId);

        boolean isValidFormat = this.isValidPersonalIdFormat(personalId);
        if (!isValidFormat) {
            result.addErrorMessage(INVALID_FORMAT_MESSAGE);
        }

        try {
            boolean isValidPersonalId = this.isValidPersonalId(personalId);
            if (!isValidPersonalId) {
                result.addErrorMessage(INVALID_ID_MESSAGE);
            }

            LocalDate birthDate = this.getBirthDate(personalId);
            if (birthDate == null) {
                result.addErrorMessage(INVALID_DATE_OF_BIRTH);
            }

            Gender gender = this.getGender(personalId);
            result.setGender(gender);

            String city = this.getCityName(personalId);
            result.setCity(city);

            int numberOfBoysBornBefore = this.calculateNumberOfBoys(personalId);
            result.setNumberOfBoysBornBefore(numberOfBoysBornBefore);

            int numberOfGirlsBornBefore = this.calculateNumberOfGirls(personalId);
            result.setNumberOfGirlsBornBefore(numberOfGirlsBornBefore);
        } catch (NumberFormatException | IndexOutOfBoundsException nfe) {

        }

        return result;
    }

    private int calculateNumberOfBornKids(String personalId) {
        return Integer.parseInt(personalId.substring(7, 9));
    }

    private int calculateNumberOfBoys(String personalId) {
        int numberOfBornKids = this.calculateNumberOfBornKids(personalId);
        return numberOfBornKids / 2 + numberOfBornKids;
    }

    private int calculateNumberOfGirls(String personalId) {
        int numberOfBornKids = this.calculateNumberOfBornKids(personalId);
        return numberOfBornKids / 2 + numberOfBornKids % 2;
    }

    private boolean isValidIbanFormat(String ibanCode) {
        Pattern ibanRegexPattern = Pattern.compile(IBAN_REGEX);
        Matcher matcher = ibanRegexPattern.matcher(ibanCode);

        return matcher.matches();
    }

    private boolean isValidIbanControlNumber(String ibanCode) {
        String reformattedCode = ibanCode.substring(4) + ibanCode.substring(0, 4);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BULGARIAN_IBAN_CODE_LENGTH; i++) {
            sb.append(Character.digit(reformattedCode.charAt(i), 36));
        }

        BigInteger bigInt = new BigInteger(sb.toString());
        int modResult = bigInt.mod(BigInteger.valueOf(97)).intValue();
        return modResult == 1;
    }

    private boolean isValidNationality(String ibanCode) {
        return GET_VALID_NATIONALITIES().contains(ibanCode.substring(0, 2));
    }

    private String reformatIban(String ibanCode) {
        return ibanCode.toUpperCase().replace(" ", "").replace("-", "");
    }

    private boolean isValidPersonalIdFormat(String personalId) {
        Pattern personalIdRegexPattern = Pattern.compile(PERSONAL_ID_REGEX);
        Matcher matcher = personalIdRegexPattern.matcher(personalId);

        return matcher.matches();
    }

    private int calculateAmount(String personalId) {
        int amount = 0;

        for (int i = 0; i < personalId.length() - 1; i++) {
            int digitValue = Integer.parseInt("" + personalId.charAt(i));
            amount = amount + (digitValue * WEIGHTS[i]);
        }

        return amount;
    }

    private boolean isValidPersonalId(String personalId) {
        try {
            int amount = this.calculateAmount(personalId);
            int result = amount % 11;
            int ninthDigit = Integer.parseInt("" + personalId.charAt(9));

            if (result == ninthDigit || (result == 10 && ninthDigit == 0))
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    private Gender getGender(String personalId) {
        int genderDefiningNumber = Integer.parseInt("" + personalId.charAt(8));

        if (genderDefiningNumber % 2 == 0)
            return Gender.MALE;
        else
            return Gender.FEMALE;
    }

    private String getCityName(String personalId) {
        int regionCode = Integer.parseInt(personalId.substring(6, 9));

        String result = "";
        if (regionCode == 43)
            result = "Blagoevgrad";

        if (regionCode > 43 && regionCode < 93)
            result = "Burgas";


        if (regionCode >= 93 && regionCode < 139)
            result = "Varna";


        if (regionCode >= 139 && regionCode < 169)
            result = "Veliko Tarnovo";

        if (regionCode >= 169 && regionCode < 183)
            result = "Vidin";

        if (regionCode > 183 && regionCode <= 217)
            result = "Vratsa";

        if (regionCode > 217 && regionCode <= 233)
            result = "Gabrovo";

        if (regionCode > 233 && regionCode <= 281)
            result = "Kurdjali";


        if (regionCode > 281 && regionCode <= 301)
            result = "Kiustendil";


        if (regionCode > 301 && regionCode <= 319)
            result = "Lovech";


        if (regionCode > 319 && regionCode <= 341)
            result = "Montana";

        if (regionCode > 341 && regionCode <= 377)
            result = "Pazardjik";

        if (regionCode > 377 && regionCode <= 395)
            result = "Pernik";

        if (regionCode > 395 && regionCode <= 435)
            result = "Pleven";

        if (regionCode > 435 && regionCode <= 501)
            result = "Plovdiv";

        if (regionCode > 501 && regionCode <= 527)
            result = "Razgrad";

        if (regionCode > 527 && regionCode <= 555)
            result = "Ruse";

        if (regionCode > 555 && regionCode <= 575)
            result = "Silistra";

        if (regionCode > 575 && regionCode <= 601)
            result = "Sliven";

        if (regionCode > 601 && regionCode <= 623)
            result = "Smolian";

        if (regionCode > 623 && regionCode <= 721)
            result = "Sofia-grad";


        if (regionCode > 721 && regionCode <= 751)
            result = "Sofia-okrug";


        if (regionCode > 751 && regionCode <= 789)
            result = "Stara zagora";


        if (regionCode > 729 && regionCode <= 821)
            result = "Dobritch";


        if (regionCode > 821 && regionCode <= 843)
            result = "Targovishte";


        if (regionCode > 843 && regionCode <= 871)
            result = "Haskovo";


        if (regionCode > 871 && regionCode <= 903)
            result = "Shumen";

        if (regionCode > 903 && regionCode <= 915)
            result = "Yambul";

        if (regionCode > 925 && regionCode <= 999)
            result = "Neizvesten";

        return result;
    }

    private String getPersonalIdBirthDateString(String personalId) {
        int year = Integer.parseInt(personalId.substring(0, 2));
        int month = Integer.parseInt(personalId.substring(2, 4));
        int day = Integer.parseInt(personalId.substring(4, 6));

        if (month > 20 && month < 33) {
            year += 1800;
            month -= 20;
        } else if (month > 40 && month < 53) {
            year += 2000;
            month -= 40;
        } else if (month > 0 && month < 13)
            year += 1900;

        return String.format("%d%s%d%s%d", day, DATE_SEPARATOR, month, DATE_SEPARATOR, year);
    }

    private LocalDate getBirthDate(String personalId) {
        String birthDateString = this.getPersonalIdBirthDateString(personalId);
        LocalDate birthDate = null;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try {
            birthDate = LocalDate.parse(birthDateString, dateTimeFormatter);
            return birthDate;
        } catch (DateTimeParseException dtps) {
            return null;
        }
    }

    public boolean isValidDate(String dateToValidate, String dateFromat) {

        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);

        try {
            Date date = sdf.parse(dateToValidate);
            System.out.println(date);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }
}
