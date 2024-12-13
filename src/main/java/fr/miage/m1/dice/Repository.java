package fr.miage.m1.dice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<DiceRollLog, Long> {
}
