package io.library.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Library API",
                version = "1.0.0",
                description = "API for managing library resources, including books, authors, and users.",
                contact = @Contact(
                        name = "Thiago da Silva Rangel",
                        email = "tsrangel1989@gmail.com"
                )
        ),
        security = {
                @SecurityRequirement(name = "basicAuth")
        }
)
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {
    @Bean
    public OpenApiCustomizer customizer() {
        return openApi -> {
            Parameter page = new Parameter()
                    .in("query")
                    .name("page")
                    .description("Page number for pagination")
                    .example("0")
                    .required(false)
                    .schema(new IntegerSchema()._default(0));

            Parameter size = new Parameter()
                    .in("query")
                    .name("size")
                    .description("Page size for pagination")
                    .example("10")
                    .required(false)
                    .schema(new IntegerSchema()._default(10));

            Parameter sort = new Parameter()
                    .in("query")
                    .name("sort")
                    .description("Sorting criteria in the format: property(asc|desc). Default sort order is ascending.")
                    .example("asc")
                    .required(false)
                    .schema(new StringSchema()._default("asc"));

            openApi.getComponents()
                    .addParameters("page", page)
                    .addParameters("size", size)
                    .addParameters("sort", sort);
        };
    }
}
