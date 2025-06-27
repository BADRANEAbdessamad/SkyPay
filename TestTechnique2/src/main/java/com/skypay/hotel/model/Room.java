package com.skypay.hotel.model;

import java.util.Date;
import java.util.Objects;

public class Room {
    private final int roomNumber;
    private RoomType type;
    private int pricePerNight;
    private final Date createdAt;

    public Room(int roomNumber, RoomType type, int pricePerNight) {
        if (pricePerNight <= 0) throw new IllegalArgumentException("Price per night must be positive");
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.createdAt = new Date();
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(int pricePerNight) {
        if (pricePerNight <= 0) throw new IllegalArgumentException("Price per night must be positive");
        this.pricePerNight = pricePerNight;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return roomNumber == room.roomNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }
}
