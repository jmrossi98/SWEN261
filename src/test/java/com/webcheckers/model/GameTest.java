package com.webcheckers.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * @author Cole Hornbeck - cdh6380
 *         Date: 11/6/2017.
 */
public class GameTest {
    Player testPlayer1;
    Player testPlayer2;
    BoardView testBoard;
    Piece.Color testColor;
    Game.ViewMode testViewMode;
    Move validMove;
    Move invalidMove;
    Game CuT;

    @Before
    public void setup() {
        testPlayer1 = new Player("Name1");
        testPlayer2 = new Player("Name2");
        testBoard = new BoardView();
        testColor = Piece.Color.RED;
        testViewMode = Game.ViewMode.PLAY;
        validMove = new Move(new Position(0,5), new Position(1,4));
        invalidMove = new Move(new Position(2, 5), new Position(4,6));

        CuT = new Game(testPlayer1, testPlayer2, testBoard, testPlayer1);
    }

    @Test
    public void getWhitePlayer_Test() {
        assertSame(testPlayer2, CuT.getWhitePlayer());
    }

    @Test
    public void getTurn_test() {
        assertTrue(CuT.getTurn() >= 0);
    }

    @Test (expected = NullPointerException.class)
    public void processMoveNull_test() {
        CuT.processMove(null);
    }

    @Test
    public void processMoveValid_test() {
        Message got;
        got = CuT.processMove(validMove);
        assertTrue(got.getType() == Message.type.info);

    }

    @Test
    public void processMoveInvalid_test() {
        Message got;
        got = CuT.processMove(invalidMove);
        assertTrue(got.getType() == Message.type.error);
    }

    @Test
    public void processMoveJump_test() {
        Move move1 = new Move(new Position(0,5), new Position(1,4));
        Move jumpMove = new Move(new Position(0,5), new Position(2,3));
        Move move2 = new Move(new Position(2,3), new Position(3,2));
        Move jumpMove2 = new Move(new Position(2,3), new Position(4,1));

        CuT.resetBoard();
        CuT.getGameBoard().setPosition(new Position(1,4), new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        assertTrue(CuT.processMove(move1).getType() == Message.type.error);
        assertTrue(jumpMove.isJump());
        assertTrue(CuT.processMove(jumpMove).getType() == Message.type.info);
        assertTrue(CuT.processMove(move2).getType() == Message.type.error);
        assertTrue(CuT.processMove(jumpMove2).getType() == Message.type.error);

    }

    @Test
    public void processMoveJump_King_test() {
        Move move1 = new Move(new Position(0,5), new Position(1,4));
        Move jumpMove = new Move(new Position(0,5), new Position(2,3));

        CuT.resetBoard();
        CuT.getGameBoard().setPosition(new Position(0,5), new Piece(Piece.Type.KING, Piece.Color.RED));
        CuT.getGameBoard().setPosition(new Position(1,4), new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        assertTrue(CuT.processMove(move1).getType() == Message.type.error);
        assertTrue(jumpMove.isJump());
        assertTrue(CuT.processMove(jumpMove).getType() == Message.type.info);

    }

    @Test
    public void test_getOtherPlayer() {
        Player other = CuT.getOtherPlayer(testPlayer1);
        assertEquals(testPlayer2, other);

        other = CuT.getOtherPlayer(testPlayer2);
        assertEquals(testPlayer1, other);
    }

    @Test
    public void test_removePlayer() {
        CuT.removePlayer(testPlayer1);
        CuT.removePlayer(testPlayer2);
    }

    @Test
    public void test_gameIsOver() {

        assertFalse(CuT.gameIsOver());
        CuT.removePlayer(CuT.getRedPlayer());
        assertTrue(CuT.gameIsOver());

        CuT.removePlayer(CuT.getWhitePlayer());
        assertTrue(CuT.gameIsOver());
    }

    @Test
    public void test_getWinner() {
        CuT.getWinner();
    }

    @Test
    public void test_getWinMove() {
        CuT.getWinMode();
    }



    @Test
    public void getRedPlayer_Test() {
        assertSame(testPlayer1, CuT.getRedPlayer());
    }

    @Test
    public void getBoardTest() {
        assertSame(testBoard, CuT.getGameBoard());
    }


    @Test
    public void getActiveColor() {
        //Active color is set in hardcode when the game is first created.
        //Should always be RED when game is first started.
        assertSame(testColor, CuT.getActiveColor());
    }

    @Test
    public void getViewMode() {
        //ViewMode is always play for now. May change in future.
        assertSame(testViewMode, CuT.getViewMode());
    }

    @Test
    public void getCurrentPlayer() {
        //For test player 1 is set as current player
        assertSame(testPlayer1, CuT.getCurrentPlayer());
    }

    @Test
    public void test_switchTurn() {
        //Check starting color
        assertSame(CuT.getActiveColor(), Piece.Color.RED);
        //Switch the turn
        CuT.switchTurn();
        //Check the switched turn
        assertSame(CuT.getActiveColor(), Piece.Color.WHITE);
        //Switch it back
        CuT.switchTurn();
        //Check again for certainty
        assertSame(CuT.getActiveColor(), Piece.Color.RED);
    }

}