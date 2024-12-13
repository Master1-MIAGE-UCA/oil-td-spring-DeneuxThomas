package fr.miage.m1.dice;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class DiceRollLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int diceCount;

    @ElementCollection
    private List<Integer> results;

    @CreationTimestamp
    private LocalDateTime timestamp;


    public DiceRollLog(int diceCount, List<Integer> results) {
        this.diceCount = diceCount;
        this.results = results;
    }

    public DiceRollLog() {

    }

    public Long getId() {
        return id;
    }

    public int getDiceCount() {
        return diceCount;
    }

    public List<Integer> getResults() {
        return results;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}