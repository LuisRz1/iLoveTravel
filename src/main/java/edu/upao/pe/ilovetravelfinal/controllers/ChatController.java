package edu.upao.pe.ilovetravelfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.upao.pe.ilovetravelfinal.models.ChatMessage;
import edu.upao.pe.ilovetravelfinal.models.User;
import edu.upao.pe.ilovetravelfinal.services.ChatMessageService;
import edu.upao.pe.ilovetravelfinal.services.UserService;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {
    private final ChatMessageService chatMessageService;
    private final UserService userService; // Agregar la inyección de UserService

    public ChatController(ChatMessageService chatMessageService, UserService userService) {
        this.chatMessageService = chatMessageService;
        this.userService = userService; // Inyectar el servicio
    }

    @PostMapping("/send-message")
    public ResponseEntity<ApiResponse> sendMessage(@RequestBody Map<String, String> messageData) {
        // Obtén el nombre del remitente del mensaje (first_name y last_name)
        String senderFirstName = messageData.get("senderFirstName");
        String senderLastName = messageData.get("senderLastName");

        // Busca al usuario que envía el mensaje por su nombre
        User sender = userService.getUserByFirstNameAndLastName(senderFirstName, senderLastName);

        if (sender == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Obtén el nombre del destinatario del mensaje (first_name y last_name)
        String receiverFirstName = messageData.get("receiverFirstName");
        String receiverLastName = messageData.get("receiverLastName");

        // Busca al usuario que recibe el mensaje por su nombre
        User receiver = userService.getUserByFirstNameAndLastName(receiverFirstName, receiverLastName);

        if (receiver == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Obtén el contenido del mensaje
        String messageText = messageData.get("message");

        // Crea un objeto ChatMessage y guárdalo en la base de datos
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(sender);
        chatMessage.setReceiver(receiver);
        chatMessage.setMessage(messageText);
        chatMessage.setSentAt(Instant.now());

        ChatMessage savedMessage = chatMessageService.saveChatMessage(chatMessage);

        return ResponseEntity.ok(new ApiResponse("Mensaje enviado exitosamente"));
    }

    @GetMapping("/messages/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable Long senderId, @PathVariable Long receiverId) {
        // Busca los objetos User correspondientes a los IDs
        User sender = userService.getUserById(senderId).orElse(null);
        User receiver = userService.getUserById(receiverId).orElse(null);

        if (sender == null || receiver == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Llama al método del servicio para obtener mensajes entre los usuarios
        List<ChatMessage> messages = chatMessageService.getChatMessages(sender, receiver);
        return ResponseEntity.ok(messages);
    }
}