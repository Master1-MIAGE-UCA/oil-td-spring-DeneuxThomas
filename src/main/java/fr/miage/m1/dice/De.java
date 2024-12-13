package fr.miage.m1.dice;

import org.springframework.stereotype.Component;

@Component
public class De {
    private int valeur;

    public De() {
        this.valeur = (int) (Math.random() * 6) + 1;
    }

    public int getValeur() {
        return this.valeur;
    }

    public void lancer() {
        this.valeur = (int) (Math.random() * 6) + 1;
    }
}
