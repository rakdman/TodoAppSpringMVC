package com.todoproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.Cookie;


@Configuration
@EnableWebSecurity
public class CustomSecurity extends WebSecurityConfigurerAdapter {


    @Autowired
    private CustomUserDetailsService userDetailsService;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        Cookie cookie = new Cookie("JSESSIONID",null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/login/");

        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(cookie);
        http.csrf().disable().authorizeRequests()
//                .antMatchers("/**").permitAll()
                .antMatchers("/registration","/contact","/saveuser").permitAll()
                .antMatchers("/","/getalltodo","/savetodo","/updateTodo", "/deleteTodo").hasAnyRole("USER","ADMIN")
                .antMatchers("/deleteUser").hasAnyRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/accessdenied")
                .and()
                .logout()
                .addLogoutHandler(cookieClearingLogoutHandler);
//                .logoutSuccessUrl("/")
//                .deleteCookies()
//                .invalidateHttpSession(true);
    }



    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);

        return daoAuthenticationProvider;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}