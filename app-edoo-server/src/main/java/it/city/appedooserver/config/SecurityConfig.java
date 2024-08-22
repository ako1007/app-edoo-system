package it.city.appedooserver.config;

import it.city.appedooserver.security.JwtErrors;
import it.city.appedooserver.security.JwtTokenFilter;
import it.city.appedooserver.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final
    AuthService authService;

    final
    JwtErrors jwtErrors;

    public SecurityConfig(@Lazy AuthService authService, JwtErrors jwtErrors) {
        this.authService = authService;
        this.jwtErrors = jwtErrors;
    }

    JwtTokenFilter jwtFilter() {
        return new JwtTokenFilter();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtErrors)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/v2/api-docs/**",
                        "/api-docs/**",
                        "/api-docs.yaml",
                        "/api-docs.yaml/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/api/v2/**",
                        "/csrf",
                        "/webjars/**")
                .permitAll()
                .antMatchers("/api/attachment/**", "/api/aware/**", "/api/category/**", "/api/group/**", "/api/student/**", "/api/auth/**","/api/payment/**")
                .permitAll()
                .anyRequest()
                .authenticated();


//        Mana shu yirda yullar tokenga qarab ochiladi
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }



}
