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
        ParkingLot parkingLot = owner.createParkingLot(1);
        Attendant attendant = new Attendant();
        owner.assign(attendant, parkingLot);
        owner.registerNotifiable(parkingLot, policeman);
        Car car = new Car("TS-1234", CarColor.BLACK);

        attendant.park(car);

        verify(policeman, times(1)).notifyFull(parkingLot.getParkingLotId());
    }

    @Test
    void testNotifyWhenFullParkingLotIsNotFull() {
        Owner owner = new Owner();
        Policeman policeman = spy(new Policeman());
        ParkingLot parkingLot = owner.createParkingLot(2);
        Attendant attendant = new Attendant();
        owner.assign(attendant, parkingLot);
        owner.registerNotifiable(parkingLot, policeman);
        Car car = new Car("TS-1234", CarColor.BLACK);

        attendant.park(car);

        verify(policeman, times(0)).notifyFull(parkingLot.getParkingLotId());
    }

    @Test
    void testNotifyWhenFullAllParkingLotsAreFull() {
        Owner owner = new Owner();
        Policeman policeman = spy(new Policeman());
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(1);
        Attendant attendant = new Attendant();
        owner.assign(attendant, firstParkingLot);
        owner.assign(attendant, secondParkingLot);
        owner.registerNotifiable(firstParkingLot, policeman);
        owner.registerNotifiable(secondParkingLot, policeman);
        Car firstCar = new Car("TS-1234", CarColor.BLACK);
        Car secondCar = new Car("TS-1235", CarColor.BLACK);

        attendant.park(firstCar);
        attendant.park(secondCar);

        verify(policeman, times(1)).notifyFull(firstParkingLot.getParkingLotId());
        verify(policeman, times(1)).notifyFull(secondParkingLot.getParkingLotId());
    }

    @Test
    void testNotifyWhenFullSomeParkingLotsAreFull() {
        Owner owner = new Owner();
        Policeman policeman = spy(new Policeman());
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(1);
        ParkingLot thirdParkingLot = owner.createParkingLot(2);
        Attendant attendant = new Attendant();
        owner.assign(attendant, firstParkingLot);
        owner.assign(attendant, secondParkingLot);
        owner.registerNotifiable(firstParkingLot, policeman);
        owner.registerNotifiable(secondParkingLot, policeman);
        owner.registerNotifiable(thirdParkingLot, policeman);
        Car firstCar = new Car("TS-1234", CarColor.BLACK);
        Car secondCar = new Car("TS-1235", CarColor.BLACK);

        attendant.park(firstCar);
        attendant.park(secondCar);

        verify(policeman, times(1)).notifyFull(firstParkingLot.getParkingLotId());
        verify(policeman, times(1)).notifyFull(secondParkingLot.getParkingLotId());
        verify(policeman, times(0)).notifyFull(thirdParkingLot.getParkingLotId());
    }

    // Tests for notifyWhenAvailable() method
    @Test
    void testNotifyWhenAvailableParkingLotIsAvailable(){
        Owner owner = new Owner();
        Policeman policeman = spy(new Policeman());
        ParkingLot parkingLot = owner.createParkingLot(1);
        Attendant attendant = new Attendant();
        owner.assign(attendant, parkingLot);
        owner.registerNotifiable(parkingLot, policeman);
        Car car = new Car("TS-1234", CarColor.BLACK);

        Ticket ticket = attendant.park(car);
        attendant.unpark(ticket);

        verify(policeman, times(1)).notifyAvailable(parkingLot.getParkingLotId());
    }

    @Test
    void testNotifyWhenAvailableParkingLotIsNotAvailable(){
        Owner owner = new Owner();
        Policeman policeman = spy(new Policeman());
        ParkingLot parkingLot = owner.createParkingLot(1);
        Attendant attendant = new Attendant();
        owner.assign(attendant, parkingLot);
        owner.registerNotifiable(parkingLot, policeman);
        Car car = new Car("TS-1234", CarColor.BLACK);

        attendant.park(car);

        verify(policeman, times(0)).notifyAvailable(parkingLot.getParkingLotId());
    }

    @Test
    void testNotifyWhenAvailableSecondParkingLotIsAvailable(){
        Owner owner = new Owner();
        Policeman policeman = spy(new Policeman());
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        ParkingLot secondParkingLot = owner.createParkingLot(1);
        owner.assign(owner, firstParkingLot);
        owner.assign(owner, secondParkingLot);
        owner.registerNotifiable(firstParkingLot, policeman);
        owner.registerNotifiable(secondParkingLot, policeman);
        Car firstCar = new Car("TS-1234", CarColor.BLACK);
        Car secondCar = new Car("TS-1235", CarColor.BLACK);
        Car thirdCar = new Car("TS-1236", CarColor.BLACK);

        owner.park(firstCar);
        owner.park(secondCar);
        Ticket ticket = owner.park(thirdCar);
        owner.unpark(ticket);

        verify(policeman, times(1)).notifyAvailable(secondParkingLot.getParkingLotId());
    }
}