package com.webcheckers.appl;

import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * @author: Jakob M Rossi jmr6558@rit.edu
 * Date: 11/8/17
 */
public class PlayerLobbyTest {

    private PlayerLobby CuT;
    private Game game;
    private ArrayList inLobbyList;
    private ArrayList inGameList;
    private ArrayList playerList;
    private Player player1;
    private Player player2;
    private Player player3;
    private BoardView boardView;

    @Before
    public void testSetup() {
        CuT = new PlayerLobby();
        player1 = new Player("Jake");
        player2 = new Player("NotJake");
        inLobbyList = new ArrayList();
        inGameList = new ArrayList();
        CuT.newProfile(player2, "Password");

    }

    @Test
    public void test_AddPlayer_validPlayer(){
        final Player temp = CuT.addPlayer(player1.getName());
        CuT.addPlayer(player1);
        assertEquals("Jake",temp.getName());
    }

    @Test (expected = NullPointerException.class)
    public void test_RemovePlayer_inValidPlayer() { CuT.removePlayer(null);}

    @Test
    public void test_RemovePlayer_validPlayer(){
        final Player temp1 = CuT.addPlayer(player1.getName());
        final boolean temp2 = CuT.removePlayer(player1);
        assertEquals(true,temp2);
    }

    @Test
    public void test_RemovePlayer_inValidPlayer2() {
        final Player temp1 = CuT.addPlayer(player1.getName());
        final boolean temp2 = CuT.removePlayer(player2);
        assertEquals(true, temp2);
    }

    @Test (expected = NullPointerException.class)
    public void test_AddPlayer_inValidPlayer() {
        String meh = null;
        CuT.addPlayer(meh);
    }


    @Test (expected = NullPointerException.class)
    public void test_PlayerAlreadySignedIn_inValidPlayer() { CuT.removePlayer(null);}

    @Test
    public void test_PlayerAlreadySignedIn_inLobbynotGame() {
        inLobbyList.add(player1);
        inGameList.add(null);
        final boolean temp = CuT.playerAlreadySignedIn(player1);
        assertEquals(false,temp);
    }

    @Test
    public void test_PlayerAlreadySignedIn_inGameNotLobby() {
        inLobbyList.add(null);
        inGameList.add(player1);
        final boolean temp = CuT.playerAlreadySignedIn(player1);
        assertEquals(false,temp);
    }

    @Test
    public void test_GetInLobbyList_Valid() {
        inLobbyList.add(player1);
        final Map temp = CuT.getInLobbyList();
        assertSame(HashMap.class ,temp.getClass());
    }

    @Test
    public void test_putInGame() {
        CuT.putInGame(player1, player2);
    }

    @Test
    public void test_gameHasFinished() {
        CuT.gameHasFinished(player1, player2);
    }

    @Test
    public void test_getPlayer() {
        CuT.addPlayer(player1.getName());
        String playerName = player1.getName();
        assertEquals(player1, CuT.getPlayer(playerName));
    }

    @Test
    public void test_gameDone() {
        CuT.gameDone(player1);
    }

    @Test
    public void test_playerInGame() {
        assertFalse(CuT.playerInGame(player1));
        assertFalse(CuT.playerInGame(player1.getName()));
    }

    @Test
    public void test_getGameList() {
        final Map temp = CuT.getGameList();
        assertSame(HashMap.class, temp.getClass());
    }

    @Test
    public void test_getPlayerList() {
        final Map temp = CuT.getPlayerList();
        assertSame(HashMap.class, temp.getClass());
    }

    @Test
    public void test_profileExists() {
        assertFalse(CuT.profileExists(player1.getName()));
    }

    @Test
    public void test_newProfile() {
        CuT.newProfile(player1, "Password");
    }

    @Test
    public void test_passwordMatch() {
        CuT.passwordMatch(player2, "Password");
    }


}
