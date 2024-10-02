package org.example.Entities;

public interface Attendant {
    void assign(ParkingLot parkingLot);
    void checkIfCarIsParked(Car car);
    Ticket park(Car car);
    Car unpark(Ticket ticket);
}
