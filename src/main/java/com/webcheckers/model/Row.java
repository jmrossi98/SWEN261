package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: Jakob M Rossi jmr6558@rit.edu
 */
public class Row implements Iterable{

    private final List<Space> spaces;

    private int index;

    public Row(int rowNum) {
        this.index = rowNum;
        spaces = new ArrayList<>();
        for(int count = 0; count < 8; count++) {
            Space toAdd;
            if ((rowNum + count) % 2 == 1) { //Valid Space
                toAdd = new Space(true, count);
                if (rowNum < 3) { //If space is within red starting zone
                    Piece newPiece = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
                    toAdd.setPiece(newPiece);
                } else if (rowNum > 4) { //If space is within white starting zone
                    Piece newPiece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
                    toAdd.setPiece(newPiece);
                }
            } else { //Invalid space
                toAdd = new Space(false, count);
            }
            spaces.add(toAdd);
        }
    }

    public Row(ArrayList<Space> spaceList, int idx) {
        spaces = new ArrayList<>();
        index = idx;
        for (Space space : spaceList) {
            spaces.add(new Space(space));
        }
    }

    public Row(Row row) {
        spaces = new ArrayList<>();
        List<Space> otherRow = row.getSpaces();
        index = row.getIndex();
        for (Space space : otherRow) {
            spaces.add(new Space(space));
        }

    }


    public Iterator iterator() {
        return spaces.iterator();
    }

    public int getIndex(){
        return this.index;
    }

    public List<Space> getSpaces() { return new ArrayList<Space>(spaces); }

    public void flipRow() {
        index = 7 - index;
        //Flips the index value of each space
        for (Space space : spaces) {
            space.flipSpace();
        }

        //Reverses the spaces array
        for (int i = 0; i < 4; i++) {
            Space holder = spaces.get(i);
            spaces.set(i, spaces.get(7 - i));
            spaces.set(7 - i, holder);
        }
    }
}
