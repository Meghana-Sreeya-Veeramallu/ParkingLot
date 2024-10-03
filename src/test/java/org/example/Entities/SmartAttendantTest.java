package org.example.Entities;

import org.example.Enums.CarColor;
import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.ParkingLotAlreadyAssigned;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartAttendantTest {
    // Test for assign() method
    @Test
    void testAssignParkingLot() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot parkingLot = new ParkingLot(5, owner);

        assertDoesNotThrow(() -> owner.assign(smartAttendant, parkingLot));
    }

    @Test
    void testAssignTwoParkingLots(){
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot firstParkingLot = new ParkingLot(5, owner);
        ParkingLot secondParkingLot = new ParkingLot(5, owner);

        assertDoesNotThrow(() -> owner.assign(smartAttendant, firstParkingLot));
        assertDoesNotThrow(() -> owner.assign(smartAttendant, secondParkingLot));
    }

    @Test
    void testAssignAParkingLotTwice(){
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot parkingLot = new ParkingLot(5, owner);

        assertDoesNotThrow(() -> owner.assign(smartAttendant, parkingLot));
        assertThrows(ParkingLotAlreadyAssigned.class, () -> owner.assign(smartAttendant, parkingLot));
    }

    // Tests for park() method
    @Test
    void testParkWhenSecondParkingLotHasMoreAvailableSlots() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot firstParkingLot = new ParkingLot(1, owner);
        ParkingLot secondParkingLot = new ParkingLot(2, owner);
        owner.assign(smartAttendant, firstParkingLot);
        owner.assign(smartAttendant, secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        smartAttendant.park(firstCar);

        assertFalse(firstParkingLot.isFull());
    }

    @Test
    void testParkWhenBothHaveEqualCapacity() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot firstParkingLot = new ParkingLot(1, owner);
        ParkingLot secondParkingLot = new ParkingLot(1, owner);
        owner.assign(smartAttendant, firstParkingLot);
        owner.assign(smartAttendant, secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        smartAttendant.park(firstCar);

        assertTrue(firstParkingLot.isFull());
        assertFalse(secondParkingLot.isFull());
    }

    @Test
    void testParkWhenBothHaveEqualCapacityAfterACarIsParked() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot firstParkingLot = new ParkingLot(2, owner);
        ParkingLot secondParkingLot = new ParkingLot(1, owner);
        owner.assign(smartAttendant, firstParkingLot);
        owner.assign(smartAttendant, secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        smartAttendant.park(firstCar);
        smartAttendant.park(secondCar);

        assertTrue(firstParkingLot.isFull());
        assertFalse(secondParkingLot.isFull());
    }

    // Tests for unpark() method
    @Test
    void testUnparkIfTicketIsValidForFirstCar() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot parkingLot = new ParkingLot(5, owner);
        owner.assign(smartAttendant, parkingLot);
        Car car = new Car("TS-1234", CarColor.RED);
        Ticket ticket = smartAttendant.park(car);

        assertDoesNotThrow(() -> smartAttendant.unpark(ticket));
    }

    @Test
    void testUnparkIfTicketIsValidForSecondCar() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot parkingLot = new ParkingLot(5, owner);
        owner.assign(smartAttendant, parkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        smartAttendant.park(firstCar);
        Ticket ticket =  smartAttendant.park(secondCar);

        Car unparkedCar = smartAttendant.unpark(ticket);

        assertEquals(secondCar, unparkedCar);
    }

    @Test
    void testUnparkIfTicketIsValidForSecondParkingLot() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot firstParkingLot = new ParkingLot(1, owner);
        ParkingLot secondParkingLot = new ParkingLot(5, owner);
        owner.assign(smartAttendant, firstParkingLot);
        owner.assign(smartAttendant, secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        smartAttendant.park(firstCar);
        Ticket ticket =  smartAttendant.park(secondCar);

        Car unparkedCar = smartAttendant.unpark(ticket);

        assertEquals(secondCar, unparkedCar);
    }

    @Test
    void testUnparkIfTicketIsInvalid() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot parkingLot = new ParkingLot(3, owner);
        owner.assign(smartAttendant, parkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        smartAttendant.park(firstCar);
        Ticket ticket =  smartAttendant.park(secondCar);
        smartAttendant.unpark(ticket);

        assertThrows(InvalidTicketException.class, () -> smartAttendant.unpark(ticket));
    }

    @Test
    void testUnparkIfTicketIsInvalidForSecondParkingLot() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot firstParkingLot = new ParkingLot(1, owner);
        ParkingLot secondParkingLot = new ParkingLot(5, owner);
        owner.assign(smartAttendant, firstParkingLot);
        owner.assign(smartAttendant, secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        smartAttendant.park(firstCar);
        Ticket ticket =  smartAttendant.park(secondCar);
        smartAttendant.unpark(ticket);

        assertThrows(InvalidTicketException.class, () -> smartAttendant.unpark(ticket));
    }

    @Test
    void testUnparkIfACarUnparkedInFirstParkingLotAndACarIsTryingToPark(){
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot firstParkingLot = new ParkingLot(1, owner);
        ParkingLot secondParkingLot = new ParkingLot(1, owner);
        owner.assign(smartAttendant, firstParkingLot);
        owner.assign(smartAttendant, secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        Car thirdCar = new Car("TS-1236", CarColor.RED);
        Ticket ticket = smartAttendant.park(firstCar);
        smartAttendant.park(secondCar);
        smartAttendant.unpark(ticket);

        assertDoesNotThrow(() -> smartAttendant.park(thirdCar));
    }
}