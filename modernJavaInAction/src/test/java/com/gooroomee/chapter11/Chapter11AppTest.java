package com.gooroomee.chapter11;

import com.gooroomee.AppTest;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class Chapter11AppTest extends AppTest {

    @Test
    public void nullPointExceptionTest() {
        Laboratory.Person person = new Laboratory.Person();
        assertThrows(NullPointerException.class, () -> person.getCar().getInsurance());
    }

    @Test
    public void howToHandleNullPointerExceptionTest() {
        // 굉장히 보수적인 방법
        Laboratory.Person person = new Laboratory.Person();
        String carInsuranceName01 = this.getCarInsuranceName01(person);
        assertEquals("Unknown", carInsuranceName01);
        String carInsuranceName02 = this.getCarInsuranceName02(person);
        assertEquals("Unknown", carInsuranceName02);


        Laboratory.Person2 person2 = new Laboratory.Person2();
        ThrowingRunnable r = () -> Optional.of(person2)
                                           // requireNonNull
                                           .flatMap(Laboratory.Person2::getCar)
                                           .flatMap(Laboratory.OptionalCar::getInsurance)
                                           .flatMap(Laboratory.OptionalInsurance::getName)
                                           .orElse("Unknown");
        assertThrows(NullPointerException.class, r);
    }

    public String getCarInsuranceName01(Laboratory.Person person) {
        if (person.getCar() != null) {
            Laboratory.Car car = person.getCar();
            if (car.getInsurance() != null) {
                Laboratory.Insurance insurance = car.getInsurance();
                if (insurance.getName() != null) {
                    return insurance.getName();
                }
            }
        }

        return "Unknown";
        // 구려
    }

    public String getCarInsuranceName02(Laboratory.Person person) {
        if (person == null) {
            return "Unknown";
        }

        Laboratory.Car car = person.getCar();
        if (car == null) {
            return "Unknown";
        }

        Laboratory.Insurance insurance = car.getInsurance();
        if (insurance == null) {
            return "Unknown";
        }
        return insurance.getName();
        // 더 구려...
    }
}