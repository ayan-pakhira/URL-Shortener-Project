package com.example.URLShortener.URL.Shortener.Service;

import com.example.URLShortener.URL.Shortener.Entity.User;
import com.example.URLShortener.URL.Shortener.Model.UserPrincipal;
import com.example.URLShortener.URL.Shortener.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found with the username " + user);
        }

        return new UserPrincipal(user);
    }
}
