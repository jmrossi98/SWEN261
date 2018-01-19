package com.webcheckers.model;

/**
 * @author Cole Hornbeck - cdh6380
 *         Date: 10/4/2017.
 *
 * A class that holds information about each player.
 */
public class Player {

    private String playerName;
    private Game currentGame;
    private Piece.Color playerColor;
    private Player opponent;
    private int gamesWon;
    private int gamesPlayed;

    /**
     * Constructor
     * @param name the name of the player to create
     */
    public Player(String name) {
        this.playerName = name;
    }

    /**
     * Returns the name of the player
     * @return playerName
     */
    public String getName() {
        return playerName;
    }

    /**
     * toString override function that allows the players to be printed out
     * @return playerName
     */
    public String toString() {
        return playerName;
    }

    public Game getGame() {
        return currentGame;
    }

    public boolean isInGame() {
        return currentGame != null;
    }

    public void setGame(Game game, Piece.Color color, Player opponent) {
        if (game == null) {
            throw new NullPointerException("Game can't be null");
        }
        if (color == null) {
            throw new NullPointerException("Color can't be null");
        }
        if (opponent == null) {
            throw new NullPointerException("Opponent can't be null");
        }

        currentGame = game;
        this.playerColor = color;
        this.opponent = opponent;
    }

    public Player getOpponent() {
        return opponent;
    }

    public Piece.Color getPlayerColor() {
        return playerColor;
    }

    /**
     * Equality overrider. Bases equality on playerName
     * @param Other the other player to compare it to
     * @return True if equal names, else false
     */
    public boolean equals(Object Other) {
        if (Other instanceof Player) {
            Player other = (Player) Other;
            return playerName.equals(other.playerName);
        } else {
            return false;
        }
    }

    public void gameDone() {
        currentGame = null;
        playerColor = null;
        opponent = null;
    }

    public int getWon() { return gamesWon;    }
    public int getPlayed() { return gamesPlayed; }

    public void playerWon() {
        gamesWon++;
    }

    public void newGame() {
        gamesPlayed++;
    }

    /**
     * Hashcode function override
     * @return the hashcode of the playerName
     */
    public int hashCode() {
        return playerName.hashCode();
    }
}
