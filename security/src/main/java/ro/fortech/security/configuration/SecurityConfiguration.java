package ro.fortech.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
    @Bean
    public SecurityFilterChain securityConfig(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/customer/**").permitAll()
                .antMatchers("/account/**").authenticated()
                .antMatchers("/swagger-ui/**").authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();

        return httpSecurity.build();
    }
}
