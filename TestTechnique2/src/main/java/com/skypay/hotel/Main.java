package com.skypay.hotel;

import com.skypay.hotel.model.RoomType;
import com.skypay.hotel.service.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        Service service = new Service();

        // Format de date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // === Étape 1 : Créer les 3 chambres ===
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setRoom(3, RoomType.SUITE, 3000);

        // === Étape 2 : Créer les 2 utilisateurs ===
        service.setUser(1, 5000);
        service.setUser(2, 10000);

        // === Étape 3 : Réservations ===
        // User 1 essaie de réserver Room 2 du 30/06/2026 au 07/07/2026 (7 nuits)
        service.bookRoom(1, 2, sdf.parse("30/06/2026"), sdf.parse("07/07/2026"));

        // User 1 essaie de réserver Room 2 du 07/07/2026 au 30/06/2026 (erreur de date)
        service.bookRoom(1, 2, sdf.parse("07/07/2026"), sdf.parse("30/06/2026"));

        // User 1 réserve Room 1 du 07/07/2026 au 08/07/2026 (1 nuit)
        service.bookRoom(1, 1, sdf.parse("07/07/2026"), sdf.parse("08/07/2026"));

        // User 2 réserve Room 1 du 07/07/2026 au 09/07/2026 (2 nuits)
        service.bookRoom(2, 1, sdf.parse("07/07/2026"), sdf.parse("09/07/2026"));

        // User 2 réserve Room 3 du 07/07/2026 au 08/07/2026 (1 nuit)
        service.bookRoom(2, 3, sdf.parse("07/07/2026"), sdf.parse("08/07/2026"));

        // === Étape 4 : setRoom(1, SUITE, 10000) → ne doit pas impacter les anciennes réservations ===
        service.setRoom(1, RoomType.SUITE, 10000);

        // === Étape 5 : Impressions finales ===
        System.out.println("\n========= PRINT ALL =========");
        service.printAll();

        System.out.println("\n========= PRINT ALL USERS =========");
        service.printAllUsers();
    }
}
