package com.webcheckers.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by King David Lawrence on 10/29/2017.
 **/
public class BoardViewTest {

    private BoardView CuT;
    private BoardView singlePiece;
    private BoardView boardView2;
    private BoardView boardView3;
    private Player whitePlayer;
    private Player redPlayer;
    private Game game;
    private Position kingPosition;
    private Move move;
    private Space space;
    private Piece piece;

    @Before
    public void testSetup() {
        CuT = new BoardView();
        singlePiece = new BoardView(true);
        boardView2 = new BoardView();
        whitePlayer = new Player("King");
        redPlayer = new Player("David");
        game = new Game(whitePlayer, redPlayer, CuT, whitePlayer);
        whitePlayer.setGame(game, Piece.Color.WHITE, redPlayer );
        redPlayer.setGame(game, Piece.Color.RED, whitePlayer );
        space = new Space(true,3);
        piece = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);

        mock(Game.class);
    }

    @Test
    public void testConstructor() throws Exception {
        assertSame(CuT, CuT);
        assertNotSame(CuT, boardView2);
        assertSame(whitePlayer.getGame().getGameBoard(), redPlayer.getGame().getGameBoard());
    }

    @Test
    public void testIterator() throws Exception {
        Iterator itrRows = CuT.iterator();
        assertNotNull(itrRows);
        assertTrue(itrRows instanceof Iterator);
    }

    @Test
    public void testFlipBoard() throws Exception {
        redPlayer.getGame().getGameBoard().flipBoard();
        assertSame(whitePlayer.getGame().getGameBoard(), redPlayer.getGame().getGameBoard());
    }

    @Test (expected = NullPointerException.class)
    public void testSetToKingNull() {
        CuT.setToKing(new Position(0,0));
    }

    @Test
    public void testSetToKingValid() {
        CuT.setToKing(new Position(1,0));
    }

    @Test
    public void test_equals() {
        assertTrue(CuT.equals(boardView2));
    }
}
