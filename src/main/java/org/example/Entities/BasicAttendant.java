package org.example.Entities;

import java.util.ArrayList;

public class BasicAttendant implements Attendant {
    protected ArrayList<ParkingLot> parkingLots;

    public BasicAttendant(){
        this.parkingLots = new ArrayList<>();
    }

    @Override
    public void assign(ParkingLot parkingLot) {
        assignLot(parkingLot, this.parkingLots);
    }

    @Override
    public void checkIfCarIsParked(Car car) {
        checkIfCarIsAlreadyParked(car, this.parkingLots);
    }

    @Override
    public Ticket park(Car car) {
        return parkCar(car, this.parkingLots);
    }

    @Override
    public Car unpark(Ticket ticket) {
        return unparkCar(ticket, this.parkingLots);
    }
}
