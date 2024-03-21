package by.haardd.cclog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "CClog",
        version = "1.0",
        description = "Computer center logging system",
        contact = @Contact(name = "Maxim Galitskiy")),
        security = {@SecurityRequirement(name = "bearerToken"), @SecurityRequirement(name = "cookie")})
@SecuritySchemes({
        @SecurityScheme(name = "cookie", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, paramName = "cookie")
})
public class OpenApiConfiguration {

}
