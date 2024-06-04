package com.example.chat_test.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
public class Room {
    private Long id;

    private String name;

    private final Set<WebSocketSession> sessions = new HashSet<>();

    public static Room create(String name) {
        Room room = new Room();
        room.id = RoomIdGenerator.createId();
        room.name = name;
        return room;
    }
}
