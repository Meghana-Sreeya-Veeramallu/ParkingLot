package org.example.Entities;

import org.example.Enums.CarColor;
import org.example.Exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttendantTest {
    // Test for assign() method
    @Test
    void testAssignParkingLot() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5, owner);

        assertDoesNotThrow(() -> attendant.assign(parkingLot));
    }

    @Test
    void testAssignTwoParkingLots(){
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(5, owner);
        ParkingLot secondParkingLot = new ParkingLot(5, owner);

        assertDoesNotThrow(() -> attendant.assign(firstParkingLot));
        assertDoesNotThrow(() -> attendant.assign(secondParkingLot));
    }

    @Test
    void testAssignAParkingLotTwice(){
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5, owner);

        assertDoesNotThrow(() -> attendant.assign(parkingLot));
        assertThrows(ParkingLotAlreadyAssigned.class, () -> attendant.assign(parkingLot));
    }

    // Test for park() method
    @Test
    void testParkIfNoParkingLotIsAssigned(){
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        Car car = new Car("TS-1234", CarColor.RED);

        assertThrows(NoParkingLotAssigned.class, () -> attendant.park(car));
    }

    @Test
    void testParkIfParkingLotIsNotFull() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5, owner);
        attendant.assign(parkingLot);
        Car car = new Car("TS-1234", CarColor.RED);

        assertDoesNotThrow(() -> attendant.park(car));
    }

    @Test
    void testParkShouldReturnTicket() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5, owner);
        attendant.assign(parkingLot);
        Car car = new Car("TS-1234", CarColor.RED);

        Ticket ticket = attendant.park(car);

        assertNotNull(ticket);
    }

    @Test
    void testParkIfFirstParkingLotIsFull() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(1, owner);
        ParkingLot secondParkingLot = new ParkingLot(1, owner);
        attendant.assign(firstParkingLot);
        attendant.assign(secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);

        assertDoesNotThrow(() -> attendant.park(firstCar));
        assertDoesNotThrow(() -> attendant.park(secondCar));
    }

    @Test
    void testParkIfAllParkingLotsAreFull() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(1, owner);
        ParkingLot secondParkingLot = new ParkingLot(1, owner);
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
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5, owner);
        attendant.assign(parkingLot);
        Car car = new Car("TS-1234", CarColor.RED);
        attendant.park(car);

        assertThrows(CarAlreadyParkedException.class, () -> attendant.park(car));
    }

    @Test
    void testParkIfCarIsAlreadyParkedInAnotherParkingLot() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(1, owner);
        ParkingLot secondParkingLot = new ParkingLot(5, owner);
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
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5, owner);
        attendant.assign(parkingLot);
        Car car = new Car("TS-1234", CarColor.RED);
        Ticket ticket = attendant.park(car);

        assertDoesNotThrow(() -> attendant.unpark(ticket));
    }

    @Test
    void testUnparkIfTicketIsValidForSecondCar() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5, owner);
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
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(1, owner);
        ParkingLot secondParkingLot = new ParkingLot(5, owner);
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
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(3, owner);
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
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(1, owner);
        ParkingLot secondParkingLot = new ParkingLot(5, owner);
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
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(1, owner);
        ParkingLot secondParkingLot = new ParkingLot(1, owner);
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