package org.example.Entities;

import org.example.Enums.CarColor;
import org.example.Exceptions.CannotAssignAttendantException;
import org.example.Exceptions.CannotCreateAParkingLotException;
import org.example.Exceptions.ParkingLotAlreadyAssigned;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OwnerTest {
    @Test
    void testParkingLotCreationWithoutOwner() {
        assertThrows(CannotCreateAParkingLotException.class, () -> {
            new ParkingLot(5, null);
        });
    }

    @Test
    void testParkingLotCreationWithOwner() {
        Owner owner = new Owner();
        assertDoesNotThrow(() -> {
            new ParkingLot(5, owner);
        });
    }

    @Test
    void testAssignAttendantToParkingLot() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(2, owner);
        Attendant attendant = new Attendant();
        owner.assign(attendant, parkingLot);
        Car car = new Car("TS-1234", CarColor.BLACK);
        Ticket ticket = attendant.park(car);

        assertNotNull(ticket);
    }

    @Test
    void testAssignAttendantToMultipleParkingLots() {
        Owner owner = new Owner();
        ParkingLot firstParkingLot = new ParkingLot(2, owner);
        ParkingLot secondParkingLot = new ParkingLot(2, owner);
        Attendant attendant = new Attendant();
        owner.assign(attendant, firstParkingLot);
        owner.assign(attendant, secondParkingLot);
        Car car = new Car("TS-1234", CarColor.BLACK);
        Ticket ticket = attendant.park(car);

        assertNotNull(ticket);
    }

    @Test
    void testAssignMultipleAttendantsToAParkingLot() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(10, owner);
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
        ParkingLot parkingLot = new ParkingLot(5, owner);
        owner.assign(owner, parkingLot);
        Car car = new Car("TS-1234", CarColor.BLACK);
        Ticket ticket = owner.park(car);

        assertNotNull(ticket);
    }

    @Test
    void testAssignMultipleAttendantsToAParkingLotIncludingOwner() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(10, owner);
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
    void testAssignSameAttendantToAParkingLotTwice() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(10, owner);
        Attendant attendant = new Attendant();
        owner.assign(attendant, parkingLot);

        assertThrows(ParkingLotAlreadyAssigned.class, () -> owner.assign(attendant, parkingLot));
    }

    @Test
    void testAssignAttendantToParkingLotWithDifferentOwner() {
        Owner firstOwner = new Owner();
        Owner secondOwner = new Owner();
        ParkingLot parkingLot = new ParkingLot(2, firstOwner);
        Attendant attendant = new Attendant();

        assertThrows(CannotAssignAttendantException.class, () -> secondOwner.assign(attendant, parkingLot));
    }

    @Test
    void testAssignOwnerToParkingLotWithDifferentOwner() {
        Owner firstOwner = new Owner();
        Owner secondOwner = new Owner();
        ParkingLot parkingLot = new ParkingLot(5, firstOwner);

        assertThrows(CannotAssignAttendantException.class, () -> secondOwner.assign(secondOwner, parkingLot));
    }

    // Tests for notifyWhenNull() method
    @Test
    void testNotifyWhenFullParkingLotIsFull() {
        Owner owner = spy(new Owner());
        ParkingLot parkingLot = spy(new ParkingLot(1, owner));
        owner.assign(owner, parkingLot);
        Car car = new Car("TS-1234", CarColor.BLACK);

        Ticket ticket = owner.park(car);
        assertNotNull(ticket);
        assertTrue(parkingLot.isFull());

        verify(owner, times(1)).notifyFull(parkingLot.getParkingLotId());
    }

    @Test
    void testNotifyWhenFullParkingLotIsNotFull() {
        Owner owner = spy(new Owner());
        ParkingLot parkingLot = new ParkingLot(2, owner);
        owner.assign(owner, parkingLot);
        Car car = new Car("TS-1234", CarColor.BLACK);

        owner.park(car);

        verify(owner, times(0)).notifyFull(parkingLot.getParkingLotId());
    }

    @Test
    void testNotifyWhenFullMultipleParkingLotsAreFull() {
        Owner owner = spy(new Owner());
        ParkingLot firstParkingLot = new ParkingLot(1, owner);
        ParkingLot secondParkingLot = new ParkingLot(2, owner);
        owner.assign(owner, firstParkingLot);
        owner.assign(owner, secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.BLACK);
        Car secondCar = new Car("TS-1235", CarColor.BLACK);
        Car thirdCar = new Car("TS-1236", CarColor.BLACK);

        owner.park(firstCar);
        owner.park(secondCar);
        owner.park(thirdCar);

        verify(owner, times(1)).notifyFull(firstParkingLot.getParkingLotId());
        verify(owner, times(1)).notifyFull(secondParkingLot.getParkingLotId());
    }

    // Tests for notifyWhenAvailable() method
    @Test
    void testNotifyWhenAvailableParkingLotIsAvailable() {
        Owner owner = spy(new Owner());
        ParkingLot parkingLot = new ParkingLot(1, owner);
        owner.assign(owner, parkingLot);
        Car car = new Car("TS-1234", CarColor.BLACK);

        Ticket ticket = owner.park(car);
        owner.unpark(ticket);

        verify(owner, times(1)).notifyAvailable(parkingLot.getParkingLotId());
    }

    @Test
    void testNotifyWhenAvailableParkingLotIsNotAvailable() {
        Owner owner = spy(new Owner());
        ParkingLot parkingLot = new ParkingLot(3, owner);
        owner.assign(owner, parkingLot);
        Car firstCar = new Car("TS-1234", CarColor.BLACK);
        Car secondCar = new Car("TS-1235", CarColor.BLACK);

        Ticket ticket = owner.park(firstCar);
        owner.park(secondCar);
        owner.unpark(ticket);

        verify(owner, times(0)).notifyAvailable(parkingLot.getParkingLotId());
    }

    @Test
    void testNotifyWhenAvailableSecondParkingLotIsAvailable() {
        Owner owner = spy(new Owner());
        ParkingLot firstParkingLot = new ParkingLot(2, owner);
        ParkingLot secondParkingLot = new ParkingLot(1, owner);
        owner.assign(owner, firstParkingLot);
        owner.assign(owner, secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.BLACK);
        Car secondCar = new Car("TS-1235", CarColor.BLACK);
        Car thirdCar = new Car("TS-1236", CarColor.BLACK);

        owner.park(firstCar);
        owner.park(secondCar);
        Ticket ticket = owner.park(thirdCar);
        owner.unpark(ticket);

        verify(owner, times(1)).notifyAvailable(secondParkingLot.getParkingLotId());
    }
}