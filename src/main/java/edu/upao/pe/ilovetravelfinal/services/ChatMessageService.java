package edu.upao.pe.ilovetravelfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.upao.pe.ilovetravelfinal.models.ChatMessage;
import edu.upao.pe.ilovetravelfinal.models.User;
import edu.upao.pe.ilovetravelfinal.repositories.ChatMessageRepository;

import java.util.List;

@Service
public class ChatMessageService {
    @Autowired
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatMessage saveChatMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getMessagesBySenderAndReceiver(User sender, User receiver) {
        return chatMessageRepository.findAllBySenderAndReceiverOrderByDateSentAsc(sender, receiver);
    }
    public List<ChatMessage> getReceivedMessagesForUser(User receiver) {
        return chatMessageRepository.findAllByReceiverOrderByDateSentAsc(receiver);
    }
    public int getReceivedMessagesCount(User user) {
        List<ChatMessage> receivedMessages = chatMessageRepository.findAllByReceiverOrderByDateSentAsc(user);
        return receivedMessages.size();
    }
}