package org.unicredit.validationapp.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationServiceImpl implements ValidationService {
    private static final int BULGARIAN_IBAN_CODE_LENGTH = 22;
    private static final String IBAN_REGEX = "^[A-Z]{2}[0-9]{2}[A-Z]{4}[0-9]{6}[A-Z0-9]{8}$";

    @Override
    public Boolean ibanIsValid(String ibanCode) {
        ibanCode = ibanCode.toUpperCase().replace(" ", "").replace("-", "");

        if (ibanCode.length() != BULGARIAN_IBAN_CODE_LENGTH)
            return false;

        if (!ibanCode.startsWith("BG"))
            return false;

        Pattern startKeyEndKeyRegex = Pattern.compile(IBAN_REGEX);
        Matcher matcher = startKeyEndKeyRegex.matcher(ibanCode);

        if (!matcher.matches()) {
            return false;
        }

        if (!this.validateIban(ibanCode)) {
            return false;
        }

        return true;
    }

    @Override
    public Boolean personalIdIsValid(String personalId) { //TODO turn into class and visualize results in webpage
        List<String> results = new ArrayList<>(); //TODO also chop code into methods for unit testing later

        char[] weights = {2, 4, 8, 5, 10, 9, 7, 3, 6};
//        System.out.print("Enter next digits your ID number: ");
//        Scanner keyboard = new Scanner(System.in);
        String number = personalId;
        char[] ofm = number.toCharArray();
        Character[] id = new Character[ofm.length];

        int region = Integer.parseInt(number.substring(6, 9));
        int amount = 0;

        if (ofm.length == 10) {
//            results.add("This ID number has 10 characters"); //is valid
            System.out.println("This ID number has 10 characters"); //is valid
            for (int i = 0; i < ofm.length - 1; i++) {
                id[i] = ofm[i];
//                System.out.print(id[i] + " ");
                int length = id.length;
                if (Character.isDigit(id[i])) {
                    int digitValue = Integer.parseInt("" + id[i]);
                    amount = amount + (digitValue * weights[i]);
                } else {
                    System.out.println("character is not a digit"); //not valid
                    break;
                }
            }
        } else {
//            results.add("This ID number hasn't 10 digits.");
            System.out.println("This ID number hasn't 10 digits.");
//            return results;
        }
//        System.out.println("Checking of the control number");
//        System.out.println(amount);
        int result = 0;
        result = amount % 11;
        if (result == Integer.parseInt("" + number.charAt(9))) {
            System.out.println("ID number is correct");

        } else if ((result == 10) && Integer.parseInt("" + number.charAt(9)) == 0) {
            System.out.println("ID number is correct");
        } else {
            System.out.println("ID number is not correct");
        }

        //TODO gender
        char gender;
        int genderNumber = Integer.parseInt(number.substring(8, 9));
        if (genderNumber % 2 == 0)
            gender = 'm';
        else
            gender = 'f';

        System.out.println("Gender is " + gender);

        String grad = this.getCityNameFromCode(region);
        System.out.println(grad);

        int year = Integer.parseInt(number.substring(0, 2));
        int month = Integer.parseInt(number.substring(2, 4));
        int day = Integer.parseInt(number.substring(4, 6));

        if (month > 20 && month < 33) {
            year += 1800;
            month -= 20;
        } else if (month > 40 && month < 53) {
            year += 2000;
            month -= 40;
        } else if (month > 0 && month < 13)
            year += 1900;

        String dateFormat = "yyyy-MM-dd";
        String dateToValidate = String.format("%d-%d-%d", year, month, day);
        boolean dateIsValid = this.isThisDateValid(dateToValidate, dateFormat);
        if (!dateIsValid)
            System.out.println("Date is NOT valid");

//        int numberOfBornKids = Integer.parseInt(number.substring(7, 9));
//        int numberOfGirls = numberOfBornKids / 2 + numberOfBornKids % 2;
//        int numberOfBoys = numberOfBornKids / 2 + numberOfBornKids;
//        System.out.print("Number of boys: " + numberOfBoys);
//        System.out.print("Number of girls: " + numberOfGirls);


        if (personalId.equals("123"))
            return true;

        return false;
    }

    private boolean validateIban(String ibanCode) {
        String reformattedCode = ibanCode.substring(4) + ibanCode.substring(0, 4);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BULGARIAN_IBAN_CODE_LENGTH; i++) {
            sb.append(Character.digit(reformattedCode.charAt(i), 36));
        }

        BigInteger bigInt = new BigInteger(sb.toString());
        int modResult = bigInt.mod(BigInteger.valueOf(97)).intValue();
        return modResult == 1;
    }

    private String getCityNameFromCode(int code) {
        String result = "";
        if (code == 43)
            result = "Blagoevgrad";

        if (code > 43 && code < 93)
            result = "Burgas";


        if (code >= 93 && code < 139)
            result = "Varna";


        if (code >= 139 && code < 169)
            result = "Veliko Tarnovo";

        if (code >= 169 && code < 183)
            result = "Vidin";

        if (code > 183 && code <= 217)
            result = "Vratsa";

        if (code > 217 && code <= 233)
            result = "Gabrovo";

        if (code > 233 && code <= 281)
            result = "Kurdjali";


        if (code > 281 && code <= 301)
            result = "Kiustendil";


        if (code > 301 && code <= 319)
            result = "Lovech";


        if (code > 319 && code <= 341)
            result = "Montana";

        if (code > 341 && code <= 377)
            result = "Pazardjik";

        if (code > 377 && code <= 395)
            result = "Pernik";

        if (code > 395 && code <= 435)
            result = "Pleven";

        if (code > 435 && code <= 501)
            result = "Plovdiv";

        if (code > 501 && code <= 527)
            result = "Razgrad";

        if (code > 527 && code <= 555)
            result = "Ruse";

        if (code > 555 && code <= 575)
            result = "Silistra";

        if (code > 575 && code <= 601)
            result = "Sliven";

        if (code > 601 && code <= 623)
            result = "Smolian";

        if (code > 623 && code <= 721)
            result = "Sofia-grad";


        if (code > 721 && code <= 751)
            result = "Sofia-okrug";


        if (code > 751 && code <= 789)
            result = "Stara zagora";


        if (code > 729 && code <= 821)
            result = "Dobritch";


        if (code > 821 && code <= 843)
            result = "Targovishte";


        if (code > 843 && code <= 871)
            result = "Haskovo";


        if (code > 871 && code <= 903)
            result = "Shumen";

        if (code > 903 && code <= 915)
            result = "Yambul";

        if (code > 925 && code <= 999)
            result = "Neizvesten";

        return result;
    }


    public boolean isThisDateValid(String dateToValidate, String dateFromat) {

        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
            System.out.println(date);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }


}
