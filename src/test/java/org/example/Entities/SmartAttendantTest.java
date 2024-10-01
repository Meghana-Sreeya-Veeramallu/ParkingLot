package org.example.Entities;

import org.example.Enums.CarColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartAttendantTest {
    @Test
    void testParkWhenSmartAttendantParksTwoCars() {
        Attendant smartAttendant = new SmartAttendant();
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLot secondParkingLot = new ParkingLot(1);
        smartAttendant.assign(firstParkingLot);
        smartAttendant.assign(secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        smartAttendant.park(firstCar);
        smartAttendant.park(secondCar);

        assertFalse(firstParkingLot.isFull());
        assertTrue(secondParkingLot.isFull());
    }

    @Test
    void testParkWhenSmartAttendantParksFirstCarAndAttendantParksSecondCar() {
        Attendant smartAttendant = new SmartAttendant();
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLot secondParkingLot = new ParkingLot(1);
        smartAttendant.assign(firstParkingLot);
        smartAttendant.assign(secondParkingLot);
        attendant.assign(firstParkingLot);
        attendant.assign(secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        smartAttendant.park(firstCar);
        attendant.park(secondCar);

        assertTrue(firstParkingLot.isFull());
        assertFalse(secondParkingLot.isFull());
    }

    @Test
    void testParkWithThreeParkingLots() {
        Attendant smartAttendant = new SmartAttendant();
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLot secondParkingLot = new ParkingLot(3);
        ParkingLot thirdParkingLot = new ParkingLot(2);
        smartAttendant.assign(firstParkingLot);
        smartAttendant.assign(secondParkingLot);
        smartAttendant.assign(thirdParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);
        Car thirdCar = new Car("TS-1236", CarColor.GREEN);
        Car fourthCar = new Car("TS-1237", CarColor.YELLOW);
        Car fifthCar = new Car("TS-1238", CarColor.BLACK);
        Car sixthCar = new Car("TS-1239", CarColor.WHITE);
        smartAttendant.park(firstCar);
        smartAttendant.park(secondCar);
        smartAttendant.park(thirdCar);
        smartAttendant.park(fourthCar);
        smartAttendant.park(fifthCar);
        smartAttendant.park(sixthCar);

        assertFalse(secondParkingLot.isFull());
    }
}