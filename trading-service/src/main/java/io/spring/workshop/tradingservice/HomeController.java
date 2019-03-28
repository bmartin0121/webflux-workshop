package io.spring.workshop.tradingservice;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

@Controller
public class HomeController {
    private final TradingUserRepository repo;

    public HomeController(TradingUserRepository repo) {
        this.repo = repo;
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String getAllUsers(Model model) {
        Flux<TradingUser> allUsers = repo.findAll();
        model.addAttribute("users",allUsers);
        return "index";
    }


}
