package com.example.web.config;

import com.example.web.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private SecurityService securityService;

   @Autowired
   private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
auth.userDetailsService(securityService)
        .passwordEncoder(passwordEncoder);

    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .formLogin()

                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied")
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
               // .antMatchers().hasAnyAuthority("employee", "ADMIN")
               // .antMatchers("/companies", "/employee").hasAnyAuthority("ADMIN")
                .anyRequest().hasAnyAuthority("user", "ADMIN");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
               .authorizeRequests()
               // .antMatchers(HttpMethod.GET, "/company/**")
               // .hasAnyAuthority("ADMIN")
               // .antMatchers(HttpMethod.GET, "/employees/**")
               // .hasAnyAuthority("ADMIN","USER")
                //.antMatchers(HttpMethod.GET, "/")
                .anyRequest()
                .permitAll()
                .and()
                .csrf()
                .disable()
                .logout()
                .and()
                .formLogin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
        web.ignoring().antMatchers("/static/**");
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/js/**");
    }



}
