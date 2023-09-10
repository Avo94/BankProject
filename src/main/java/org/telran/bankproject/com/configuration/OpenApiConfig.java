package org.telran.bankproject.com.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Bank Application API",
                description = "Bank Project", version = "1.0.0",
                contact = @Contact(
                        name = "Anton Vouk",
                        email = "avo94eu@gmail.com",
                        url = "https://github.com/Avo94"
                )
        )
)

@SecurityScheme(
        name = "basicauth",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)

public class OpenApiConfig {
//
}