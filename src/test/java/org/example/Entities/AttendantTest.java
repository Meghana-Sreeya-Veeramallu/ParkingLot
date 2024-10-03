package org.example.Entities;

import org.example.Enums.CarColor;
import org.example.Exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttendantTest {
    // Test for assign() method
    @Test
    void testAssignParkingLot() {
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5);

        assertDoesNotThrow(() -> attendant.assign(parkingLot));
    }

    @Test
    void testAssignTwoParkingLots(){
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot secondParkingLot = new ParkingLot(5);

        assertDoesNotThrow(() -> attendant.assign(firstParkingLot));
        assertDoesNotThrow(() -> attendant.assign(secondParkingLot));
    }

    @Test
    void testAssignAParkingLotTwice(){
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5);

        assertDoesNotThrow(() -> attendant.assign(parkingLot));
        assertThrows(ParkingLotAlreadyAssigned.class, () -> attendant.assign(parkingLot));
    }

    // Test for park() method
    @Test
    void testParkIfNoParkingLotIsAssigned(){
        Attendant attendant = new Attendant();
        Car car = new Car("TS-1234", CarColor.RED);

        assertThrows(NoParkingLotAssigned.class, () -> attendant.park(car));
    }

    @Test
    void testParkIfParkingLotIsNotFull() {
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5);
        attendant.assign(parkingLot);
        Car car = new Car("TS-1234", CarColor.RED);

        assertDoesNotThrow(() -> attendant.park(car));
    }

    @Test
    void testParkShouldReturnTicket() {
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5);
        attendant.assign(parkingLot);
        Car car = new Car("TS-1234", CarColor.RED);

        Ticket ticket = attendant.park(car);

        assertNotNull(ticket);
    }

    @Test
    void testParkIfFirstParkingLotIsFull() {
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        attendant.assign(firstParkingLot);
        attendant.assign(secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);

        assertDoesNotThrow(() -> attendant.park(firstCar));
        assertDoesNotThrow(() -> attendant.park(secondCar));
    }

    @Test
    void testParkIfAllParkingLotsAreFull() {
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        attendant.assign(firstParkingLot);
        attendant.assign(secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        Car thirdCar = new Car("TS-1236", CarColor.RED);
        attendant.park(firstCar);
        attendant.park(secondCar);

        assertThrows(ParkingLotFullException.class, () -> attendant.park(thirdCar));
    }

    @Test
    void testParkIfCarIsAlreadyParked() {
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5);
        attendant.assign(parkingLot);
        Car car = new Car("TS-1234", CarColor.RED);
        attendant.park(car);

        assertThrows(CarAlreadyParkedException.class, () -> attendant.park(car));
    }

    @Test
    void testParkIfCarIsAlreadyParkedInAnotherParkingLot() {
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(5);
        attendant.assign(firstParkingLot);
        attendant.assign(secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        attendant.park(firstCar);
        attendant.park(secondCar);

        assertThrows(CarAlreadyParkedException.class, () -> attendant.park(firstCar));
    }

    // Tests for unpark() method
    @Test
    void testUnparkIfTicketIsValidForFirstCar() {
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5);
        attendant.assign(parkingLot);
        Car car = new Car("TS-1234", CarColor.RED);
        Ticket ticket = attendant.park(car);

        assertDoesNotThrow(() -> attendant.unpark(ticket));
    }

    @Test
    void testUnparkIfTicketIsValidForSecondCar() {
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5);
        attendant.assign(parkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        attendant.park(firstCar);
        Ticket ticket =  attendant.park(secondCar);

        Car unparkedCar = attendant.unpark(ticket);

        assertEquals(secondCar, unparkedCar);
    }

    @Test
    void testUnparkIfTicketIsValidForSecondParkingLot() {
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(5);
        attendant.assign(firstParkingLot);
        attendant.assign(secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        attendant.park(firstCar);
        Ticket ticket =  attendant.park(secondCar);

        Car unparkedCar = attendant.unpark(ticket);

        assertEquals(secondCar, unparkedCar);
    }

    @Test
    void testUnparkIfTicketIsInvalid() {
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(3);
        attendant.assign(parkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        attendant.park(firstCar);
        Ticket ticket =  attendant.park(secondCar);
        attendant.unpark(ticket);

        assertThrows(InvalidTicketException.class, () -> attendant.unpark(ticket));
    }

    @Test
    void testUnparkIfTicketIsInvalidForSecondParkingLot() {
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(5);
        attendant.assign(firstParkingLot);
        attendant.assign(secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        attendant.park(firstCar);
        Ticket ticket =  attendant.park(secondCar);
        attendant.unpark(ticket);

        assertThrows(InvalidTicketException.class, () -> attendant.unpark(ticket));
    }

    @Test
    void testUnparkIfACarUnparkedInFirstParkingLotAndACarIsTryingToPark(){
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        attendant.assign(firstParkingLot);
        attendant.assign(secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        Car thirdCar = new Car("TS-1236", CarColor.RED);
        Ticket ticket = attendant.park(firstCar);
        attendant.park(secondCar);
        attendant.unpark(ticket);

        assertDoesNotThrow(() -> attendant.park(thirdCar));
    }
}