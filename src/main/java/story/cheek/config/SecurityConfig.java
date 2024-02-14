package story.cheek.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import story.cheek.member.repository.MemberRepository;
import story.cheek.security.*;
import story.cheek.security.oauth2.OAuth2AuthenticationFailureHandler;
import story.cheek.security.oauth2.OAuth2AuthenticationSuccessHandler;
import story.cheek.security.util.JwtExtractor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String ADMIN = "ADMIN";
    private final TokenProvider tokenProvider;
    private final JwtExtractor jwtExtractor;
    private final MemberRepository memberRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandlingConfigurer -> {
                    exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                    exceptionHandlingConfigurer.accessDeniedHandler(jwtAccessDeniedHandler);
                })
                .headers(httpSecurityHeadersConfigurer ->
                        httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .sessionManagement(SessionManagementConfigurer ->
                        SessionManagementConfigurer.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers("/").permitAll()
                                        .requestMatchers("/login", "/error").permitAll()
                                        .requestMatchers("/api/v1/refresh").permitAll()
                                        .requestMatchers("/v3/**", "swagger-ui/**").permitAll()
                                        .anyRequest().authenticated()
                )
                .oauth2Login(oAuth2LoginConfigurer -> {
                    oAuth2LoginConfigurer.authorizationEndpoint(authorizationEndpointConfig -> {
                        authorizationEndpointConfig.baseUri("/api/oauth2/authorize");
                        authorizationEndpointConfig.authorizationRequestRepository(cookieAuthorizationRequestRepository());
                    });
                    oAuth2LoginConfigurer.redirectionEndpoint(redirectionEndpointConfig ->
                            redirectionEndpointConfig.baseUri("/api/oauth2/callback/*")
                    );
                    oAuth2LoginConfigurer.userInfoEndpoint(userInfoEndpointConfig ->
                            userInfoEndpointConfig.userService(customOAuth2UserService)
                    );
                    oAuth2LoginConfigurer.successHandler(oAuth2AuthenticationSuccessHandler);
                    oAuth2LoginConfigurer.failureHandler(oAuth2AuthenticationFailureHandler);
                })
                .addFilterBefore(new TokenAuthenticationFilter(tokenProvider, jwtExtractor, memberRepository), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
