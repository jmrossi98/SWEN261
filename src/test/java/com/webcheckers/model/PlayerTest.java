package com.webcheckers.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * @author Cole Hornbeck - cdh6380
 *         Date: 10/27/2017.
 */
public class PlayerTest {

    private String testName1 = "John Doe";
    private String testName2 = "Jane Fawn";
    private Player CuT;
    private Player player1Copy;
    private Player player2;
    private Piece.Color testColor;

    private Game game;

    @Before
    public void testSetup() {
        CuT = new Player(testName1);
        player1Copy = new Player(testName1);
        player2 = new Player(testName2);
        game = mock(Game.class);
        testColor = Piece.Color.RED;
    }


    @Test
    public void test_getName() {
        final String name = CuT.getName();
        assertNotNull(name);
        assertSame(testName1, name);
    }

    @Test
    public void test_toString() {
        final String nameToString = CuT.toString();
        assertNotNull(nameToString);
        assertSame(testName1, nameToString);
    }

    @Test
    public void test_getGame_isInGame() {
        //Player can return a null game.
        //Must then make sure that the values returned
        //  from isInGame match whether or not the
        //  player is actually in a game.
        //
        //Tests both getGame and isInGame simultaneously
        final Game testGame = CuT.getGame();
        final boolean gotGame;
        if (testGame == null) {
            gotGame = false;
        } else {
            gotGame = true;
        }
        final boolean isInGameTest = CuT.isInGame();

        assertSame(gotGame, isInGameTest);
    }

    @Test (expected = NullPointerException.class)
    public void test_setGame_invalidGame() {
        CuT.setGame(null, testColor, player2);
    }

    @Test (expected = NullPointerException.class)
    public void test_setGame_invalidColor() {
        CuT.setGame(game, null, player2);
    }

    @Test (expected = NullPointerException.class)
    public void test_setGame_invalidOpponent() { CuT.setGame(game, testColor, null); }

    @Test
    public void test_getOpponent() {
        //Set the opponent
        //SetGame should be already tested by this point
        CuT.setGame(mock(Game.class), Piece.Color.RED, player2);
        assertSame(CuT.getOpponent(), player2);
    }

    @Test
    public void test_getPlayerColor() {
        CuT.setGame(mock(Game.class), testColor, mock(Player.class));
        assertSame(CuT.getPlayerColor(), testColor);
    }

    @Test
    public void test_isInGame() {
        CuT.gameDone();
        assertFalse(CuT.isInGame());
        //Set a game, isInGame() should return true
        CuT.setGame(game, testColor, player2);
        assertTrue(CuT.isInGame());
    }


    @Test
    public void test_equals() {
        //Test two different players.
        //Expected: False
        boolean equalsTest = CuT.equals(player2);
        assertFalse(equalsTest);

        equalsTest = CuT.equals(mock(Game.class));
        assertFalse(equalsTest);

        //Test two seperate objects of same player
        //Expected: True
        equalsTest = CuT.equals(player1Copy);
        assertTrue(equalsTest);

        //Test same object against itself
        //Expected: True
        equalsTest = CuT.equals(CuT);
        assertTrue(equalsTest);
    }

    @Test
    public void test_hashCode() {
        //Test two different players.
        //Expected: False
        boolean hashTest = CuT.hashCode() == player2.hashCode();
        assertFalse(hashTest);

        //Test two seperate objects of same player
        //Expected: True
        hashTest = CuT.hashCode() == player1Copy.hashCode();
        assertTrue(hashTest);

        //Test same object against itself
        //Expected: True
        hashTest = CuT.hashCode() == CuT.hashCode();
        assertTrue(hashTest);
    }



}