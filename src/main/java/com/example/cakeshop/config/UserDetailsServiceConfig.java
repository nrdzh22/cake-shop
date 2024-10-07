//Links UserService to the UserDetailsService used by Spring Security.
//Retrieves user details for authentication, throwing an exception if the user is not found.
package com.example.cakeshop.config;

import com.example.cakeshop.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.ArrayList;

@Configuration
public class UserDetailsServiceConfig {

    private final UserService userService;

    public UserDetailsServiceConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            com.example.cakeshop.model.User user = userService.findByUsername(username);
            if (user != null) {
                return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        };
    }
}
