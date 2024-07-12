package com.gooroomee;


import org.junit.Assert;
import org.junit.Test;

public class MoimTest {

    @Test
    public void isFull() {
        Moim moim = new Moim();
        moim.maxNumberOfAttendees = 0;
        Assert.assertFalse(moim.isEnrollmentFull());

//        moim.maxNumberOfAttendees = 100;
//        moim.numberOfEnrollment = 20;
//        Assert.assertFalse(moim.isEnrollmentFull());
//
//        moim.numberOfEnrollment = 100;
//        Assert.assertTrue(moim.isEnrollmentFull());
    }
}