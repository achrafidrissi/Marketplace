package com.maarketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.ExternalDocumentation;

@EnableJpaAuditing
@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "BAM Marketplace API",
                description = "REST API de la marketplace de Bank Al-Maghrib permettant de vendre des pièces anciennes, produits personnalisés, etc.",
                version = "v1",
                contact = @Contact(
                        name = "Équipe Technique BAM",
                        email = "tech@bam.ma",
                        url = "https://www.bkam.ma"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Documentation fonctionnelle BAM",
                url = "https://www.bkam.ma/marketplace-docs"
        )
)
public class MaarketplaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaarketplaceApplication.class, args);
    }

}
