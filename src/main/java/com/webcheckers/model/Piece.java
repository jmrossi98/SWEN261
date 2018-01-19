package com.webcheckers.model;


/**
 * @author: Jakob M Rossi- jmr6558@rit.edu
 * Date: 10/12/17
 */
public class Piece {

    private Type type;
    private Color color;

    public enum Type {SINGLE, KING}

    public enum Color {WHITE, RED}

    public Piece(Type pieceType, Color pieceColor) {
        this.type = pieceType;
        this.color = pieceColor;
    }

    public Piece(Piece piece) {
        this.type = piece.getType();
        this.color = piece.getColor();
    }

    public Type getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public String toString() {
        return "\n\tColor: " + color;
    }

    public boolean equals(Object other) {
        Piece otherPiece;
        if (other instanceof Piece) {
            otherPiece = (Piece) other;
        } else { return false; }


        if (otherPiece.type != type) {
            return false;
        }

        if (otherPiece.color != color) {
            return false;
        }

        return true;
    }

    public void makeKing() {
        this.type = type.KING;
    }
}
