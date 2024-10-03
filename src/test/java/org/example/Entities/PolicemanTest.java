package org.example.Entities;

import org.example.Enums.CarColor;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PolicemanTest {

    // Tests for notifyWhenNull() method
    @Test
    void testNotifyWhenFullParkingLotIsFull() {
        Owner owner = new Owner();
        Policeman policeman = spy(new Policeman());
        ParkingLot parkingLot = new ParkingLot(1, owner);
        Attendant attendant = new Attendant();
        owner.assign(attendant, parkingLot);
        parkingLot.registerNotifiable(policeman);
        Car car = new Car("TS-1234", CarColor.BLACK);

        attendant.park(car);

        verify(policeman, times(1)).notifyFull(parkingLot.parkingLotId);
    }

    @Test
    void testNotifyWhenFullParkingLotIsNotFull() {
        Owner owner = new Owner();
        Policeman policeman = spy(new Policeman());
        ParkingLot parkingLot = new ParkingLot(2, owner);
        Attendant attendant = new Attendant();
        owner.assign(attendant, parkingLot);
        parkingLot.registerNotifiable(policeman);
        Car car = new Car("TS-1234", CarColor.BLACK);

        attendant.park(car);

        verify(policeman, times(0)).notifyFull(parkingLot.parkingLotId);
    }

    @Test
    void testNotifyWhenFullAllParkingLotsAreFull() {
        Owner owner = new Owner();
        Policeman policeman = spy(new Policeman());
        ParkingLot firstParkingLot = new ParkingLot(1, owner);
        ParkingLot secondParkingLot = new ParkingLot(1, owner);
        Attendant attendant = new Attendant();
        owner.assign(attendant, firstParkingLot);
        owner.assign(attendant, secondParkingLot);
        firstParkingLot.registerNotifiable(policeman);
        secondParkingLot.registerNotifiable(policeman);
        Car firstCar = new Car("TS-1234", CarColor.BLACK);
        Car secondCar = new Car("TS-1235", CarColor.BLACK);

        attendant.park(firstCar);
        attendant.park(secondCar);

        verify(policeman, times(1)).notifyFull(firstParkingLot.parkingLotId);
        verify(policeman, times(1)).notifyFull(secondParkingLot.parkingLotId);
    }

    @Test
    void testNotifyWhenFullSomeParkingLotsAreFull() {
        Owner owner = new Owner();
        Policeman policeman = spy(new Policeman());
        ParkingLot firstParkingLot = new ParkingLot(1, owner);
        ParkingLot secondParkingLot = new ParkingLot(1, owner);
        ParkingLot thirdParkingLot = new ParkingLot(2, owner);
        Attendant attendant = new Attendant();
        owner.assign(attendant, firstParkingLot);
        owner.assign(attendant, secondParkingLot);
        firstParkingLot.registerNotifiable(policeman);
        secondParkingLot.registerNotifiable(policeman);
        thirdParkingLot.registerNotifiable(policeman);
        Car firstCar = new Car("TS-1234", CarColor.BLACK);
        Car secondCar = new Car("TS-1235", CarColor.BLACK);

        attendant.park(firstCar);
        attendant.park(secondCar);

        verify(policeman, times(1)).notifyFull(firstParkingLot.parkingLotId);
        verify(policeman, times(1)).notifyFull(secondParkingLot.parkingLotId);
        verify(policeman, times(0)).notifyFull(thirdParkingLot.parkingLotId);
    }

    // Tests for notifyWhenAvailable() method
    @Test
    void testNotifyWhenAvailableParkingLotIsAvailable(){
        Owner owner = new Owner();
        Policeman policeman = spy(new Policeman());
        ParkingLot parkingLot = new ParkingLot(1, owner);
        Attendant attendant = new Attendant();
        owner.assign(attendant, parkingLot);
        parkingLot.registerNotifiable(policeman);
        Car car = new Car("TS-1234", CarColor.BLACK);

        Ticket ticket = attendant.park(car);
        attendant.unpark(ticket);

        verify(policeman, times(1)).notifyAvailable(parkingLot.parkingLotId);
    }

    @Test
    void testNotifyWhenAvailableParkingLotIsNotAvailable(){
        Owner owner = new Owner();
        Policeman policeman = spy(new Policeman());
        ParkingLot parkingLot = new ParkingLot(1, owner);
        Attendant attendant = new Attendant();
        owner.assign(attendant, parkingLot);
        parkingLot.registerNotifiable(policeman);
        Car car = new Car("TS-1234", CarColor.BLACK);

        attendant.park(car);

        verify(policeman, times(0)).notifyAvailable(parkingLot.parkingLotId);
    }

    @Test
    void testNotifyWhenAvailableSecondParkingLotIsAvailable(){
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

        verify(owner, times(1)).notifyAvailable(secondParkingLot.parkingLotId);
    }
}