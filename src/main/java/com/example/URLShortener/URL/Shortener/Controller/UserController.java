package com.example.URLShortener.URL.Shortener.Controller;

import com.example.URLShortener.URL.Shortener.Entity.User;
import com.example.URLShortener.URL.Shortener.Model.UserDTO;
import com.example.URLShortener.URL.Shortener.Repository.UserRepository;
import com.example.URLShortener.URL.Shortener.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/register/create-user")
   public ResponseEntity<?> createUserEntry(@RequestBody User user){
       User savedUser = userService.SaveEntry(user);

       if(savedUser != null){
           return new ResponseEntity<>(HttpStatus.CREATED);
       }

       return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
   }

   @PreAuthorize("hasRole('ADMIN')")
   @PostMapping("/admin/create-admin")
   public ResponseEntity<?> createAdminEntry(@RequestBody UserDTO request){
        User createdAdmin = userService.saveAdminEntry(request.getUserName(), request.getPassword());

        if(createdAdmin != null){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
   }

   @PreAuthorize("hasRole('ADMIN')")
   @GetMapping("/admin/all-users")
   public ResponseEntity<List<User>> getAllUsersByAdmin(){
        List<User> all = userService.getAll();

        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }

   @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
   @PutMapping("/update/{userName}")
   public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
        User userInDb = userRepository.findByUserName(userName);

        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setEmail(user.getEmail());
            userInDb.setPassword(encoder.encode(user.getPassword()));
            userService.SaveEntry(userInDb);
            return ResponseEntity.ok("User updated successfully");
        }
       return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
   }


   @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/delete/{userName}")
   public ResponseEntity<?> deleteUser(@PathVariable String userName){
        List<User> users = (List<User>) userRepository.findByUserName(userName);

        users.removeIf(user -> {
            if(user.getUserName().equals(userName)){
                userRepository.delete(user);
                return true;
            }
            return false;
        });
        return ResponseEntity.ok("user deleted successfully");

   }

}
