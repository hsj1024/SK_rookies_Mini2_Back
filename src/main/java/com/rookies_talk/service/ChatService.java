package com.rookies_talk.service;

import com.rookies_talk.entity.ChatMessage;
import com.rookies_talk.entity.ChatRoom;
import com.rookies_talk.entity.User;
import com.rookies_talk.repository.ChatMessageRepository;
import com.rookies_talk.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class ChatService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

    public ChatRoom createChatRoom(String name, Set<User> users) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        chatRoom.setUsers(users);
        return chatRoomRepository.save(chatRoom);
    }

    public void sendMessage(Long chatRoomId, User sender, String content) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));

        ChatMessage message = new ChatMessage();
        message.setChatRoom(chatRoom);
        message.setSender(sender);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        kafkaTemplate.send("chat-topic", message);
        chatMessageRepository.save(message);
    }

    public List<ChatMessage> getMessages(Long chatRoomId) {
        return chatMessageRepository.findByChatRoomId(chatRoomId);
    }
}
