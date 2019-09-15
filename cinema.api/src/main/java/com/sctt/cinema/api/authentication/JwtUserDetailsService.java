package com.sctt.cinema.api.authentication;

import com.sctt.cinema.api.business.repository.UserRepository;
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
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<com.sctt.cinema.api.business.entity.jpa.User> user = repo.findById(username);
        if (user.isPresent())
            return new User(username, user.get().password, new ArrayList<>());
        else
            throw new UsernameNotFoundException("User not found with username: " + username);
    }
}