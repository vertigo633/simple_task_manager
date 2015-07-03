package com.vertigo633.utils;

/**
 * Created by Vertigo633 on 12.06.2015.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static Pattern pattern;
    private static Matcher matcher;

    private static final String NAME_PATTERN = "^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$";

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PHONE_PATTERN = "\\d+";

    public static boolean validateEmail(final String e_mail) {

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(e_mail);
        return matcher.matches();
    }

    public static boolean validatePhone(final String phone_number) {
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(phone_number);
        return matcher.matches();
    }

    public static boolean validateName(final String name) {
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);
        return matcher.matches();
    }
}