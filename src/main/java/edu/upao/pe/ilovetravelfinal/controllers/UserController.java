package edu.upao.pe.ilovetravelfinal.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.upao.pe.ilovetravelfinal.models.ChatMessage;
import edu.upao.pe.ilovetravelfinal.models.User;
import edu.upao.pe.ilovetravelfinal.services.ChatMessageService;
import edu.upao.pe.ilovetravelfinal.services.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final ChatMessageService chatMessageService; // Agregar la inyección de ChatMessageService

    public UserController(UserService userService, ChatMessageService chatMessageService) {
        this.userService = userService;
        this.chatMessageService = chatMessageService; // Inyectar el servicio
    }

    @GetMapping
    private List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userid}")
    public ResponseEntity<User> getUserById(@PathVariable Long userid) {
        User user = userService.getUserById(userid).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Calcula la cantidad de mensajes enviados y recibidos por el usuario
        List<ChatMessage> sentMessages = chatMessageService.getSentMessagesForUser(user);
        List<ChatMessage> receivedMessages = chatMessageService.getReceivedMessagesForUser(user);

        user.setSentMessages(sentMessages);
        user.setReceivedMessages(receivedMessages);

        return ResponseEntity.ok(user);
    }


    @PostMapping
    public String addUser(@RequestBody User user){
        userService.addUser(user);
        return "Registrado correctamente";
    }

    @DeleteMapping("/{userid}")
    public void deleteUser(@PathVariable Long userid){
        userService.deleteUserById(userid);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        User user = userService.verifyAccount(email, password);

        if (user != null) {
            String comment = "Sesión Iniciada, Bienvenido -> " + user.getFirstName() + user.getLastName();
            ApiResponse res = new ApiResponse(comment, user);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}