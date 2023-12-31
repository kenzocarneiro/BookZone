package fr.insacvl.asl.bcn.bookzone.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers(PathRequest.toH2Console())
            ) // https://jessitron.com/2020/06/15/spring-security-for-h2-console/
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/", "/register", "/test", "/public/**", "/webjars/**", "/css/**", "/favicon.ico","/ouvrage/**", "/exemplaire/**", "/error/**").permitAll()
                    .requestMatchers("/welcome").hasAnyAuthority("ROLE_CLIENT", "ROLE_LIBRAIRE", "ROLE_ADMIN")
                    .requestMatchers("/client/**").hasAnyAuthority("ROLE_CLIENT")
                    .requestMatchers("/libraire/**").hasAnyAuthority("ROLE_LIBRAIRE")
                    .requestMatchers("/admin/**", "/console/**").hasAuthority("ROLE_ADMIN")
                    .requestMatchers("/panier/**").hasAnyAuthority("ROLE_CLIENT", "ROLE_ANONYMOUS")
                    .anyRequest().authenticated()
            )
            .formLogin(login -> login
                    .loginPage("/login")
                    .loginProcessingUrl("/processLogin")
                    .defaultSuccessUrl("/welcome", true)
                    .failureUrl("/login-error")
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/processLogout")
                    .logoutSuccessUrl("/")
                    .permitAll()
            )
            .headers(headers -> headers
                    .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
