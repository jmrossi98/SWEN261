package com.webcheckers.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: Jakob M Rossi- jmr6558@rit.edu
 * Date: 10/12/17
 */
public class BoardView implements Iterable{

    private List<Row> rows;
    private List<ArrayList<Space>> ArrayRows;

    /**
     * Primary constructor to build a new clean board.
     */
    public BoardView() {
        rows = new ArrayList<>();
        for (int count = 0; count < 8; count++) {
            rows.add(new Row(count));
        }

        IterableToList();
    }

    /**
     * Test constructor to build a board with a single jump left.
     * @param bool To choose constructor, means nothing.
     */
    public BoardView(boolean bool) {
        rows = new ArrayList<>();
        for (int count = 0; count < 8; count++) {
            rows.add(new Row(count));
        }

        ArrayRows = new ArrayList<>();
        IterableToList();

        int rowCount = 0;
        int spaceCount = 0;

        for(ArrayList<Space> row : ArrayRows) {
            for (Space space : row) {
                space.setPiece(null);
                if(rowCount == 6 && spaceCount == 1) {
                    space.setPiece(new Piece(Piece.Type.KING, Piece.Color.RED));
                } else if(rowCount == 7 && spaceCount == 2) {
                    space.setPiece(new Piece(Piece.Type.KING, Piece.Color.WHITE));
                } else if(rowCount == 5 && spaceCount == 2) {
                    space.setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
                } else if(rowCount == 4 && spaceCount == 3) {
                    space.setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
                }
                spaceCount++;
            }
            rowCount++;
            spaceCount = 0;
        }

        ListToIterable();

    }

    public BoardView(BoardView otherBoard) {
        rows = new ArrayList<>();
        List<Row> otherRows = otherBoard.getRows();
        for (Row row : otherRows) {
            rows.add(new Row(row));
        }

        IterableToList();

    }

    public List<Row> getRows() {
        return rows;
    }

    public Piece getPieceAt(Position position) {
        return ArrayRows.get(position.getRow()).get(position.getCell()).getPiece();
    }

    public void setPosition(Position position, Piece piece) {
        ArrayRows.get(position.getRow()).get(position.getCell()).setPiece(piece);
    }

    public void setToKing(Position position) {
        if (getPieceAt(position) == null) {
            throw new NullPointerException("Piece at position to King cannot be null");
        }
        ArrayRows.get(position.getRow()).get(position.getCell()).setKing();
    }

    public int updateRedPiecesLeft() {
        IterableToList();   //Update array version of board
        int red = 0;
        for (ArrayList<Space> row : ArrayRows) {
            for (Space space : row) {
                if (space.getPiece() != null) {
                    if (space.getPiece().getColor() == Piece.Color.RED) {
                        red++;
                    }
                }
            }
        }
        return red;
    }

    public int updateWhitePiecesLeft() {
        IterableToList();   //Update array version of board
        int white = 0;
        for (ArrayList<Space> row : ArrayRows) {
            for (Space space : row) {
                if (space.getPiece() != null) {
                    if (space.getPiece().getColor() == Piece.Color.WHITE) {
                        white++;
                    }
                }
            }
        }
        return white;
    }

    public Iterator iterator() {
        return rows.iterator();
    }

    /**
     * Function to completely flip the internal iterable boardView.
     * Will be useful for changing turns and Moves.
     */
    public void flipBoard() {
        //Flips the internal spaces in each row
        for(Row row : rows) {
            row.flipRow();
        }

        //Reverses the row
        for (int i = 0; i < 4; i++) {
            Row holder = rows.get(i);
            rows.set(i, rows.get(7 - i));
            rows.set(7 - i, holder);
        }
    }

    public List getArray() {
        return ArrayRows;
    }

    /**
     * Transforms the BoardView iterable version of the board to an ArrayList version.
     * ArrayRows contains a list of ArrayLists that contain spaces.
     * @return ArrayRows for now, can probably change to void.
     */
    public List IterableToList() {
        ArrayRows = new ArrayList<>();

        //This algorithm works, but exposes more of the iterable than I'd like.
        //Keep for now, not most important.
        for (int count = 0; count < 8; count++) {
            Row row = rows.get(count);
            ArrayList<Space> spaceList = (ArrayList)row.getSpaces();
            ArrayRows.add(spaceList);
        }
        return ArrayRows;
    }

