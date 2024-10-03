package org.example.Entities;

public interface Attendable {
    void assign(ParkingLot parkingLot);
    void checkIfCarIsParked(Car car);
    Ticket park(Car car);
    Car unpark(Ticket ticket);
}
