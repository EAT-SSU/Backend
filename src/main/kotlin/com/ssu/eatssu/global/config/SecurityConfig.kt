package com.ssu.eatssu.global.config

import com.ssu.eatssu.global.auth.jwt.JwtAuthenticationFilter
import com.ssu.eatssu.global.auth.jwt.JwtTokenProvider
import com.ssu.eatssu.global.auth.jwt.handler.JwtAccessDeniedHandler
import com.ssu.eatssu.global.auth.user.application.AuthenticationManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfigurationSource
import ssu.eatssu.global.handler.JwtAuthenticationEntryPoint

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManager: AuthenticationManager,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    private val corsConfigurationSource: CorsConfigurationSource
) {

    companion object {
        private val RESOURCE_LIST = arrayOf(
            "/swagger-ui/**",
            "/api-docs/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/admin/img/**",
            "/css/**",
            "/js/**",
            "/favicon.ico",
            "/error/**",
            "/webjars/**",
            "/h2-console/**"
        )

        private val AUTH_WHITELIST = arrayOf(
            "/",
            "/api/oauths/kakao",
            "/api/oauths/apple",
            "/api/menus/**",
            "/api/meals/**",
            "/admin/login",
            "/api/reviews",
            "/api/reviews/menus/**",
            "/api/reviews/meals/**"
        )

        private val ADMIN_PAGE_LIST = arrayOf(
            "/admin/**"
        )
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { c -> c.disable() }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers(*AUTH_WHITELIST).permitAll()
                    .requestMatchers(*RESOURCE_LIST).permitAll()
                    .requestMatchers(*ADMIN_PAGE_LIST).hasRole("ADMIN")
                    .anyRequest().authenticated()
            }

            .addFilterBefore(
                JwtAuthenticationFilter(
                    jwtTokenProvider,
                    authenticationManager
                ),
                UsernamePasswordAuthenticationFilter::class.java
            )

            .cors { corsConfigurationSource }

            .exceptionHandling {
                it.accessDeniedHandler(jwtAccessDeniedHandler)
            }

        return http.build()
    }
}