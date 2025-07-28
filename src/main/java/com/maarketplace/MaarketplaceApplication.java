package com.maarketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MaarketplaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaarketplaceApplication.class, args);
    }

}
