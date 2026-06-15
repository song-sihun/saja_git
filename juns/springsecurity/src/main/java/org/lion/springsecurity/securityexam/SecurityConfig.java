package org.lion.springsecurity.securityexam;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // 기본설정 아무런 설정 안한거
//        http.authorizeHttpRequests(
//                authorizeRequests -> authorizeRequests
//                        .anyRequest()
//                        .authenticated()
//        )
//        .formLogin(Customizer.withDefaults())
//        .httpBasic(Customizer.withDefaults())
//        .csrf(Customizer.withDefaults());

        //
        http.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/login", "/home", "/home/*").permitAll()
                        .anyRequest().authenticated()
        ).formLogin(form -> form
                .loginProcessingUrl("/login")
                .loginPage("/loginForm")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/home")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    log.info("Successfully logged in {}", authentication.getName());
                    response.setContentType("application/json");
                })
                .failureHandler((request, response, authentication) -> {
                    log.info("Failure logged in");
                    response.setContentType("application/json");
                })



        ).logout(logout -> logout
                .logoutUrl("/logout")
                .logoutUrl("/")
                .addLogoutHandler((request, response, authentication) -> {
                    log.info("Successfully logged out");
                    response.setContentType("application/json");
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        session.invalidate();
                    }

                })
                .deleteCookies("JSESSIONID")

        );



        return http.build();
    }
}
