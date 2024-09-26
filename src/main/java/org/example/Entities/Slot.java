package org.example.Entities;

import org.example.Exceptions.InvalidTicketException;

public class Slot {
    private Car car;
    private Ticket ticket;

    public Slot() {
        this.car = null;
        this.ticket = null;
    }

    public boolean isEmpty(){
        return car == null;
    }

    public Ticket park(Car car){
        this.car = car;
        this.ticket = new Ticket();
        return ticket;
    }

    public Car unpark(Ticket ticket){
        if (this.ticket != null && this.ticket.isSameTicket(ticket)){
            Car car = this.car;
            this.car = null;
            this.ticket = null;
            return car;
        }
        throw new InvalidTicketException("Car not found");
    }

    public boolean isCarParked(Car car) {
        return !isEmpty() && this.car.isSameCar(car);
    }
}