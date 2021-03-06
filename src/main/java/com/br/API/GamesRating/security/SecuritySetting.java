package com.br.API.GamesRating.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecuritySetting extends WebSecurityConfigurerAdapter {

  private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};
  private static final String[] PUBLIC_MATCHERS_GET = {"/game/**"};
  private static final String[] PUBLIC_MATCHERS_POST = {"/user/**", "/auth/forgot/**"};
  @Autowired private Environment environment;
  @Autowired private UserDetailsService userDetailsService;
  @Autowired private JWTUtil jwtUtil;

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
        .antMatchers(
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/**",
            "/swagger-ui.html",
            "/webjars/**");
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {

    if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
      httpSecurity.headers().frameOptions().disable();
    }

    httpSecurity.cors().and().csrf().disable();
    httpSecurity
        .authorizeRequests()
        .antMatchers(PUBLIC_MATCHERS)
        .permitAll()
        .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET)
        .permitAll()
        .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST)
        .permitAll()
        .anyRequest()
        .authenticated();
    httpSecurity.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
    httpSecurity.addFilter(
        new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
    httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("*/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
