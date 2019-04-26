package io.spring.workshop.tradingservice;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.socket.CloseStatus;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.StandardWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ReplayProcessor;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EchoWebSocketHandlerTests {

    @LocalServerPort
    private String port;

    @Test
    public void echo() throws Exception {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        ReplayProcessor<WebSocketMessage> replayProcessor = ReplayProcessor.create();

        webSocketClient.execute(getUrl("/websocket/echo"),
                session -> session.send(Flux.just("1", "2", "4").map(session::textMessage))
                        .thenMany(session.receive().take(3))
                        .subscribeWith(replayProcessor)
                        .then())
                .block();

        StepVerifier.create(replayProcessor)
                .assertNext(message -> assertPayloadEquals(message, "1"))
                .assertNext(message -> assertPayloadEquals(message, "2"))
                .assertNext(message -> assertPayloadEquals(message, ":("))
                .thenCancel()
                .verify();
    }

    private void assertPayloadEquals(WebSocketMessage message, String expectedPayload) {
        Assertions.assertThat(message.getPayloadAsText()).isEqualTo(expectedPayload);
    }

    protected URI getUrl(String path) throws URISyntaxException {
        return new URI("ws://localhost:" + this.port + path);
    }
}