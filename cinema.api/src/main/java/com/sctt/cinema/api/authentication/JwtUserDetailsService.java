package com.sctt.cinema.api.authentication;

import com.sctt.cinema.api.business.repository.UserRepository;
import com.sctt.cinema.api.business.service.jpa.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.sctt.cinema.api.business.entity.jpa.User user = service.findById(username);
        if (user != null)
            return new User(username, user.password, new ArrayList<>());
        else
            throw new UsernameNotFoundException("User not found with email: " + username);
    }
}