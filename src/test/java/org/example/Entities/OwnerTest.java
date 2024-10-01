package org.example.Entities;

import org.example.Enums.CarColor;
import org.example.Exceptions.ParkingLotAlreadyAssigned;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {
    @Test
    void testAssignAttendantToParkingLot() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(2);
        Attendant attendant = new Attendant();
        owner.assign(attendant, parkingLot);
        Car car = new Car("TS-1234", CarColor.BLACK);
        Ticket ticket = attendant.park(car);

        assertNotNull(ticket);
    }

    @Test
    void testAssignAttendantToMultipleParkingLots() {
        Owner owner = new Owner();
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLot secondParkingLot = new ParkingLot(2);
        Attendant attendant = new Attendant();
        owner.assign(attendant, firstParkingLot);
        owner.assign(attendant, secondParkingLot);
        Car car = new Car("TS-1234", CarColor.BLACK);
        Ticket ticket = attendant.park(car);

        assertNotNull(ticket);
    }

    @Test
    void testAssignMultipleAttendantsToAParkingLot(){
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(10);
        Attendant firstAttendant = new Attendant();
        Attendant secondAttendant = new Attendant();
        owner.assign(firstAttendant, parkingLot);
        owner.assign(secondAttendant, parkingLot);
        Car firstCar = new Car("TS-1234", CarColor.BLACK);
        Ticket firstTicket = firstAttendant.park(firstCar);
        Car secondCar = new Car("TS-1235", CarColor.BLACK);
        Ticket secondTicket = secondAttendant.park(secondCar);

        assertNotNull(firstTicket);
        assertNotNull(secondTicket);
    }

    @Test
    void testAssignOwnerToParkingLot() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(5);
        owner.assign(parkingLot);
        Car car = new Car("TS-1234", CarColor.BLACK);
        Ticket ticket = owner.park(car);

        assertNotNull(ticket);
    }

    @Test
    void testAssignMultipleAttendantsToAParkingLotIncludingOwner(){
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(10);
        Attendant firstAttendant = new Attendant();
        Attendant secondAttendant = new Attendant();
        owner.assign(firstAttendant, parkingLot);
        owner.assign(secondAttendant, parkingLot);
        owner.assign(owner, parkingLot);
        Car car = new Car("TS-1234", CarColor.BLACK);
        Ticket ticket = owner.park(car);

        assertNotNull(ticket);
    }

    @Test
    void testAssignSameAttendantToAParkingLotTwice(){
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(10);
        Attendant attendant = new Attendant();
        owner.assign(attendant, parkingLot);

        assertThrows(ParkingLotAlreadyAssigned.class, () -> owner.assign(attendant, parkingLot));
    }
}