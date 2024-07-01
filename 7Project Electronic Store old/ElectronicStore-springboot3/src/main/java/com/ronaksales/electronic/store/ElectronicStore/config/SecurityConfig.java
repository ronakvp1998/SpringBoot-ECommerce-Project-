package com.ronaksales.electronic.store.ElectronicStore.config;

import com.fasterxml.classmate.AnnotationOverrides;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests((authorize) -> {authorize
                .anyRequest()
                .authenticated();
        }).httpBasic(Customizer.withDefaults());

        return http.build();
    }


    // This is for form based authentication
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
//                .formLogin(form -> form.loginPage("login.html")
//                .loginProcessingUrl("/process-url")
//                .defaultSuccessUrl("/dashboard")
//                .failureUrl("/error"))
//                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                        .permitAll()) ;
//        return http.build();
//    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this.userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        // User class is from springframework package
//        UserDetails normalUser = User.builder().username("admin")
//                .password(passwordEncoder().encode( "admin123"))
//                .roles("NORMAL").build();
//
//        UserDetails adminUser = User.builder()
//                .username("ronak")
//                .password(passwordEncoder().encode( "ronak123"))
//                .roles("ADMIN").build();
//
//        // create users in memory
//        // InMemoryUserDetailsManager- is implementation class of UserDetailService
//        // it can take multiple userDetails object inside the parameter
//        return new InMemoryUserDetailsManager(normalUser,adminUser);
//    }



}
