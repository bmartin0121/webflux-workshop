package io.spring.workshop.tradingservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Configuration
public class EchoRouter {

    @Bean
    public HandlerMapping echoHandlerMapping() {
        var handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setUrlMap(Map.of("/websocket/echo","echoMessageHandler"));
        handlerMapping.setOrder(-2147483648);
        return handlerMapping;
    }
    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
