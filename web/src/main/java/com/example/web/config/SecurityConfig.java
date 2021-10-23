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

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/sign-in")
                .failureUrl("/sign-in?error")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/sign-in")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied");
               /* .and()
                .authorizeRequests()
                .antMatchers("/sign-in").permitAll()
                .antMatchers("/**", "/message", "/messages").hasAnyAuthority("employee", "ADMIN")
                .antMatchers("/companies", "/employee").hasAnyAuthority("ADMIN")
                .anyRequest().hasAnyAuthority("employee", "ADMIN");*/
       /* http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/**").hasAnyAuthority("ADMIN")
                //.antMatchers(HttpMethod.DELETE, "/**").hasAnyAuthority("ADMIN")
                .anyRequest()
                .permitAll()
                .and()
                .logout()
                .and()
                .formLogin()
                .loginPage("/sign-in")
                .defaultSuccessUrl("/");*/

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
        web.ignoring().antMatchers("/static/**");
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/js/**");
    }


}
