[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/QGf4F8_h)

# Projet "Dice" - Gestion de lancés de dés avec Spring Boot

## Description

Le projet "Dice" est une application construite avec Spring Boot permettant de simuler des lancés de dés et de gérer un historique des résultats en base de données. Ce projet met en œuvre les concepts fondamentaux de Spring Boot, notamment l'injection de dépendances, les services RESTful, les entités JPA et les repositories.

---

## Prérequis

- **Java** : Version 17
- **Gradle** : Build Tool
- **IDE** : IntelliJ IDEA (recommandé)

---

## Installation

1. **Cloner le dépôt** :

    ```
    git clone <URL_DU_DEPOT>
    cd <NOM_DU_DEPOT>
    ```

2. **Ouvrir le projet** dans IntelliJ IDEA.

3. **Configurer Java 17**  
   Assurez-vous que le projet utilise Java 17 en ajoutant la configuration suivante dans `build.gradle.kts` :

    ```
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }
    ```

---

## Dépendances

Les principales dépendances utilisées dans ce projet sont :

- Spring Boot Starter Data JPA  
- Spring Boot Starter Web  
- SpringDoc OpenAPI Starter WebMVC UI  
- H2 Database  

Ces dépendances sont définies dans `build.gradle.kts` :

    ```
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
        runtimeOnly("com.h2database:h2")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }
    ```

---

## Étapes de réalisation

### 1. Création du projet Spring Boot

- Utilisez **Spring Initializr** pour créer le projet.  
- Ajoutez les dépendances nécessaires : `Spring Web`, `Spring Data JPA`, `H2 Database`.

---

### 2. Configuration de l'application

- Configurez l'application pour qu'elle utilise le port `8081`.  
- Ajoutez les configurations suivantes dans `application.properties` :  

    ```
    spring.application.name=dice
    server.port=8081
    ```

---

### 3. Classe `De`

Implémentez une classe représentant un dé avec les méthodes nécessaires pour effectuer un lancé.  

    ```
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
    ```

---

### 4. Entité `DiceRollLog`

Modélisez une entité JPA pour enregistrer les informations des lancés de dés :

    ```
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

        public DiceRollLog() {}

        public Long getId() { return id; }
        public int getDiceCount() { return diceCount; }
        public List<Integer> getResults() { return results; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
    ```

---

### 5. Repository `DiceRollLog`

Implémentez une interface héritant de `JpaRepository<DiceRollLog, Long>` :

    ```
    package fr.miage.m1.dice;

    import org.springframework.data.jpa.repository.JpaRepository;

    public interface Repository extends JpaRepository<DiceRollLog, Long> {}
    ```

---

### 6. Contrôleur REST

Créez un contrôleur REST pour gérer les lancés de dés :

    ```
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

        @GetMapping("/rollDices/{X}")
        public List<Integer> getRollDices(@PathVariable int X) {
            return services.rollDices(X);
        }
    }
    ```

---

### 7. Service

Créez un service pour la logique métier :

    ```
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
    ```

---

## Tests et validation

- Démarrez l'application.  
- Testez les endpoints suivants :  
  - **GET** `/rollDice`  
  - **GET** `/rollDices/{X}`  
  - **GET** `/diceLogs`
- Vérifiez les réponses JSON et les données dans la base H2.

---

## Bonus

Ajoutez **Swagger** pour documenter vos endpoints. Accédez à la documentation via :  
[http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html).
