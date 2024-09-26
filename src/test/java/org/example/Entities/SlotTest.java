package org.example.Entities;

import org.example.Enums.CarColor;
import org.example.Exceptions.InvalidTicketException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlotTest {

    // Tests for isEmpty() method
    @Test
    void testIsEmptyIfSlotIsEmpty() {
        Slot slot = new Slot();
        assertTrue(slot.isEmpty());
    }

    @Test
    void testIsEmptyIfSlotIsNotEmpty() {
        Slot slot = new Slot();
        Car car = new Car("TS-1234", CarColor.RED);
        slot.park(car);
        assertFalse(slot.isEmpty());
    }

    // Tests for park() method
    @Test
    void testParkSuccessfully() {
        Slot slot = new Slot();
        Car car = new Car("TS-1234", CarColor.RED);
        Ticket ticket = slot.park(car);
        assertNotNull(ticket);
    }

    // Tests for unpark() method
    @Test
    void testUnparkIfTicketIsValid() {
        Slot slot = new Slot();
        Car car = new Car("TS-1234", CarColor.RED);
        Ticket ticket = slot.park(car);

        Car actualCar = slot.unpark(ticket);

        assertEquals(car, actualCar);
    }

    @Test
    void testUnparkIfTicketIsInvalid() {
        Slot slot = new Slot();
        Car car = new Car("TS-1234", CarColor.RED);
        slot.park(car);
        Ticket invalidTicket = new Ticket();

        assertThrows(InvalidTicketException.class, () -> slot.unpark(invalidTicket));
    }

    // Tests for isCarParked() method
    @Test
    void testIsCarParkedIfCarIsParked() {
        Slot slot = new Slot();
        Car car = new Car("TS-1234", CarColor.RED);
        slot.park(car);

        assertTrue(slot.isCarParked(car));
    }

    @Test
    void testIsCarParkedIfCarIsNotParked() {
        Slot slot = new Slot();
        Car car = new Car("TS-1234", CarColor.RED);

        assertFalse(slot.isCarParked(car));
    }

    // Tests for isCarOfColor() method
    @Test
    void testIsCarOfColorIfCarIsOfSameColor() {
        Slot slot = new Slot();
        Car car = new Car("TS-1234", CarColor.RED);
        slot.park(car);

        assertTrue(slot.isCarOfColor(CarColor.RED));
    }

    @Test
    void testIsCarOfColorIfCarIsOfDifferentColor() {
        Slot slot = new Slot();
        Car car = new Car("TS-1234", CarColor.RED);
        slot.park(car);

        assertFalse(slot.isCarOfColor(CarColor.BLUE));
    }

    // Tests for hasSameRegistrationNumber() method
    @Test
    void testHasSameRegistrationNumberIfCarRegistrationNumbersAreSame() {
        Slot slot = new Slot();
        Car car = new Car("TS-1234", CarColor.RED);
        slot.park(car);

        assertTrue(slot.hasSameRegistrationNumber("TS-1234"));
    }

    @Test
    void testHasSameRegistrationNumberIfCarRegistrationNumbersAreDifferent() {
        Slot slot = new Slot();
        Car car = new Car("TS-1234", CarColor.RED);
        slot.park(car);

        assertFalse(slot.hasSameRegistrationNumber("TS-1235"));
    }
}