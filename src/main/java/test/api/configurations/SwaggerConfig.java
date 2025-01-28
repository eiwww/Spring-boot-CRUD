package test.api.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	private final String securitySchemaName = "Bearer Authentication";

	@Bean
	public OpenAPI customizOpenAPI(){
		return new OpenAPI()
			.addSecurityItem(new SecurityRequirement()
				.addList(securitySchemaName))
			.components(new Components()
				.addSecuritySchemes(securitySchemaName, new SecurityScheme()
					.name(securitySchemaName)
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT")
				)
			);
	}
}
