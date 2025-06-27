# Skypay Hotel Reservation System

## Résumé du projet

Ce projet implémente un système simplifié de réservation d’hôtel en Java.  
Le système gère trois entités principales : **User**, **Room** et **Booking**.

---

## Entités

- **User** : identifié par un `userId` unique et un solde (`balance`).
- **Room** : identifié par un `roomNumber`, un type (`RoomType`) parmi `STANDARD`, `JUNIOR_SUITE`, `SUITE`, et un prix par nuit.
- **Booking** : représente une réservation entre un utilisateur et une chambre sur une période donnée, avec un snapshot des informations au moment de la réservation.

---

## Fonctionnalités implémentées

- `setUser(int userId, int balance)` : crée un utilisateur s’il n’existe pas.
- `setRoom(int roomNumber, RoomType type, int price)` : crée une chambre si elle n’existe pas, sans impacter les réservations passées.
- `bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut)` : réserve une chambre si :
    - Les dates sont valides (`checkIn` < `checkOut`).
    - L’utilisateur et la chambre existent.
    - L’utilisateur dispose d’un solde suffisant.
    - La chambre est disponible sur la période demandée (pas de chevauchement avec d’autres réservations).
- `printAll()` : affiche toutes les chambres et réservations, du plus récent au plus ancien.
- `printAllUsers()` : affiche tous les utilisateurs, du plus récent au plus ancien.

---

## Cas de test

1. Création des chambres :

| ID  | Type         | Prix/nuit |
|------|--------------|-----------|
| 1    | STANDARD     | 1000      |
| 2    | JUNIOR_SUITE | 2000      |
| 3    | SUITE        | 3000      |

2. Création des utilisateurs :

| ID  | Solde  |
|------|--------|
| 1    | 5000   |
| 2    | 10000  |

3. Réservations testées :

| Utilisateur | Chambre | Arrivée      | Départ      | Nuitées | Résultat attendu                  |
|-------------|---------|--------------|-------------|---------|---------------------------------|
| 1           | 2       | 30/06/2026   | 07/07/2026  | 7       | Acceptée                        |
| 1           | 2       | 07/07/2026   | 30/06/2026  | Invalid | Rejetée (date départ avant arrivée) |
| 1           | 1       | 07/07/2026   | 08/07/2026  | 1       | Acceptée                        |
| 2           | 1       | 07/07/2026   | 09/07/2026  | 2       | Acceptée                        |
| 2           | 3       | 07/07/2026   | 08/07/2026  | 1       | Acceptée                        |

4. Modification chambre existante :

- `setRoom(1, SUITE, 10000)` modifie la chambre 1 sans impacter les réservations passées.

---

## Résultat attendu (extrait)

```
=== Rooms ===
Room 3 | Type: SUITE | Price: 3000
Room 2 | Type: JUNIOR_SUITE | Price: 2000
Room 1 | Type: SUITE | Price: 10000

=== Bookings ===
Booking{userId=2, userBalanceAtBooking=10000, roomNumber=3, roomType=SUITE, roomPricePerNight=3000, checkIn=Wed Jul 07 00:00:00 UTC 2026, checkOut=Thu Jul 08 00:00:00 UTC 2026, createdAt=...}
Booking{userId=2, userBalanceAtBooking=10000, roomNumber=1, roomType=STANDARD, roomPricePerNight=1000, checkIn=Tue Jul 07 00:00:00 UTC 2026, checkOut=Thu Jul 09 00:00:00 UTC 2026, createdAt=...}
Booking{userId=1, userBalanceAtBooking=5000, roomNumber=1, roomType=STANDARD, roomPricePerNight=1000, checkIn=Tue Jul 07 00:00:00 UTC 2026, checkOut=Wed Jul 08 00:00:00 UTC 2026, createdAt=...}
Booking{userId=1, userBalanceAtBooking=5000, roomNumber=2, roomType=JUNIOR_SUITE, roomPricePerNight=2000, checkIn=Tue Jun 30 00:00:00 UTC 2026, checkOut=Tue Jul 07 00:00:00 UTC 2026, createdAt=...}

=== Users ===
User 2 | Balance: 2500
User 1 | Balance: 0
```

---

## Instructions pour compiler et exécuter 
### En ligne de commande (sans Maven): 
```bash 
javac -d out src/**/*.java
java -cp out Main

```
### Avec Maven
```bash 
mvn clean compile
mvn exec:java -Dexec.mainClass=Main

```
---
## Questions bonus

### 1. Mettre toutes les fonctions dans un seul service est-il recommandé ?

Non, pour un projet plus complexe, il est préférable de séparer les responsabilités (UserService, RoomService, BookingService) pour faciliter la maintenance, les tests unitaires et respecter le principe de responsabilité unique.

### 2. Alternatives à `setRoom` sans impacter les réservations passées ?

On peut rendre les entités immuables et gérer les versions des chambres (par exemple, versionner les prix ou types dans le temps), ou créer de nouvelles entités pour chaque modification. Cela garantit que les anciennes réservations restent cohérentes.

---

## Instructions pour exécuter

- Compiler avec Java 17 ou supérieur.
- Exécuter les tests unitaires JUnit fournis.
- Observer les sorties `printAll()` et `printAllUsers()` pour valider les résultats.

---

Merci ! N’hésitez pas à me demander si vous souhaitez un exemple complet de tests ou du code source.

