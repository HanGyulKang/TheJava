package com.gooroomee;

public class App {
    public static void main( String[] args ) {
        Moim moim = new Moim();
        moim.maxNumberOfAttendees = 100;
        moim.numberOfEnrollment = 100;
        System.out.println(moim.isEnrollmentFull());
    }
}
