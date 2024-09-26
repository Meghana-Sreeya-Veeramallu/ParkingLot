package org.example.Entities;

import org.example.Enums.CarColor;
import org.example.Exceptions.CannotCreateAParkingLotException;
import org.example.Exceptions.CarAlreadyParkedException;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.ParkingLotFullException;

import java.util.ArrayList;

public class ParkingLot {/**/
    private final ArrayList<Slot> slots;

    public ParkingLot(int capacity) {
        if (capacity <= 0) {
            throw new CannotCreateAParkingLotException("Capacity should be greater than 0");
        }
        this.slots = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            slots.add(new Slot());
        }
    }

    public boolean isFull(){
        try {
            getNearestSlot();
            return false;
        }
        catch (ParkingLotFullException e) {
            return true;
        }
    }

    public void checkIfCarIsParked(Car car) {
        for (Slot slot : slots) {
            if (slot.isCarParked(car)) {
                throw new CarAlreadyParkedException("Car is already parked");
            }
        }
    }

    public Ticket park(Car car) {
        if (isFull()) {
            throw new ParkingLotFullException("Parking lot is full");
        }
        checkIfCarIsParked(car);
        Slot slot = getNearestSlot();
        Ticket ticket = slot.park(car);
        return ticket;
    }

    public Car unpark(Ticket ticket) {
        for (Slot slot : slots) {
            try {
                Car car = slot.unpark(ticket);
                return car;
            } catch (CarNotFoundException _) {
            }
        }
        throw new CarNotFoundException("Car is not found");
    }

    private Slot getNearestSlot() {
        for (Slot slot : slots) {
            if (slot.isEmpty()) {
                return slot;
            }
        }
        throw new ParkingLotFullException("Parking lot is full");
    }

    public int countCarsByColor(CarColor color) {
        int count = 0;
        for (Slot slot : slots) {
            if (slot.isCarOfColor(color)) {
                count++;
            }
        }
        return count;
    }

    public int getCarSlotNumberByRegistrationNumber(String registrationNumber) {
        for (int i = 0; i < slots.size(); i++) {
            Slot slot = slots.get(i);
            if (slot.hasSameRegistrationNumber(registrationNumber)) {
                return i + 1;
            }
        }
        throw new CarNotFoundException("Car with given registration number is not found");
    }
}

