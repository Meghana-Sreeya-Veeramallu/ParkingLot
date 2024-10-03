package org.example.Entities;

import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.NoParkingLotAssigned;
import org.example.Exceptions.ParkingLotAlreadyAssigned;

import java.util.ArrayList;

public class Attendant implements Attendable{
    private final NextLotStrategy nextLotStrategy;
    protected ArrayList<ParkingLot> parkingLots;

    public Attendant(NextLotStrategy strategy){
        this.parkingLots = new ArrayList<>();
        this.nextLotStrategy = strategy;
    }

    public Attendant(){
        this.parkingLots = new ArrayList<>();
        this.nextLotStrategy = new BasicNextLotStrategy();
    }

    public void assign(ParkingLot parkingLot) {
        if (parkingLots.contains(parkingLot)) {
            throw new ParkingLotAlreadyAssigned("Parking lot is already assigned");
        }
        parkingLots.add(parkingLot);
        System.out.println(parkingLots);
    }

    public void checkIfCarIsParked(Car car) {
        for (ParkingLot parkingLot : parkingLots) {
            parkingLot.checkIfCarIsParked(car);
        }
    }

    public Ticket park(Car car) {
        if (parkingLots.isEmpty()) {
            throw new NoParkingLotAssigned("No parking lot is assigned");
        }
        checkIfCarIsParked(car);

        ParkingLot selectedLot = nextLotStrategy.getNextLot(parkingLots);

        Ticket ticket = selectedLot.park(car);
        return ticket;
    }

    public Car unpark(Ticket ticket) {
        for (ParkingLot parkingLot : parkingLots) {
            try {
                Car car = parkingLot.unpark(ticket);
                return car;
            } catch (InvalidTicketException ignored) {
            }
        }
        throw new InvalidTicketException("Ticket is invalid");
    }
}
