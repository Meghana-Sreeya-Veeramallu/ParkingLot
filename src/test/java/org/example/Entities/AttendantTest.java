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
        ParkingLot parkingLot = owner.createParkingLot(5);
        assertDoesNotThrow(() -> owner.assign(attendant, parkingLot));
    }

    @Test
    void testAssignTwoParkingLots(){
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = owner.createParkingLot(5);
        ParkingLot secondParkingLot = owner.createParkingLot(5);

        assertDoesNotThrow(() -> owner.assign(attendant, firstParkingLot));
        assertDoesNotThrow(() -> owner.assign(attendant, secondParkingLot));
    }

    @Test
    void testAssignAParkingLotTwice(){
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = owner.createParkingLot(5);

        assertDoesNotThrow(() -> owner.assign(attendant, parkingLot));
        assertThrows(ParkingLotAlreadyAssigned.class, () -> owner.assign(attendant, parkingLot));
    }

    // Tests for assign() method for Attendant with SmartNextLotStrategy
    @Test
    void testAssignParkingLotUsingSmartAttendant() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot parkingLot = owner.createParkingLot(5);

        assertDoesNotThrow(() -> owner.assign(smartAttendant, parkingLot));
    }

    @Test
    void testAssignTwoParkingLotsUsingSmartAttendant(){
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot firstParkingLot = owner.createParkingLot(5);
        ParkingLot secondParkingLot = owner.createParkingLot(5);

        assertDoesNotThrow(() -> owner.assign(smartAttendant, firstParkingLot));
        assertDoesNotThrow(() -> owner.assign(smartAttendant, secondParkingLot));
    }

    @Test
    void testAssignAParkingLotTwiceUsingSmartAttendant(){
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot parkingLot = owner.createParkingLot(5);

        assertDoesNotThrow(() -> owner.assign(smartAttendant, parkingLot));
        assertThrows(ParkingLotAlreadyAssigned.class, () -> owner.assign(smartAttendant, parkingLot));
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
        ParkingLot parkingLot = owner.createParkingLot(5);
        owner.assign(attendant, parkingLot);
        Car car = new Car("TS-1234", CarColor.RED);

        assertDoesNotThrow(() -> attendant.park(car));
    }

    @Test
    void testParkShouldReturnTicket() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = owner.createParkingLot(5);
        owner.assign(attendant, parkingLot);
        Car car = new Car("TS-1234", CarColor.RED);

        Ticket ticket = attendant.park(car);

        assertNotNull(ticket);
    }

    @Test
    void testParkIfFirstParkingLotIsFull() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(1);
        owner.assign(attendant, firstParkingLot);
        owner.assign(attendant, secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);

        assertDoesNotThrow(() -> attendant.park(firstCar));
        assertDoesNotThrow(() -> attendant.park(secondCar));
    }

    @Test
    void testParkIfAllParkingLotsAreFull() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(1);
        owner.assign(attendant, firstParkingLot);
        owner.assign(attendant, secondParkingLot);
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
        ParkingLot parkingLot = owner.createParkingLot(5);
        owner.assign(attendant, parkingLot);
        Car car = new Car("TS-1234", CarColor.RED);
        attendant.park(car);

        assertThrows(CarAlreadyParkedException.class, () -> attendant.park(car));
    }

    @Test
    void testParkIfCarIsAlreadyParkedInAnotherParkingLot() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(5);
        owner.assign(attendant, firstParkingLot);
        owner.assign(attendant, secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        attendant.park(firstCar);
        attendant.park(secondCar);

        assertThrows(CarAlreadyParkedException.class, () -> attendant.park(firstCar));
    }

    // Tests for park() mthod for Attendant with SmartNextLotStrategy
    @Test
    void testParkWhenSecondParkingLotHasMoreAvailableSlots() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(2);
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
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(1);
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
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        ParkingLot secondParkingLot = owner.createParkingLot(1);
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
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = owner.createParkingLot(5);
        owner.assign(attendant, parkingLot);
        Car car = new Car("TS-1234", CarColor.RED);
        Ticket ticket = attendant.park(car);

        assertDoesNotThrow(() -> attendant.unpark(ticket));
    }

    @Test
    void testUnparkIfTicketIsValidForSecondCar() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = owner.createParkingLot(5);
        owner.assign(attendant, parkingLot);
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
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(5);
        owner.assign(attendant, firstParkingLot);
        owner.assign(attendant, secondParkingLot);
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
        ParkingLot parkingLot = owner.createParkingLot(3);
        owner.assign(attendant, parkingLot);
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
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(5);
        owner.assign(attendant, firstParkingLot);
        owner.assign(attendant, secondParkingLot);
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
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(1);
        owner.assign(attendant, firstParkingLot);
        owner.assign(attendant, secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        Car thirdCar = new Car("TS-1236", CarColor.RED);
        Ticket ticket = attendant.park(firstCar);
        attendant.park(secondCar);
        attendant.unpark(ticket);

        assertDoesNotThrow(() -> attendant.park(thirdCar));
    }

    // Tests for unpark() method for Attendant with SmartNextLotStrategy
    @Test
    void testUnparkIfTicketIsValidForFirstCarUsingSmartAttendant() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot parkingLot = owner.createParkingLot(5);
        owner.assign(smartAttendant, parkingLot);
        Car car = new Car("TS-1234", CarColor.RED);
        Ticket ticket = smartAttendant.park(car);

        assertDoesNotThrow(() -> smartAttendant.unpark(ticket));
    }

    @Test
    void testUnparkIfTicketIsValidForSecondCarUsingSmartAttendant() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot parkingLot = owner.createParkingLot(5);
        owner.assign(smartAttendant, parkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        smartAttendant.park(firstCar);
        Ticket ticket =  smartAttendant.park(secondCar);

        Car unparkedCar = smartAttendant.unpark(ticket);

        assertEquals(secondCar, unparkedCar);
    }

    @Test
    void testUnparkIfTicketIsValidForSecondParkingLotUsingSmartAttendant() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(5);
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
    void testUnparkIfTicketIsInvalidUsingSmartAttendant() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot parkingLot = owner.createParkingLot(3);
        owner.assign(smartAttendant, parkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        smartAttendant.park(firstCar);
        Ticket ticket =  smartAttendant.park(secondCar);
        smartAttendant.unpark(ticket);

        assertThrows(InvalidTicketException.class, () -> smartAttendant.unpark(ticket));
    }

    @Test
    void testUnparkIfTicketIsInvalidForSecondParkingLotUsingSmartAttendant() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(5);
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
    void testUnparkIfACarUnparkedInFirstParkingLotAndACarIsTryingToParkUsingSmartAttendant(){
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(1);
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

    @Test
    void testAttendantAndSmartAttendantParkConsecutively() {
        Owner owner = new Owner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        ParkingLot secondParkingLot = owner.createParkingLot(2);
        ParkingLot thirdParkingLot = owner.createParkingLot(2);
        Attendant attendant = new Attendant();
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        owner.assign(attendant, firstParkingLot);
        owner.assign(attendant, secondParkingLot);
        owner.assign(smartAttendant, secondParkingLot);
        owner.assign(smartAttendant, thirdParkingLot);
        Car firstCar = new Car("TS-1231", CarColor.RED);
        Car secondCar = new Car("TS-1232", CarColor.BLUE);
        Car thirdCar = new Car("TS-1233", CarColor.GREEN);
        Car fourthCar = new Car("TS-1234", CarColor.BLACK);
        Car fifthCar = new Car("TS-1235", CarColor.YELLOW);

        attendant.park(firstCar);
        smartAttendant.park(secondCar);
        assertFalse(firstParkingLot.isFull());
        attendant.park(thirdCar);
        assertTrue(firstParkingLot.isFull());
        smartAttendant.park(fourthCar);
        assertFalse(secondParkingLot.isFull());
        attendant.park(fifthCar);
        assertTrue(secondParkingLot.isFull());
        assertFalse(thirdParkingLot.isFull());
    }

    @Test
    void testSmartAttendantAndAttendantParkConsecutively() {
        Owner owner = new Owner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        ParkingLot secondParkingLot = owner.createParkingLot(2);
        ParkingLot thirdParkingLot = owner.createParkingLot(2);
        Attendant smartAttendant = new Attendant(new SmartNextLotStrategy());
        Attendant attendant = new Attendant();
        owner.assign(smartAttendant, secondParkingLot);
        owner.assign(smartAttendant, thirdParkingLot);
        owner.assign(attendant, firstParkingLot);
        owner.assign(attendant, secondParkingLot);
        Car firstCar = new Car("TS-1231", CarColor.RED);
        Car secondCar = new Car("TS-1232", CarColor.BLUE);
        Car thirdCar = new Car("TS-1233", CarColor.GREEN);
        Car fourthCar = new Car("TS-1234", CarColor.BLACK);
        Car fifthCar = new Car("TS-1235", CarColor.YELLOW);

        smartAttendant.park(firstCar);
        attendant.park(secondCar);
        assertFalse(firstParkingLot.isFull());
        assertFalse(secondParkingLot.isFull());
        smartAttendant.park(thirdCar);
        assertFalse(secondParkingLot.isFull());
        attendant.park(fourthCar);
        assertTrue(firstParkingLot.isFull());
        smartAttendant.park(fifthCar);
        assertFalse(thirdParkingLot.isFull());
    }
}