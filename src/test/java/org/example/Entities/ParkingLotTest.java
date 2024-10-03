package org.example.Entities;

import org.example.Enums.CarColor;
import org.example.Exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingLotTest {

    // Tests for ParkingLot class
    @Test
    void testExceptionForParkingLotWithCapacity0() {
        Owner owner = new Owner();
        assertThrows(CannotCreateAParkingLotException.class, () -> {new ParkingLot(0, owner);});
    }
    
    @Test
    void testExceptionForParkingLotWithOwnerAsNull() {
        assertThrows(CannotCreateAParkingLotException.class, () -> {new ParkingLot(5, null);});
    }

    @Test
    void testParkingLotWith5Slots() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(5, owner);

        assertNotNull(parkingLot);
    }

    // Tests for park() method
    @Test
    void testParkCarWhenParkingLotIsEmpty() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(2, owner);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Ticket ticket = parkingLot.park(firstCar);

        assertNotNull(ticket);
    }

    @Test
    void testParkCarWhenParkingLotIsNotEmpty() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(2, owner);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        parkingLot.park(firstCar);

        parkingLot.park(secondCar);
    }

    @Test
    void testParkingWhenParkingLotIsFull() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(1, owner);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);

        parkingLot.park(firstCar);

        assertThrows(ParkingLotFullException.class, () -> parkingLot.park(secondCar));
    }

    @Test
    void testParkCarWhenCarIsAlreadyParked() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(2, owner);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1234", CarColor.RED);
        parkingLot.park(firstCar);

        assertThrows(CarAlreadyParkedException.class, () -> parkingLot.park(secondCar));
    }

    // Tests for unpark() method
    @Test
    void testUnparkCarWithValidTicket() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(2, owner);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Ticket ticket = parkingLot.park(firstCar);

        assertDoesNotThrow(() -> parkingLot.unpark(ticket));
    }

    @Test
    void testUnparkCarShouldReturnTheParkedCar() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(2, owner);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Ticket ticket = parkingLot.park(firstCar);

        Car actualCar = parkingLot.unpark(ticket);

        assertEquals(firstCar, actualCar);
    }

    @Test
    void testUnparkCarWithInvalidTicket() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(2, owner);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        parkingLot.park(firstCar);

        Ticket invalidTicket = new Ticket();

        assertThrows(InvalidTicketException.class, () -> parkingLot.unpark(invalidTicket));
    }

    // Tests for countCarsByColor() method
    @Test
    void testCountCarsByColorWhenNoCarIsParked() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(5, owner);
        int expectedCount = 0;

        int actualCount = parkingLot.countCarsByColor(CarColor.RED);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    void testCountCarsByColorWhenCarsAreParked() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(5, owner);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        Car thirdCar = new Car("TS-1236", CarColor.RED);
        parkingLot.park(firstCar);
        parkingLot.park(secondCar);
        parkingLot.park(thirdCar);
        int expectedCount = 2;

        int actualCount = parkingLot.countCarsByColor(CarColor.RED);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    void testCountCarsByColorWhenCarsAreParkedBySpyingOnParkingLot() {
        Owner owner = new Owner();
        ParkingLot parkingLot = spy(new ParkingLot(5, owner));
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        Car thirdCar = new Car("TS-1236", CarColor.RED);
        parkingLot.park(firstCar);
        parkingLot.park(secondCar);
        parkingLot.park(thirdCar);

        parkingLot.countCarsByColor(CarColor.RED);

        verify(parkingLot, times(1)).countCarsByColor(CarColor.RED);
    }

    // Tests for getSlotNumberByRegistrationNumber() method
    @Test
    void testGetCarSlotNumberByRegistrationNumberWhenParkingLotIsEmpty() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(5, owner);
        String registrationNumber = "TS-1234";

        assertThrows(CarNotFoundException.class, () -> parkingLot.getCarSlotNumberByRegistrationNumber(registrationNumber));
    }

    @Test
    void testGetCarSlotNumberByRegistrationNumberWhenCarIsParked() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(5, owner);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        parkingLot.park(firstCar);
        String registrationNumber = "TS-1234";
        int expectedSlotNumber = 1;

        int actualSlotNumber = parkingLot.getCarSlotNumberByRegistrationNumber(registrationNumber);

        assertEquals(expectedSlotNumber, actualSlotNumber);
    }

    @Test
    void testGetCarSlotNumberByRegistrationNumberWhenCarIsParkedSecond() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(5, owner);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        parkingLot.park(firstCar);
        parkingLot.park(secondCar);
        String registrationNumber = "TS-1235";
        int expectedSlotNumber = 2;

        int actualSlotNumber = parkingLot.getCarSlotNumberByRegistrationNumber(registrationNumber);

        assertEquals(expectedSlotNumber, actualSlotNumber);
    }

    @Test
    void testGetCarSlotNumberByRegistrationNumberWhenCarIsNotPresent() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(2, owner);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        parkingLot.park(firstCar);
        parkingLot.park(secondCar);
        String registrationNumber = "TS-1236";

        assertThrows(CarNotFoundException.class, () -> parkingLot.getCarSlotNumberByRegistrationNumber(registrationNumber));
    }

    @Test
    void testGetCarSlotNumberByRegistrationNumberWhenCarIsParkedByAndUnparked(){
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(5, owner);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Ticket ticket = parkingLot.park(firstCar);
        parkingLot.unpark(ticket);
        String registrationNumber = "TS-1234";

        assertThrows(CarNotFoundException.class, () -> parkingLot.getCarSlotNumberByRegistrationNumber(registrationNumber));
    }

    // Tests for countAvailableSlots() method
    @Test
    void testCountAvailableSlotsWhenParkingLotIsEmpty() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(5, owner);
        int expectedCount = 5;

        int actualCount = parkingLot.countAvailableSlots();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    void testCountAvailableSlotsWhenSomeSlotsAreOccupied() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(5, owner);
        parkingLot.park(new Car("TS-1234", CarColor.RED));
        parkingLot.park(new Car("TS-1235", CarColor.BLUE));
        int expectedCount = 3;

        int actualCount = parkingLot.countAvailableSlots();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    void testCountAvailableSlotsWhenParkingLotIsFull() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(5, owner);
        parkingLot.park(new Car("TS-1234", CarColor.RED));
        parkingLot.park(new Car("TS-1235", CarColor.BLUE));
        parkingLot.park(new Car("TS-1236", CarColor.GREEN));
        parkingLot.park(new Car("TS-1237", CarColor.BLACK));
        parkingLot.park(new Car("TS-1230", CarColor.WHITE));

        int expectedCount = 0;

        int actualCount = parkingLot.countAvailableSlots();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    void testCountAvailableSlotsAfterUnparkingACar() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(5, owner);
        Ticket ticket = parkingLot.park(new Car("TS-1234", CarColor.RED));
        parkingLot.park(new Car("TS-1245", CarColor.BLUE));
        parkingLot.unpark(ticket);

        int expectedCount = 4;

        int actualCount = parkingLot.countAvailableSlots();

        assertEquals(expectedCount, actualCount);
    }
}