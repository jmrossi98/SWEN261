package com.webcheckers.model;

/**
 * @author Cole Hornbeck - cdh6380
 *         Date: 10/25/2017.
 */
public class Move {
    private Position start;
    private Position end;
    private boolean jump;


    /**
     * Move constructor
     * @param moveFrom The position the piece is coming from
     * @param moveTo The position the piece is moving to
     */
    public Move(Position moveFrom, Position moveTo) {
        this.start = moveFrom;
        this.end = moveTo;
    }

    public Move(Move move) {
        this.start = new Position(move.getStart());
        this.end = new Position(move.getEnd());
    }

    public boolean isJump() {
        double fromX = start.getCell();
        double fromY = start.getRow();
        double toX = end.getCell();
        double toY = end.getRow();
        //Calculate distance
        double distance = Math.sqrt(Math.pow(fromX - toX, 2) + Math.pow(fromY - toY, 2));
        if (distance >= 2) { //If distance is >= to 2, it was a jump
            jump = true;
        } else {
            jump = false;
        }
        return jump;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() { return end; }


}
