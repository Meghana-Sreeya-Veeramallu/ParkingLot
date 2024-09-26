package org.example.Entities;

import org.example.Enums.CarColor;

public class Car {
    private final String registrationNumber;
    private final CarColor color;

    public Car(String registrationNumber, CarColor color) {
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

    public boolean isSameColor(CarColor color) {
        return this.color == color;
    }

    public boolean isSameCar(Car car) {
        return this.registrationNumber.equals(car.registrationNumber);
    }

    public boolean hasSameRegistrationNumber(String registrationNumber) {
        return this.registrationNumber.equals(registrationNumber);
    }
}
