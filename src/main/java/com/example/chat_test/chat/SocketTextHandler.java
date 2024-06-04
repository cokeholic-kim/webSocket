package com.example.chat_test.chat;

import com.example.chat_test.model.Room;
import com.example.chat_test.model.RoomRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class SocketTextHandler extends TextWebSocketHandler {
    private final RoomRepository roomRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long roomId = getRoomId(session);
        roomRepository.room(roomId).getSessions().add(session);
        log.info("새 클라이언트와 연결 되었습니다.");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Long roomId = getRoomId(session);
        Room currentRoom = roomRepository.room(roomId);

        log.info(message.getPayload());


        for (WebSocketSession connectedSession : currentRoom.getSessions()) {
            connectedSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long roomId = getRoomId(session);
        roomRepository.room(roomId).getSessions().remove(session);
        log.info("클라이언트와 연결이 해제 되었습니다.");
    }

    private Long getRoomId(WebSocketSession session) {
        return Long.parseLong(
                session.getAttributes()
                        .get("roomId")
                        .toString()
        );
    }
}
