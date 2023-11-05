package edu.upao.pe.ilovetravelfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.upao.pe.ilovetravelfinal.models.ChatMessage;
import edu.upao.pe.ilovetravelfinal.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllBySenderOrderBySentAtAsc(User sender);
    List<ChatMessage> findAllByReceiverOrderBySentAtAsc(User receiver);

    long countByReceiver(User receiver);

    List<ChatMessage> findAllBySenderAndReceiverOrderBySentAtAsc(User sender, User receiver);
}