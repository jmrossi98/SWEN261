package com.webcheckers.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by King David Lawrence on 11/8/2017.
 */
public class RowTest {

    private Row CuT;
    private Row row1;
    private int testNum = 4;
    private Game game;

    @Before
    public void testSetup() {
        CuT = new Row(testNum);
        game = mock(Game.class);
    }

    @Test
    public void getSpaces() throws Exception {
        assertNotNull(CuT.getSpaces());
    }

    @Test
    public void testCopyConstructor() {
        row1 = new Row((ArrayList<Space>) CuT.getSpaces(), 1);
        assertNotNull(row1.getSpaces());
    }

    @Test
    public void iterator() throws Exception {
        assertNotNull(CuT.iterator());
    }

    @Test
    public void getIndex() throws Exception {
        assertEquals(4, CuT.getIndex());
    }

    @Test
    public void flipRow() throws Exception {
        CuT.flipRow();
        assertEquals(3, CuT.getIndex());
    }

}