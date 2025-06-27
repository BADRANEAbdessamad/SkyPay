package com.skypay.hotel;

import com.skypay.hotel.model.*;

import java.util.*;

public class Service {
    private final List<User> users = new ArrayList<>();
    private final List<Room> rooms = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();

    public void setUser(int userId, int balance) {
        Optional<User> existing = users.stream()
                .filter(u -> u.getId() == userId)
                .findFirst();
        if (existing.isEmpty()) {
            users.add(new User(userId, balance));
        }
    }

    public void setRoom(int roomNumber, RoomType type, int price) {
        Optional<Room> existing = rooms.stream()
                .filter(r -> r.getRoomNumber() == roomNumber)
                .findFirst();
        if (existing.isEmpty()) {
            rooms.add(new Room(roomNumber, type, price));
        }
    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        // ⚠️ Vérifications de base
        if (checkIn == null || checkOut == null || !checkIn.before(checkOut)) {
            System.out.println("Invalid date range");
            return;
        }

        User user = users.stream()
                .filter(u -> u.getId() == userId)
                .findFirst()
                .orElse(null);
        Room room = rooms.stream()
                .filter(r -> r.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);

        if (user == null || room == null) {
            System.out.println("User or room not found");
            return;
        }

        long nights = daysBetween(checkIn, checkOut);
        int totalCost = (int) nights * room.getPricePerNight();

        // 🔒 Vérification de solde
        if (user.getBalance() < totalCost) {
            System.out.println("Insufficient balance");
            return;
        }

        // ⛔ Vérification de disponibilité
        boolean roomAvailable = bookings.stream().noneMatch(b ->
                b.getRoomSnapshot().getRoomNumber() == roomNumber &&
                        datesOverlap(b.getCheckIn(), b.getCheckOut(), checkIn, checkOut));

        if (!roomAvailable) {
            System.out.println("Room not available for this period");
            return;
        }

        // ✅ Réservation valide
        user.debit(totalCost);
        bookings.add(new Booking(user, room, checkIn, checkOut));
        System.out.println("Booking successful");
    }

    public void printAll() {
        System.out.println("=== Rooms ===");
        rooms.stream()
                .sorted(Comparator.comparing(Room::getCreatedAt).reversed())
                .forEach(r -> System.out.printf("Room %d | Type: %s | Price: %d\n",
                        r.getRoomNumber(), r.getType(), r.getPricePerNight()));

        System.out.println("=== Bookings ===");
        bookings.stream()
                .sorted(Comparator.comparing(Booking::getCreatedAt).reversed())
                .forEach(b -> System.out.println(b));
    }

    public void printAllUsers() {
        System.out.println("=== Users ===");
        users.stream()
                .sorted(Comparator.comparing(User::getCreatedAt).reversed())
                .forEach(u -> System.out.printf("User %d | Balance: %d\n", u.getId(), u.getBalance()));
    }

    // Helper method to compare only date (yyyy-MM-dd)
    private boolean datesOverlap(Date d1Start, Date d1End, Date d2Start, Date d2End) {
        return !d1End.before(d2Start) && !d1Start.after(d2End);
    }

    private long daysBetween(Date start, Date end) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);

        // Clear hour, min, sec, ms
        for (Calendar cal : new Calendar[]{calStart, calEnd}) {
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }

        long diffMillis = calEnd.getTimeInMillis() - calStart.getTimeInMillis();
        return diffMillis / (1000 * 60 * 60 * 24);
    }
}
