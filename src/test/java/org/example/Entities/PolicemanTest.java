package org.example.Entities;

import org.example.Enums.CarColor;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PolicemanTest {

    // Tests for notifyWhenNull() method
    @Test
    void testNotifyWhenFullWhenParkingLotIsFull() {
        Policeman policeman = spy(new Policeman());
        ParkingLot parkingLot = new ParkingLot(1);
        BasicAttendant attendant = new BasicAttendant();
        attendant.assign(parkingLot);
        parkingLot.registerNotifiable(policeman);
        Car car = new Car("TS-1234", CarColor.BLACK);

        attendant.park(car);

        verify(policeman, times(1)).notifyWhenFull(parkingLot);
    }

    @Test
    void testNotifyWhenFullWhenParkingLotIsNotFull() {
        Policeman policeman = spy(new Policeman());
        ParkingLot parkingLot = new ParkingLot(2);
        BasicAttendant attendant = new BasicAttendant();
        attendant.assign(parkingLot);
        parkingLot.registerNotifiable(policeman);
        Car car = new Car("TS-1234", CarColor.BLACK);

        attendant.park(car);

        verify(policeman, times(0)).notifyWhenFull(parkingLot);
    }

    @Test
    void testNotifyWhenFullWhenAllParkingLotsAreFull() {
        Policeman policeman = spy(new Policeman());
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        BasicAttendant attendant = new BasicAttendant();
        attendant.assign(firstParkingLot);
        attendant.assign(secondParkingLot);
        firstParkingLot.registerNotifiable(policeman);
        secondParkingLot.registerNotifiable(policeman);
        Car firstCar = new Car("TS-1234", CarColor.BLACK);
        Car secondCar = new Car("TS-1235", CarColor.BLACK);

        attendant.park(firstCar);
        attendant.park(secondCar);

        verify(policeman, times(1)).notifyWhenFull(firstParkingLot);
        verify(policeman, times(1)).notifyWhenFull(secondParkingLot);
    }

    @Test
    void testNotifyWhenFullWhenSomeParkingLotsAreFull() {
        Policeman policeman = spy(new Policeman());
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        ParkingLot thirdParkingLot = new ParkingLot(2);
        BasicAttendant attendant = new BasicAttendant();
        attendant.assign(firstParkingLot);
        attendant.assign(secondParkingLot);
        firstParkingLot.registerNotifiable(policeman);
        secondParkingLot.registerNotifiable(policeman);
        thirdParkingLot.registerNotifiable(policeman);
        Car firstCar = new Car("TS-1234", CarColor.BLACK);
        Car secondCar = new Car("TS-1235", CarColor.BLACK);

        attendant.park(firstCar);
        attendant.park(secondCar);

        verify(policeman, times(1)).notifyWhenFull(firstParkingLot);
        verify(policeman, times(1)).notifyWhenFull(secondParkingLot);
        verify(policeman, times(0)).notifyWhenFull(thirdParkingLot);
    }

    // Tests for notifyWhenAvailable() method
    @Test
    void testNotifyWhenAvailableWhenParkingLotIsAvailable(){
        Policeman policeman = spy(new Policeman());
        ParkingLot parkingLot = new ParkingLot(1);
        BasicAttendant attendant = new BasicAttendant();
        attendant.assign(parkingLot);
        parkingLot.registerNotifiable(policeman);
        Car car = new Car("TS-1234", CarColor.BLACK);

        Ticket ticket = attendant.park(car);
        attendant.unpark(ticket);

        verify(policeman, times(1)).notifyWhenAvailable(parkingLot);
    }

    @Test
    void testNotifyWhenAvailableWhenParkingLotIsNotAvailable(){
        Policeman policeman = spy(new Policeman());
        ParkingLot parkingLot = new ParkingLot(1);
        BasicAttendant attendant = new BasicAttendant();
        attendant.assign(parkingLot);
        parkingLot.registerNotifiable(policeman);
        Car car = new Car("TS-1234", CarColor.BLACK);

        attendant.park(car);

        verify(policeman, times(0)).notifyWhenAvailable(parkingLot);
    }

    @Test
    void testNotifyWhenAvailableWhenSecondParkingLotIsAvailable(){
        Owner owner = spy(new Owner());
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLot secondParkingLot = new ParkingLot(1);
        owner.assign(owner, firstParkingLot);
        owner.assign(owner, secondParkingLot);
        Car firstCar = new Car("TS-1234", CarColor.BLACK);
        Car secondCar = new Car("TS-1235", CarColor.BLACK);
        Car thirdCar = new Car("TS-1236", CarColor.BLACK);

        owner.park(firstCar);
        owner.park(secondCar);
        Ticket ticket = owner.park(thirdCar);
        owner.unpark(ticket);

        verify(owner, times(1)).notifyWhenAvailable(secondParkingLot);
    }
}