package io.spring.workshop.tradingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.*;

@Controller
public class QuotesController {
    @GetMapping(value = "/quotes", produces = TEXT_HTML_VALUE)
    public String getQuotes() {
        return "quotes";
    }

    @GetMapping(value = "/quotes/feed", produces = TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Quote> getFeed() {
        return WebClient.create()
                .get()
                .uri("http://localhost:8081/quotes")
                .accept(APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(Quote.class);
    }
}
