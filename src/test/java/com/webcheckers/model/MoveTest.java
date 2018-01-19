package com.webcheckers.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * @author: Jakob M Rossi - jmr6558@rit.edu
 * Date: 11/20/17
 */
public class MoveTest {

    private Move CuT;
    private Move testMove;
    private Position testStart1;
    private Position testEnd1;
    private Position testStart2;
    private Position testEnd2;

    @Before
    public void setup(){
        testStart1 = new Position(3,4);
        testEnd1 = new Position(3,2);
        CuT = new Move(testStart1,testEnd1);
        testStart2 = new Position(2,2);
        testEnd2 = new Position(2,3);
        testMove = new Move(testStart2,testEnd2);
    }

    @Test
    public void testIsJump_Valid(){
        final boolean test = CuT.isJump();
        assertTrue(test);
    }

    @Test
    public void testIsJump_inValid(){
        final boolean invalid = testMove.isJump();
        assertFalse(invalid);
    }
}

