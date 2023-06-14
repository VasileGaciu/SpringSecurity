package ro.fortech.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ro.fortech.security.configuration.securityfilters.AuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration{

    @Autowired
    private AuthFilter authFilter;
    @Bean
    public SecurityFilterChain securityConfig(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .headers().frameOptions()
                .disable()
                .and()
                .authorizeRequests()
                .antMatchers("/customer/**").hasRole("USER")
                .mvcMatchers("/auth/token").permitAll()
                .mvcMatchers("/auth/refresh").authenticated()
                .mvcMatchers("/account/**").hasRole("ADMIN")
                .and()
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .disable()
                .httpBasic().disable();

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
    }
}
