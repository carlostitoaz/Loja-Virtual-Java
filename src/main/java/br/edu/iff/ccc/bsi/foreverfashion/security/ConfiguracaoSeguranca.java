package br.edu.iff.ccc.bsi.foreverfashion.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.Customizer;

@Configuration
public class ConfiguracaoSeguranca {

    @Bean
    public SecurityFilterChain filtroSeguranca(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
                .ignoringRequestMatchers("/api/**")
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame
                    .sameOrigin()
                )
            )
            .authorizeHttpRequests(autorizacao -> autorizacao
                .requestMatchers(
                    "/login", "/css/**", "/js/**", "/images/**",
                    "/h2-console/**"
                ).permitAll()
                //.requestMatchers("/api/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .formLogin(formulario -> formulario
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(sair -> sair
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder codificadorSenha() {
        return new BCryptPasswordEncoder();
    }
}
