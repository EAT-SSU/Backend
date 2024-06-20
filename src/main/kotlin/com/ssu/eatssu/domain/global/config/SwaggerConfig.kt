package com.ssu.eatssu.domain.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .components(Components())
            .info(openApiDefinition())
    }

    private fun openApiDefinition(): Info {
        return Info()
            .title("EatSsu API")
            .description("API for EatSsu")
            .version("1.0.0")
    }
}