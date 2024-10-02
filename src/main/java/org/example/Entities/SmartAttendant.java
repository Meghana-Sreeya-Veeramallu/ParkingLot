package org.example.Entities;

import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.NoParkingLotAssigned;
import org.example.Exceptions.ParkingLotAlreadyAssigned;
import org.example.Exceptions.ParkingLotFullException;

import java.util.ArrayList;

public class SmartAttendant implements Attendant {

    protected ArrayList<ParkingLot> parkingLots;

    public SmartAttendant(){
        this.parkingLots = new ArrayList<>();
    }

    public void assign(ParkingLot parkingLot){
        if(this.parkingLots.contains(parkingLot)){
            throw new ParkingLotAlreadyAssigned("Parking lot is already assigned");
        }
        this.parkingLots.add(parkingLot);
    }

    public void checkIfCarIsParked(Car car){
        for(ParkingLot parkingLot : parkingLots){
            parkingLot.checkIfCarIsParked(car);
        }
    }

    public Ticket park(Car car) {
        if (parkingLots.isEmpty()) {
            throw new NoParkingLotAssigned("No parking lot is assigned");
        }
        checkIfCarIsParked(car);

        ParkingLot selectedLot = null;
        int maxCapacityLeft = -1;

        for (ParkingLot parkingLot : parkingLots) {
            int availableSlots = parkingLot.countAvailableSlots();
            if (availableSlots > maxCapacityLeft) {
                maxCapacityLeft = availableSlots;
                selectedLot = parkingLot;
            }
        }

        if (selectedLot == null || selectedLot.isFull()) {
            throw new ParkingLotFullException("All parking lots are full");
        }

        Ticket ticket = selectedLot.park(car);
        return ticket;
    }

    public Car unpark(Ticket ticket){
        for(ParkingLot parkingLot : parkingLots){
            try{
                Car car = parkingLot.unpark(ticket);
                return car;
            } catch (InvalidTicketException ignored){
            }
        }
        throw new InvalidTicketException("Ticket is invalid");
    }
}