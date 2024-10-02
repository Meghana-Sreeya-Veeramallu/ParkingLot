package org.example.Entities;

import org.example.Enums.CarColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartAttendantTest {
    @Test
    void testParkWhenSecondParkingLotHasMoreAvailableSlots() {
        Attendant smartAttendant = new SmartAttendant();
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(2);
        smartAttendant.assign(firstParkingLot);
        smartAttendant.assign(secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        smartAttendant.park(firstCar);

        assertFalse(firstParkingLot.isFull());
    }

    @Test
    void testParkWhenBothHaveEqualCapacity() {
        Attendant smartAttendant = new SmartAttendant();
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        smartAttendant.assign(firstParkingLot);
        smartAttendant.assign(secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        smartAttendant.park(firstCar);

        assertTrue(firstParkingLot.isFull());
        assertFalse(secondParkingLot.isFull());
    }

    @Test
    void testParkWhenBothHaveEqualCapacityAfterACarIsParked() {
        Attendant smartAttendant = new SmartAttendant();
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLot secondParkingLot = new ParkingLot(1);
        smartAttendant.assign(firstParkingLot);
        smartAttendant.assign(secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        smartAttendant.park(firstCar);
        smartAttendant.park(secondCar);

        assertTrue(firstParkingLot.isFull());
        assertFalse(secondParkingLot.isFull());
    }
}