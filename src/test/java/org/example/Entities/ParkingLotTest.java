package org.example.Entities;

import org.example.Enums.CarColor;
import org.example.Exceptions.CarAlreadyParkedException;
import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.ParkingLotFullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {

    // Tests for ParkingLot class
    @Test
    public void testExceptionForParkingLotWithCapacity0() {
        assertThrows(Exception.class, () -> {new ParkingLot(0);});
    }

    @Test
    public void testParkingLotWith5Slots() throws Exception {
        ParkingLot parkingLot = new ParkingLot(5);

        assertNotNull(parkingLot);
    }

    // Tests for park() method
    @Test
    public void testParkCarWhenParkingLotIsEmpty() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Ticket ticket = parkingLot.park(firstCar);

        assertNotNull(ticket);
    }

    @Test
    public void testParkCarWhenParkingLotIsNotEmpty() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        parkingLot.park(firstCar);

        parkingLot.park(secondCar);
    }

    @Test
    public void testParkingWhenParkingLotIsFull() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);

        parkingLot.park(firstCar);

        assertThrows(ParkingLotFullException.class, () -> parkingLot.park(secondCar));
    }

    @Test
    public void testParkCarWhenCarIsAlreadyParked() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1234", CarColor.RED);
        parkingLot.park(firstCar);

        assertThrows(CarAlreadyParkedException.class, () -> parkingLot.park(secondCar));
    }

    // Tests for unpark() method
    @Test
    public void testUnparkCarWithValidTicket() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Ticket ticket = parkingLot.park(firstCar);

        assertDoesNotThrow(() -> parkingLot.unpark(ticket));
    }

    @Test
    public void testUnparkCarShouldReturnTheParkedCar() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Ticket ticket = parkingLot.park(firstCar);

        Car actualCar = parkingLot.unpark(ticket);

        assertEquals(firstCar, actualCar);
    }

    @Test
    public void testUnparkCarWithInvalidTicket() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        parkingLot.park(firstCar);

        Ticket invalidTicket = new Ticket();

        assertThrows(InvalidTicketException.class, () -> parkingLot.unpark(invalidTicket));
    }

}