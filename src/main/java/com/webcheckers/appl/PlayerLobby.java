package com.webcheckers.appl;

import com.webcheckers.model.Player;
import freemarker.ext.beans.HashAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cole Hornbeck - cdh6380
 *         Date: 10/2/2017.
 *
 *         Holds the information about all the players currently signed in.
 *         Can add or remove players, and can send out the list of players.
 */
public class PlayerLobby {
    //Initialize a Logger
    // private static final Logger LOG = Logger.getLogger()

    //The list of all players in the server that have signed in
    final Map<String, Player> inLobby = new HashMap<>();
    final Map<String, Player> inGame = new HashMap<>();
    final Map<String, Player> playerList = new HashMap<>();
    final Map<Player, Integer> savedPlayers = new HashMap<>();

    /**
     * Adds a player to inLobby
     * @param newName the String name of the player to add
     * @return A player Object that was added. Not used.
     */
    public Player addPlayer(String newName) {
        if (newName==null){
            throw new NullPointerException("Player name can't be null");
        }
        Player toAdd = new Player(newName);
        inLobby.put(newName, toAdd);
        playerList.put(newName, toAdd);
        return toAdd;
    }

    public void addPlayer(Player player) {
        inLobby.put(player.getName(), player);
        playerList.put(player.getName(), player);
    }

    /**
     * Removes a player from inLobby
     * @param playerToRemove the player to remove from the list
     * @return true if successful, else false. Return value not used though.
     */
    public boolean removePlayer(Player playerToRemove) {
        if (playerToRemove==null){
            throw new NullPointerException("Player can't be null");
        }
        playerList.remove(playerToRemove.getName(), playerToRemove);
        if (inGame.containsKey(playerToRemove.getName())) {
            return inGame.remove(playerToRemove.getName(), playerToRemove);
        } else {
            return inLobby.remove(playerToRemove.getName(), playerToRemove);
        }
    }

    /**
     * Checks if inLobby already contains the given player
     * @param name the Player object to check for
     * @return true if found, else false
     */
    public boolean playerAlreadySignedIn(Player name) {
        if(name == null){
            throw new NullPointerException("Player name can't be null");
        }
        return inLobby.containsKey(name.getName()) || inGame.containsKey(name.getName());
    }

    public boolean profileExists(String playerName) {
        return savedPlayers.containsKey(new Player(playerName));
    }

    public void newProfile(Player playerName, String password) {
        savedPlayers.put(playerName, password.hashCode());
        inLobby.put(playerName.getName(), playerName);
        playerList.put(playerName.getName(), playerName);
    }

    /**
     * Returns if the given password is correct for the given player
     * @param player The player to check the password of
     * @param password The password to check against
     * @return True if passwords match, else false
     */
    public boolean passwordMatch(Player player, String password) {
        return savedPlayers.get(player) == password.hashCode();
    }

    /**
     * Returns the list of players
     * @return inLobby
     */
    public Map<String, Player> getInLobbyList() {
        return inLobby;
    }

    /**
     * Returns the list of players in the game
     * @return inGame
     */
    public Map<String, Player> getGameList() { return inGame; }

    public Map<String, Player> getPlayerList() { return playerList; }

    /**
     * Takes in two players who were in the lobby and puts them in a new list of players in a game
     * @param player1 Player in the game
     * @param player2 Player in the game
     */
    public void putInGame(Player player1, Player player2) {
        inLobby.remove(player1.getName(), player1);
        inLobby.remove(player2.getName(), player2);
        inGame.put(player1.getName(), player1);
        inGame.put(player2.getName(), player2);
    }

    public boolean playerInGame(String playerName) {
        return inGame.containsKey(playerName);
    }

    public boolean playerInGame(Player player) {
        return inGame.containsKey(player.getName());
    }

    public void gameDone(Player player) {
        player.gameDone();
        inGame.remove(player.getName(), player);
        inLobby.put(player.getName(), player);
    }

    /**
     * Takes two players who are done with a game and moves them from inGame list to inLobby
     * @param player1 Player done with game
     * @param player2 Player done with game
     */
    public void gameHasFinished(Player player1, Player player2) {
        inGame.remove(player1.getName(), player1);
        inGame.remove(player2.getName(), player2);
        inLobby.put(player1.getName(), player1);
        inLobby.put(player2.getName(), player2);
    }

    public Player getPlayer(String name) {
        return playerList.get(name);
    }

}
