package fr.miage.m1.dice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private Services services;

    @GetMapping("/rollDice")
    public int rollDice() {
        return services.rollDices(1).get(0);
    }

    @GetMapping("/rollDice/{X}")
    public List<Integer> getRollDices(@PathVariable int X) {
        return services.rollDices(X);
    }

    @GetMapping("/diceLogs")
    public List<DiceRollLog> getDiceLogs() {
        return services.getDiceLogs();
    }
}
