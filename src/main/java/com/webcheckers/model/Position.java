package com.webcheckers.model;

/**
 * @author Cole Hornbeck - cdh6380
 *         Date: 10/25/2017.
 *         Effectively a coordinate class to be used in the Move class
 */
public class Position {
    private int cell;      //X co-ordinate on board
    private int row;        //Y co-ordinate on board

    /**
     * Position constructor
     * @param row The row
     * @param space The space in the row
     */
    public Position(int space, int row) {
        this.cell = space;
        this.row = row;
    }

    public Position(Position other) {
        this.cell = other.getCell();
        this.row = other.getRow();
    }

    public int getRow() { return row; }
    public int getCell() { return cell; }

    public boolean outOfBounds(){
        return cell<0 || cell>7 || row<0 || row>7;
    }

    public boolean equals(Object other) {
        Position p2;
        if (other instanceof Position) {
            p2 = (Position)other;
        } else {
            return false;
        }

        return (row == p2.getRow()) && (cell == p2.getCell());
    }
}
