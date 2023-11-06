package edu.upao.pe.ilovetravelfinal.controllers;

import edu.upao.pe.ilovetravelfinal.models.User;
import edu.upao.pe.ilovetravelfinal.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    private List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userid}")
    public User getUserById(@PathVariable Long userid){
        return userService.getUserById(userid).orElse(new User());
    }

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody User user){
        try{
            User newUser = userService.addUser(user);
            user.setRegistrationDate(Instant.now());
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (IllegalStateException sms){
            return new ResponseEntity<>(sms.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{userid}")
    public void deleteUser(@PathVariable Long userid){
        userService.deleteUserById(userid);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        try {
            String email = loginRequest.get("email");
            String password = loginRequest.get("password");
            User loginuser = userService.verifyAccount(email, password);
            return new ResponseEntity<>(loginuser, HttpStatus.OK);
        }catch (IllegalStateException sms){
            return new ResponseEntity<>(sms.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}