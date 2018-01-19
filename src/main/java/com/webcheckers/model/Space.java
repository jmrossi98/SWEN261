package com.webcheckers.model;

/**
 * @author: Jakob M Rossi jmr6558@rit.edu
 */
public class Space {

    private int cellIdx;
    private Piece piece;
    private boolean validity;

    public Space(boolean validity, int index) {
        this.validity = validity;
        this.cellIdx = index;
    }

    public Space(Space space) {
        this.cellIdx = space.getCellIdx();
        if (space.getPiece() == null) {
            this.piece = null;
        } else {
            this.piece = new Piece(space.getPiece());
        }
        this.validity = space.spaceValidity();
    }

    public int getCellIdx(){
        return this.cellIdx;
    }

    public boolean isValid(){
        if (piece == null) {
            return validity;
        } else {
            return false;
        }
    }

    public boolean spaceValidity() {
        return validity;
    }

    public Piece getPiece(){
        if (piece == null) {
            return null;
        }
        return piece;
    }

    public void setKing() {
        if (piece != null) {
            piece.makeKing();
        } else {
            throw new NullPointerException("Null piece set to King");
        }
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void flipSpace() {
        cellIdx = 7 - cellIdx;
    }

    public String toString() {
        return "Cell Index: " + cellIdx + "\n" +
                "Piece:" + piece + "\n" +
                "Validity: " + validity;

    }

    public boolean equals(Object other) {
        Space otherSpace;
        if (other instanceof Space) {
            otherSpace = (Space)other;
        } else { return false; }

        if (otherSpace.validity != validity) {
            return false;
        }

        if (otherSpace.cellIdx != cellIdx) {
            return false;
        }

        if ( !(piece == otherSpace.piece || piece.equals(otherSpace.piece)) ) {
            return false;
        }
        return true;
    }

}
