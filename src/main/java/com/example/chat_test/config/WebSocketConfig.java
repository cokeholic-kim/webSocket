package com.example.chat_test.config;

import com.example.chat_test.chat.SocketTextHandler;
import com.example.chat_test.chat.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;
    private final SocketTextHandler socketTextHandler;
    private final HandshakeInterceptor ChattingHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // endpoint 설정 : /api/v1/chat/{postId}
        // 이를 통해서 ws://localhost:9090/ws/chat 으로 요청이 들어오면 websocket 통신을 진행한다.
        // setAllowedOrigins("*")는 모든 ip에서 접속 가능하도록 해줌
        registry.addHandler(socketTextHandler, "/chat/rooms/*")
                .addInterceptors(ChattingHandshakeInterceptor)
                //cors설정
                .setAllowedOrigins("*");
                //sockjs설정
//                .withSockJS();
    }
}
