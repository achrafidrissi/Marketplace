package com.maarketplace.helpers.validators;

import java.time.LocalDateTime;

public class TypeValidators {

    public static boolean validateTimestamp(LocalDateTime timestamp) {
        return timestamp != null && (timestamp.isBefore(LocalDateTime.now()) || timestamp.isEqual(LocalDateTime.now()));
    }

    public static boolean validateString(String string) {
        return string != null && !string.isBlank() && !string.isEmpty();
    }
}
