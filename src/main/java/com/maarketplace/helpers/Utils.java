package com.maarketplace.helpers;

import com.maarketplace.model.Credentials;
import com.maarketplace.model.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Utils {

    public static Float roundNumberTo2Decimals(Float number) {
        if (number == null) {
            return 0.0F;
        }
        return Math.round(number * 100) / 100.0F;
    }

    public static Float calculateProductPrice(Product product, Integer quantity) {
        if (product == null || quantity == null || quantity <= 0) {
            return 0.0F;
        }
        return roundNumberTo2Decimals(product.getPrice() * quantity);
    }

    public static void cryptAndSaveUserCredentialsPassword(@NotNull Credentials credentials, @NotNull PasswordEncoder passwordEncoder) {
        if (credentials.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null when encoding.");
        }
        String encodedPassword = passwordEncoder.encode(credentials.getPassword());
        credentials.setPassword(encodedPassword);
    }


}
