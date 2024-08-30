package com.gooroomee.chapter12;

import com.gooroomee.AppTest;
import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoField;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class Chapter12AppTest extends AppTest {

    @Test
    public void introduction() {
        Date date = new Date(117, 8, 21);
        System.out.println(date);
    }

    @Test
    public void localDateTest() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        Month month = now.getMonth();
        int dayOfMonth = now.getDayOfMonth();

        {
            // 이렇게 뽑을 수도 있다...
            int year2 = now.get((ChronoField.YEAR));
            int month2 = now.get((ChronoField.MONTH_OF_YEAR));
            int dayOfMonth2 = now.get((ChronoField.DAY_OF_MONTH));
        }


        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int lengthOfMonth = now.lengthOfMonth();
        boolean leapYear = now.isLeapYear(); // 윤년 여부
    }

    @Test
    public void localTimeTest() {
        LocalTime time = LocalTime.of(13, 45, 20);
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();

        LocalDate now = LocalDate.now();
        LocalDateTime start = now.atTime(LocalTime.MIN);
        LocalDateTime end = now.atTime(LocalTime.MAX);
        System.out.println(start);
        System.out.println(end);

        // 물론 이렇게도 만들 수 있다.
        LocalDateTime start2 = now.atStartOfDay();
        assertEquals(start, start2);
    }

    @Test
    public void instantTest() {
        // Instant : 기계 전용 유틸리티
        Instant instant = Instant.ofEpochSecond(3);
        Instant instant1 = Instant.ofEpochSecond(3, 0);
        Instant instant2 = instant.ofEpochSecond(2, 1_000_000_000);
        Instant instant3 = instant.ofEpochSecond(4, -1_000_000_000);
    }
}