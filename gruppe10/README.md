# Restaurant Ordering API (Gruppe 10)

## Projektbeschreibung
Dieses Projekt ist eine **REST-API für ein Restaurant-Bestellsystem**
Die API ermöglicht Kunden das Abrufen und Bestellen von Speisen, der Küche die Verwaltung von Bestellungen sowie der Verwaltung die Pflege der Speisekarte, Kategorien, Zahlungsmittel und Bewertungen.

## Inhalt
Die API bildet drei Hauptbereiche ab:  
Kunden können die Speisekarte abrufen, filtern, Bestellungen aufgeben, bezahlen und den Status ihrer Bestellung einsehen sowie nach Abschluss Bewertungen abgeben.  
Die Küche erhält eine chronologische Übersicht bezahlter Bestellungen und kann deren Status aktualisieren.  
Die Verwaltung kann Speisen, Kategorien, Filter-Tags, Zahlungsmittel und die Speisekarte verwalten sowie Bewertungen einsehen.

## Notwendige Abhängigkeiten
- Java 25
- Gradle
- Spring Boot
- Spring Data JPA
- MySQL
- MySQL JDBC Driver

## Installationsanleitung

1. Repository klonen:
   ```bash
   git clone https://git.th-wildau.de/wir/fose/ga-ws2526/gruppe10.git

2. MySQL-Datenbank erstellen:
    ```sql
    CREATE DATABASE gastroapi;

3. env.properties im src/main/ressources erstellen und Datenbankzugang in konfigurieren:
    ```properties
    MYSQL_USERNAME=dein_username
    MYSQL_PASSWORD=dein_passwort

4. Projekt starten:
    ```bash
    ./gradlew bootRun

5. Die API ist anschließend unter folgender Adresse erreichbar:
    ```http://localhost:8080```
