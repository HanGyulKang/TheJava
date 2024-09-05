package com.gooroomee.chapter13;

import com.gooroomee.AppTest;
import com.gooroomee.chapter13.study.classes.Sizer;
import com.gooroomee.chapter13.study.classes.Triangle;
import org.junit.Test;

import static org.junit.Assert.*;

public class Chapter13AppTest extends AppTest {

    @Test
    public void defaultMethodTest() {
        Triangle triangleA = new Triangle(100);
        Triangle triangleB = new Triangle(0);

        Sizer sizerA = new Sizer(triangleA);
        Sizer sizerB = new Sizer(triangleB);
        boolean isEmptyTriangleA = sizerA.isEmpty();
        boolean isEmptyTriangleB = sizerB.isEmpty();

        assertFalse(isEmptyTriangleA);
        assertTrue(isEmptyTriangleB);

        int sizeA = sizerA.doubleIt(triangleA.getSize());
        int sizeB = sizerA.doubleIt(triangleB.getSize());
        assertEquals(sizeA, 200);
        assertEquals(sizeB, 0);

        assertEquals(200, sizerA.doubleIt(sizerA.size()));
        assertEquals(0, sizerB.doubleIt(sizerB.size()));
    }
}