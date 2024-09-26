package org.example.Entities;

import org.junit.jupiter.api.Test;

import org.example.Enums.CarColor;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    // Tests for isSameColor() method
    @Test
    void testIsSameColorIfColorIsSame() {
        Car car = new Car("TS-1234", CarColor.RED);

        assertTrue(car.isSameColor(CarColor.RED));
    }

    @Test
    void testIsSameColorIfColorIsDifferent() {
        Car car = new Car("TS-1234", CarColor.RED);

        assertFalse(car.isSameColor(CarColor.BLUE));
    }

    // Tests for isSameCar() method
    @Test
    void testIsSameCarIfCarsAreSame() {
        Car car = new Car("TS-1234", CarColor.RED);

        assertTrue(car.isSameCar(car));
    }

    @Test
    void testIsSameCarIfCarsHaveSameAttributes() {
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1234", CarColor.RED);

        assertTrue(firstCar.isSameCar(secondCar));
    }

    @Test
    void testIsSameCarIfCarsAreDifferent() {
        Car firstCar = new Car("TS-1234", CarColor.RED);
        Car secondCar = new Car("TS-1235", CarColor.BLUE);

        assertFalse(firstCar.isSameCar(secondCar));
    }
}