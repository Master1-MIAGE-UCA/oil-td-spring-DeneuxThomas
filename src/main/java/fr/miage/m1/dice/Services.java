package fr.miage.m1.dice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Services {

    @Autowired
    private De de;

    @Autowired
    private Repository repository;

    public List<Integer> rollDices(int X) {
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < X; i++) {
            de.lancer();
            results.add(de.getValeur());
        }

        DiceRollLog log = new DiceRollLog(X, results);
        repository.save(log);

        return results;
    }

    public List<DiceRollLog> getDiceLogs() {
        return repository.findAll();
    }
}