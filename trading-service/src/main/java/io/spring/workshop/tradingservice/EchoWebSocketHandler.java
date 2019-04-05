package io.spring.workshop.tradingservice;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Component(value = "echoMessageHandler")
public class EchoWebSocketHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(
                webSocketSession.receive()
                        .delayElements(Duration.ofSeconds(1))
                        .log());
    }
}
