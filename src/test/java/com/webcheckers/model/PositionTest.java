package com.webcheckers.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Cole Hornbeck - cdh6380
 *         Date: 11/21/2017.
 */
public class PositionTest {
    Position CuT;
    Position CuT_copy_1;
    Position CuT_copy_2;
    Position outOfBoundsPosition;

    @Before
    public void setup() {
        CuT = new Position(4,4);
        CuT_copy_1 = new Position(4,4);
        CuT_copy_2 = new Position(3,3);
        outOfBoundsPosition = new Position(-1,5);
    }

    @Test
    public void test_GetRow() {
        assertSame(CuT.getRow(), 4);
    }

    @Test
    public void test_GetCell() {
        assertSame(CuT.getCell(), 4);
    }

    @Test
    public void test_OutOfBounds() {
        assertFalse(CuT.outOfBounds());
        assertTrue(outOfBoundsPosition.outOfBounds());
    }

    @Test
    public void test_Equals() {
        assertEquals(CuT, CuT_copy_1);
        assertNotEquals(CuT, CuT_copy_2);
    }

}