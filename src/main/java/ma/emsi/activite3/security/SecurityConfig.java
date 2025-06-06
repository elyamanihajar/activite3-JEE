package ma.emsi.activite3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN").build()
        );

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //httpSecurity.formLogin();
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/webjars/**", "/h2-console/**").permitAll());
        httpSecurity.rememberMe(remember -> remember
                .key("uniqueAndSecret")
                .tokenValiditySeconds(7 * 24 * 60 * 60) // 1 semaine
                .rememberMeParameter("remember-me") // facultatif, nom du paramètre checkbox
        );
        httpSecurity.formLogin(form -> form.defaultSuccessUrl("/", true).loginPage("/login").permitAll());
        httpSecurity.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/user/**").hasRole("USER"));
        httpSecurity.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/admin/**").hasRole("ADMIN"));
        httpSecurity.authorizeHttpRequests((authorize) ->
                authorize.anyRequest().authenticated());
        httpSecurity.exceptionHandling(exception -> exception.accessDeniedPage("/notAuthorized"));
        return httpSecurity.build();
    }
}
