package com.maarketplace.helpers.validators;

import com.maarketplace.helpers.constants.FieldSizes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidators {

    private static final String PRODUCT_NAME_REGEX = "[^\\\\/:*?\"<>|]*";

    private static final Pattern PRODUCT_NAME_PATTERN = Pattern.compile(FieldValidators.PRODUCT_NAME_REGEX);

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8}$";

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(FieldValidators.PASSWORD_REGEX);

    public static Boolean passwordValidator(String password) {
        Matcher passwordMatcher = FieldValidators.PASSWORD_PATTERN.matcher(password);
        return passwordMatcher.matches();
    }

    public static Boolean productNameValidator(String productName) {
        Matcher productNameMatcher = FieldValidators.PRODUCT_NAME_PATTERN.matcher(productName);
        return productNameMatcher.matches() && productName.length() >= FieldSizes.PRODUCT_NAME_MIN_LENGTH && productName.length() <= FieldSizes.PRODUCT_NAME_MAX_LENGTH;
    }
}