    /**
     * Transforms the ArrayList version of hte board back into the iterable version.
     * Useful for transferring Moves back onto the board ofr display.
     * @return
     */
    public List ListToIterable() {
        rows = new ArrayList<>();
        int idx = 0;
        //Uses copy constructors to basically try and copy everything back into the iterable lists.
        for (ArrayList<Space> row : ArrayRows) {
            rows.add(new Row(row, idx));
            idx++;
        }
        return rows;
    }

    public boolean equals(Object other) {
        BoardView board2;
        if (other instanceof BoardView) {
            board2 = (BoardView) other;
        } else {
            return false;
        }

        //Loop through entire board space by space to determine equality.
        //Haven't thoroughly tested, could be wrong.
        //Need to get Move function working before can test better.
        int rowCount = 0;
        for (Row row : rows) {
            ArrayList<Space> spaces = (ArrayList<Space>) row.getSpaces();
            ArrayList<Space> otherSpaces = (ArrayList<Space>) board2.rows.get(rowCount).getSpaces();

            int spaceCount = 0;
            for (Space s : spaces) {
                Space otherSpace = otherSpaces.get(spaceCount);
                if (!s.equals(otherSpace)) {
                    return false;
                }
                spaceCount++;
            }
            rowCount++;
        }
       return true;
    }

    /**
     * Algorithm to determine if there are moves remaining for a given player color.
     * Run time will decrease as the game continues, but will only have to
     *  calculate through a maximum of 12 pieces each time it's called.
     * @param color The Player color to check.
     * @return True if there are moves left for this player, else false.
     */
    public boolean colorHasMoves(Piece.Color color) {
        IterableToList();  //Rebuild List view.
        int rowNum = 0; //Current row value
        int colNum = 0; //Current column value
        for (ArrayList<Space> row : ArrayRows) {
            for (Space space : row) {
                if (space.getPiece() != null) {
                    if (space.getPiece().getColor() == color) {
                        //If the piece at each position is the same as the color of the current player
                        //Check all possible diagonal moves to see if can make move.
                        Piece piece = space.getPiece();
                        ArrayList<Position> possibleMoves = new ArrayList<>();

                        //Build all possible moves
                        Position moveFrontLeft = new Position( colNum - 1,rowNum - 1);
                        Position moveFrontRight = new Position( colNum + 1,rowNum - 1);
                        Position moveBackLeft = new Position( colNum - 1,rowNum + 1);
                        Position moveBackRight = new Position( colNum + 1, rowNum + 1);

                        Position jumpFrontLeft = new Position( colNum - 2, rowNum - 2);
                        Position jumpFrontRight = new Position( colNum + 2, rowNum - 2);
                        Position jumpBackLeft = new Position( colNum - 2, rowNum + 2);
                        Position jumpBackRight = new Position( colNum + 2, rowNum + 2);

                        //Add to a list, moves first then jumps
                        possibleMoves.add(moveFrontLeft);
                        possibleMoves.add(moveFrontRight);
                        if (piece.getType() == Piece.Type.KING) {
                            possibleMoves.add(moveBackLeft);
                            possibleMoves.add(moveBackRight);

                        }
                        possibleMoves.add(jumpFrontRight);
                        possibleMoves.add(jumpFrontLeft);
                        if(piece.getType() == Piece.Type.KING) {
                            possibleMoves.add(jumpBackLeft);
                            possibleMoves.add(jumpBackRight);
                        }

                        IterableToList();
                        if (possibleMoves.size() != 0) {
                            ArrayList<Double> num = new ArrayList<>();
                            //Check moves for out of bounds positions
                            for(int count = 0; count < possibleMoves.size(); count++) {
                                if (possibleMoves.get(count).outOfBounds()) {
                                    possibleMoves.remove(count);
                                    count = -1;
                                } else if (getPieceAt(possibleMoves.get(count)) != null) {
                                    possibleMoves.remove(count);
                                    count = -1;
                                }
                            }
                            //At this point only valid positions to move to will be left in list.
                            //Will be ordered regular moves first then jumps, by way of adding them.
                            //Therefore if the checking makes it to a jump, and there is no piece
                            //  at the jump position, the jump is valid, because the move between
                            //  has already been registered as invalid; containing a piece.

                            //If there is no piece at the position, then there are moves left on the board.
                            for (Position pos : possibleMoves) {
                                if (getPieceAt(pos) == null) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                colNum++;
            }
            rowNum++;
            colNum = 0;
        }

        //If the program ran through the whole board and didnt find any piece that was true, false.
        return false;
    }
}
