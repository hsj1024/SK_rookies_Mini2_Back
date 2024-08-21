package com.rookies_talk.controller;

import com.rookies_talk.entity.ChatMessage;
import com.rookies_talk.entity.ChatRoom;
import com.rookies_talk.entity.User;
import com.rookies_talk.service.ChatService;
import com.rookies_talk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @PostMapping("/room")
    public ChatRoom createRoom(@RequestBody String name, @RequestBody Set<User> users) {
        return chatService.createChatRoom(name, users);
    }

    @PostMapping("/send")
    public void sendMessage(@RequestParam Long chatRoomId,
                            @RequestParam Long senderId,
                            @RequestBody String content) {
        User sender = userService.findUserById(senderId); // 실제로 데이터베이스에서 사용자 정보를 가져오는 로직
        chatService.sendMessage(chatRoomId, sender, content);
    }

    @GetMapping("/messages/{roomId}")
    public List<ChatMessage> getMessages(@PathVariable Long roomId) {
        return chatService.getMessages(roomId);
    }
}
