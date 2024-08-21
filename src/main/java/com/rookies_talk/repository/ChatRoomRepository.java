package com.rookies_talk.repository;

import com.rookies_talk.entity.ChatMessage;
import com.rookies_talk.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {}


