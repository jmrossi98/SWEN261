package com.webcheckers.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Null;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
/**
 * Created by jennamacbeth on 10/29/17.
 */
public class  PieceTest {

    private Piece.Type single = Piece.Type.SINGLE;
    private Piece.Type king = Piece.Type.KING;
    private Piece.Color red = Piece.Color.RED;
    private Piece.Color white = Piece.Color.WHITE;

    private Piece testPiece = new Piece(single, red);
    private Piece testPiece2 = new Piece(king, white);

    @Test
    public void test_getType() {
        final Piece.Type test = testPiece.getType();
        assertSame(single, test);
    }

    @Test
    public void test_getColor() {
        final Piece.Color test = testPiece.getColor();
        assertSame(red, test);
    }

    @Test
    public void test_getType2() {
        final Piece.Type test = testPiece2.getType();
        assertSame(king, test);
    }

    @Test
    public void test_getColor2() {
        final Piece.Color test = testPiece2.getColor();
        assertSame(white, test);
    }

    @Test
    public void test_equals() {
        assertTrue(testPiece.equals(new Piece(single, red)));
        assertFalse(testPiece.equals(new Piece(king, red)));
        assertFalse(testPiece.equals(new Piece(single, white)));
    }

    @Test
    public void test_makeKing() {
        testPiece.makeKing();
        final Piece.Type test = testPiece.getType();
        assertSame(test, king);
    }


}