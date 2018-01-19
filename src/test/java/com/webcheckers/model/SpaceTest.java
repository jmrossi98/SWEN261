package com.webcheckers.model;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Null;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;

/**
 * @author Jakob M Rossi - jmr6558@rit.edu
 *         Date: 10/30/2017.
 */
public class SpaceTest {

    private Space CuT;
    private Space other;
    private int testIdx = 4;
    private boolean testValidity = true;
    private Game game;
    private Piece piece;
    private Piece otherPiece;

    @Before
    public void testSetup() {
        CuT = new Space(testValidity,testIdx);
        other = new Space(testValidity, testIdx);

        game = mock(Game.class);
        piece = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);

        CuT.setPiece(piece);
        otherPiece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);

        other.setPiece(otherPiece);
    }

    @Test
    public void test_setPiece_inValidPiece() { CuT.setPiece(null);}

    @Test
    public void test_equals() {

        assertFalse(CuT.equals(mock(Move.class)));
        assertFalse(CuT.equals(new Space(true, 4)));
        assertFalse(CuT.equals(new Space(true, 5)));
        assertFalse(CuT.equals(new Space(false, 4)));

        CuT.setPiece(piece);
        assertFalse(CuT.equals(other));
    }

    @Test
    public void test_toString() {
        CuT.setPiece(piece);

        String wanted = "Cell Index: " + testIdx + "\n" +
                        "Piece:" + piece + "\n" +
                        "Validity: " + testValidity;

        assertEquals(CuT.toString(), wanted);

    }

    @Test
    public void test_isValid() {
        CuT.setPiece(piece);
        assertFalse(CuT.isValid());

        CuT.setPiece(null);
        assertTrue(CuT.isValid());

        CuT.setPiece(piece);
    }

    @Test
    public void test_setKing() {
        CuT.setPiece(piece);
        CuT.setKing();
    }

    @Test (expected = NullPointerException.class)
    public void test_setKingNull() {
        CuT.setPiece(null);
        CuT.setKing();
        CuT.setPiece(piece);
    }

}
