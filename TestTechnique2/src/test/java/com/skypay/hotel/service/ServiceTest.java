package com.skypay.hotel.service;

import com.skypay.hotel.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private Service service;

    private Date date(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);  // mois commence à 0
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @BeforeEach
    void setUp() {
        service = new Service();
        service.setUser(1, 1000);
        service.setRoom(101, RoomType.STANDARD, 200);
    }

    @Test
    void testBookingWithValidData() {
        Date checkIn = date(2025, 7, 1);
        Date checkOut = date(2025, 7, 4); // 3 nuits × 200 = 600 < 1000

        service.bookRoom(1, 101, checkIn, checkOut);

        assertEquals(1, service.getBookings().size());
        Booking booking = service.getBookings().get(0);
        assertEquals(101, booking.getRoomSnapshot().getRoomNumber());
        assertEquals(1, booking.getUserSnapshot().getId());
    }

    @Test
    void testBookingFailsWithNullDates() {
        service.bookRoom(1, 101, null, date(2025, 7, 4));
        service.bookRoom(1, 101, date(2025, 7, 1), null);
        assertEquals(0, service.getBookings().size());
    }

    @Test
    void testBookingFailsWhenCheckInAfterCheckOut() {
        Date checkIn = date(2025, 7, 5);
        Date checkOut = date(2025, 7, 1);
        service.bookRoom(1, 101, checkIn, checkOut);
        assertEquals(0, service.getBookings().size());
    }

    @Test
    void testBookingFailsWhenUserDoesNotExist() {
        Date checkIn = date(2025, 7, 1);
        Date checkOut = date(2025, 7, 4);
        service.bookRoom(999, 101, checkIn, checkOut); // utilisateur inconnu
        assertEquals(0, service.getBookings().size());
    }

    @Test
    void testBookingFailsWhenRoomDoesNotExist() {
        Date checkIn = date(2025, 7, 1);
        Date checkOut = date(2025, 7, 4);
        service.bookRoom(1, 999, checkIn, checkOut); // chambre inconnue
        assertEquals(0, service.getBookings().size());
    }

    @Test
    void testBookingFailsWithInsufficientBalance() {
        service.setUser(2, 100); // solde trop faible
        Date checkIn = date(2025, 7, 1);
        Date checkOut = date(2025, 7, 4); // coût = 600
        service.bookRoom(2, 101, checkIn, checkOut);
        assertEquals(0, service.getBookings().size());
    }

    @Test
    void testBookingFailsWhenRoomIsAlreadyBooked() {
        Date checkIn = date(2025, 7, 1);
        Date checkOut = date(2025, 7, 4);
        service.bookRoom(1, 101, checkIn, checkOut); // première réservation

        // Deuxième réservation avec chevauchement
        service.setUser(2, 1000);
        service.bookRoom(2, 101, date(2025, 7, 2), date(2025, 7, 5));

        assertEquals(1, service.getBookings().size()); // seconde doit échouer
    }

    @Test
    void testBookingWorksWhenNoDateOverlap() {
        Date checkIn1 = date(2025, 7, 1);
        Date checkOut1 = date(2025, 7, 4);
        service.bookRoom(1, 101, checkIn1, checkOut1);

        // Deuxième utilisateur, dates après la première réservation
        service.setUser(2, 1000);
        service.bookRoom(2, 101, date(2025, 7, 5), date(2025, 7, 7));

        assertEquals(2, service.getUsers().size());
        assertEquals(2, service.getBookings().size());
    }
}
