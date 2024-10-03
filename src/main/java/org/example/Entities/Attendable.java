package org.example.Entities;

public interface Attendable {
    void checkIfCarIsParked(Car car);
    Ticket park(Car car);
    Car unpark(Ticket ticket);
}
