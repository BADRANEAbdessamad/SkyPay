package com.skypay.hotel.model;

import java.util.Date;
import java.util.Objects;

public class Booking {
    private final User userSnapshot;
    private final Room roomSnapshot;
    private final Date checkIn;
    private final Date checkOut;
    private final Date createdAt;

    public Booking(User user, Room room, Date checkIn, Date checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }
        if (!checkIn.before(checkOut)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        this.userSnapshot = new User(user.getId(), user.getBalance()); // snapshot of user balance
        this.roomSnapshot = new Room(room.getRoomNumber(), room.getType(), room.getPricePerNight());
        this.checkIn = new Date(checkIn.getTime());
        this.checkOut = new Date(checkOut.getTime());
        this.createdAt = new Date();
    }

    public User getUserSnapshot() {
        return userSnapshot;
    }

    public Room getRoomSnapshot() {
        return roomSnapshot;
    }

    public Date getCheckIn() {
        return new Date(checkIn.getTime());
    }

    public Date getCheckOut() {
        return new Date(checkOut.getTime());
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // equals and hashCode based on user, room, checkIn, checkOut maybe? Optional

    @Override
    public String toString() {
        return "Booking{" +
                "userId=" + userSnapshot.getId() +
                ", userBalanceAtBooking=" + userSnapshot.getBalance() +
                ", roomNumber=" + roomSnapshot.getRoomNumber() +
                ", roomType=" + roomSnapshot.getType() +
                ", roomPricePerNight=" + roomSnapshot.getPricePerNight() +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", createdAt=" + createdAt +
                '}';
    }
}
