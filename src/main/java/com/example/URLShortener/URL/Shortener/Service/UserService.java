package com.example.URLShortener.URL.Shortener.Service;

import com.example.URLShortener.URL.Shortener.Entity.User;
import com.example.URLShortener.URL.Shortener.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
   private AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    //to register a user
    public User SaveEntry(User user){
        User user1 = userRepository.findByUserName(user.getUserName());

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        return userRepository.save(user);
    }


    //to register an admin
    public User saveAdminEntry(String userName, String password){
        User admin = new User();

        admin.setUserName(userName);
        admin.setPassword(encoder.encode(password));
        admin.setRoles(Arrays.asList("ADMIN", "USER"));
        return userRepository.save(admin);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

}
