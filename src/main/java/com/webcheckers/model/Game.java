package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Cole Hornbeck - cdh6380
 *         Date: 10/13/2017.
 */
public class Game {
    private BoardView gameBoard;
    private Player whitePlayer;
    private Player redPlayer;
    private Player winner;
    private Piece.Color activeColor;
    private WinMode winMode;
    private ViewMode viewMode;
    private Player currentPlayer;
    private Stack<BoardView> previousBoards;
    private Stack<Move> previousMoves;
    private int numRedPiecesLeft;
    private int numWhitePiecesLeft;
    private int turn;
    private boolean hasMoveLeft;
    private boolean gameIsOver;
    private boolean hasResigned;

    public enum ViewMode { PLAY, SPECTATOR, REPLAY }
    public enum WinMode { PIECES, SIGNOUT, RESIGN, MOVES }

    /**
     * Builds the Game object. Consists of one master board that will be given to Player 1,
     *      then flipped, copied, and given to player two. Then flipped back.
     * @param playerOne Red Player
     * @param playerTwo White Player
     * @param board  the initial board
     * @param viewingPlayer the player who is currently playing.
     */
    public Game(Player playerOne, Player playerTwo, BoardView board, Player viewingPlayer) {
        this.redPlayer = playerOne;
        this.whitePlayer = playerTwo;
        previousBoards = new Stack<>();
        previousMoves = new Stack<>();
        this.turn = 0;
        hasMoveLeft = true;
        gameIsOver = false;
        hasResigned = false;

        gameBoard = board;

        this.numRedPiecesLeft = 12;
        this.numWhitePiecesLeft = 12;

        this.activeColor = Piece.Color.RED;
        this.viewMode = ViewMode.PLAY;
        this.currentPlayer = viewingPlayer;

        whitePlayer.setGame(this, Piece.Color.WHITE, redPlayer);
        redPlayer.setGame(this, Piece.Color.RED, whitePlayer);

    }

    public void playerHasResigned() { hasResigned = true; }

    public void removeMove(){
        BoardView oldBoard = previousBoards.pop();
        gameBoard = new BoardView(oldBoard);
        previousMoves.pop();
    }

    public void removePlayer(Player player) {
        if (player.equals(whitePlayer)) {
            whitePlayer = null;
        } else if (player.equals(redPlayer)) {
            redPlayer = null;
        }
    }

    public Player getOtherPlayer(Player player) {
        if (player.equals(redPlayer)) {
            return whitePlayer;
        } else {
            return redPlayer;
        }
    }

    public Player getWinner() {
        return winner;
    }

    public WinMode getWinMode() {
        return winMode;
    }

    /**
     * Function to check if the game is over
     * Game is over if
     * - either player has zero pieces left
     * - either player can make no more moves
     * - either player signs out
     * - either player resigns the game
     *
     * @return true if game over, else false
     */
    public boolean gameIsOver() {

        //If either player has zero pieces remaining.
        if (numRedPiecesLeft == 0 || numWhitePiecesLeft == 0) {
            if (numWhitePiecesLeft == 0) {
                winner = redPlayer;
            } else {
                winner = whitePlayer;
            }
            winMode = WinMode.PIECES;
            return true;
        }

        //Checks if active color can make any moves
        if (!(gameBoard.colorHasMoves(activeColor))) {
            if (activeColor == Piece.Color.RED) {
                winner = whitePlayer;
            } else {
                winner = redPlayer;
            }
            winMode = WinMode.MOVES;
            return true;
        }

        //If either player signed out or resigned.
        if(whitePlayer == null || redPlayer == null) {
            if(whitePlayer == null) {
                winner = redPlayer;
            } else {
                winner = whitePlayer;
            }
            winMode = WinMode.RESIGN;
            return true;
        }

        return false;

    }

    public int getTurn() { return turn; }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public BoardView getGameBoard() {
        return gameBoard;
    }

    public Piece.Color getActiveColor() {
        return activeColor;
    }

    public ViewMode getViewMode() {
        return viewMode;
    }

    public Player getCurrentPlayer() { return currentPlayer; }

    private void updatePiecesLeft() {
        numRedPiecesLeft = gameBoard.updateRedPiecesLeft();
        numWhitePiecesLeft = gameBoard.updateWhitePiecesLeft();
    }

    /**
     * The function that process a players move and changes the gameBoard
     * If the move was submitted by white player, flip the board, make the move, flip the board back.
     * Calls validateMove
     * @param move The Move to change on the gameBoard
     */
    public Message processMove(Move move) {
        if (move == null) { throw new NullPointerException("Input move is null"); }

        if (currentPlayer.equals(whitePlayer)) {
            gameBoard.flipBoard();
        } //If it's the whites players turn, flip the board to process the move, then flip back

        //Turns the iterator board to a list to process the move and update the list
        gameBoard.IterableToList();


        Message validMove = validateMove(move);
        //If the move was invalid, return the message without changing the board at all.
        if (validMove.getType() == Message.type.error) {
            if (currentPlayer.equals(whitePlayer)) {
                gameBoard.flipBoard(); //Flip board back
            }
            return validMove;
        } //After this point the move is considered valid.

        //Push copy of board to stack to save turn before changing board
        if(currentPlayer.equals(whitePlayer)) {
            gameBoard.flipBoard();
            previousBoards.push(new BoardView(gameBoard));
            gameBoard.flipBoard();
        } else {
            previousBoards.push(new BoardView(gameBoard));
        }
        //Push copy of the processed Move
        previousMoves.push(new Move(move));

        if (move.isJump()) { //If move is jump, average columns and rows to find jumped position
                             //and set to null piece
            int jumpedCol = (move.getStart().getCell() + move.getEnd().getCell()) / 2;
            int jumpedRow = (move.getStart().getRow() + move.getEnd().getRow()) / 2;
            Position jumpedPos = new Position(jumpedCol, jumpedRow);
            gameBoard.setPosition(jumpedPos, null);
        }

        //Check if piece needs to be converted to King piece and do so
        //If end row is 0, change piece to king
        if (move.getEnd().getRow() == 0) {
            gameBoard.setToKing(move.getStart());
        }
        //Grabs piece that's being moved from Start position
        Piece movingPiece = gameBoard.getPieceAt(move.getStart());
        //Sets start piece to null
        gameBoard.setPosition(move.getStart(), null);
        //Sets end piece to grabbed piece
        gameBoard.setPosition(move.getEnd(), movingPiece);
        //Transfers the ArrayList board back onto the Iterable board for full transfer
        gameBoard.ListToIterable();

        if (currentPlayer.equals(whitePlayer)) {
            gameBoard.flipBoard();
        } //If it's the whites players turn, flip the board back to the correct orientation for them

        //Update the number of each piece left on the board.
        updatePiecesLeft();
        //Check if game is over, store in boolean form.
        gameIsOver = gameIsOver();
        //Return the validMove Message from validateMove
        return validMove;

    }

    private ArrayList<Position> capturesOnBoard() {

        gameBoard.IterableToList(); //Updates the board arrayList for use.
        ArrayList<Position> captures = new ArrayList<>(); //positions were we could/should have jumped to
        for (int c = 0;  c < 8; c++) {
            for (int r = 0; r < 8; r++) {
                Position p = new Position(c, r);
                Position moveFwdLeft = new Position(c-1, r-1);
                Position jumpFwdLeft = new Position(c-2, r-2);
                Position moveFwdRight = new Position(c+1, r-1);
                Position jumpFwdRight = new Position(c+2, r-2);
                Position moveBkLeft = new Position(c-1, r+1);
                Position jumpBkLeft = new Position(c-2, r+2);
                Position moveBkRight = new Position(c+1, r+1);
                Position jumpBkRight = new Position(c+2, r+2);

                Piece pieceAt = gameBoard.getPieceAt(p);
                Piece leftCapture = moveFwdLeft.outOfBounds() ? null: gameBoard.getPieceAt(moveFwdLeft);
                Piece leftJump = jumpFwdLeft.outOfBounds() ? null: gameBoard.getPieceAt(jumpFwdLeft);
                Piece rightCapture = moveFwdRight.outOfBounds() ? null: gameBoard.getPieceAt(moveFwdRight);
                Piece rightJump = jumpFwdRight.outOfBounds() ? null: gameBoard.getPieceAt(jumpFwdRight);
                Piece bkLeftCapture = moveBkLeft.outOfBounds() ? null: gameBoard.getPieceAt(moveBkLeft);
                Piece bkLeftJump = jumpBkLeft.outOfBounds() ? null: gameBoard.getPieceAt(jumpBkLeft);
                Piece bkRightCapture = moveBkRight.outOfBounds() ? null: gameBoard.getPieceAt(moveBkRight);
                Piece bkRightJump = jumpBkRight.outOfBounds() ? null: gameBoard.getPieceAt(jumpBkRight);

                Piece.Color currentPlayerColor =  currentPlayer.getPlayerColor();
                //Piece.Color opponentColor = currentPlayer.getOpponent().getPlayerColor();
                Piece.Color opponentColor;
                if(currentPlayerColor == Piece.Color.RED) {
                    opponentColor = Piece.Color.WHITE;
                } else {
                    opponentColor = Piece.Color.RED;
                }

                if (pieceAt != null) {
                    if (pieceAt.getColor() == currentPlayerColor) { //single and king pieces can have forward captures/jumps
                        if (!jumpFwdLeft.outOfBounds() && leftCapture != null && leftCapture.getColor() == opponentColor && leftJump == null ) {
                            captures.add(jumpFwdLeft);

                        }
                        if (!jumpFwdRight.outOfBounds() && rightCapture != null && rightCapture.getColor() == opponentColor && rightJump == null) {
                            captures.add(jumpFwdRight);
                        }
                    }
                    if (pieceAt.getType() == Piece.Type.KING && pieceAt.getColor() == currentPlayerColor) {//only Kings can move backwards
                        if (!jumpBkLeft.outOfBounds() && bkLeftCapture != null && bkLeftCapture.getColor() == opponentColor && bkLeftJump == null ) {
                            captures.add(jumpBkLeft);
                        }
                        if (!jumpBkRight.outOfBounds() && bkRightCapture != null && bkRightCapture.getColor() == opponentColor && bkRightJump == null) {
                            captures.add(jumpBkRight);
                        }
                    }
                }

            }
        }
        return captures;
    }

    /** Simple reset function for testing **/
    public void resetBoard() {
        gameBoard = new BoardView();
    }

    /**
     * The function that holds all the logic for validating a Move object
     * @param move the Move given from the server.
     * @return Returns a new Message object. Message parameters are (String, Message.type)
     *          If the move was invalid, returns as (Reason why invalid, Message.type.error)
     *          If the move was valid, returns as (Valid Move, Message.type.info)
     */
    private Message validateMove(Move move) {
        Position start = move.getStart();
        Piece movingPiece = gameBoard.getPieceAt(start);
        Position end = move.getEnd();
        boolean hadPrevMove;
        Move previousMove;
        if (previousMoves.empty()) {
            hadPrevMove = false;
            previousMove = null;
        } else {
            hadPrevMove = true;
            previousMove = previousMoves.peek();
        }

        if (previousMove != null) { //If there was a previous move
            if (!previousMove.isJump()) { //If that previous move was not a jump
                return new Message("Cannot move multiple times", Message.type.error);
            }
            else if (previousMove.isJump() && !move.isJump()) { //If previous was a jump and current is not jump
                return new Message("Must continue jumping", Message.type.error);
            }
        }

        ArrayList <Position> captures = capturesOnBoard();
        if (captures.size() == 0) {//if there are no captures to make on the board
            //Checks to make sure horizontal move distance is equal to vertical move distance
            if (Math.abs(start.getCell() - end.getCell()) != Math.abs(start.getRow() - end.getRow())) {
                return new Message("Must move in straight diagonal line", Message.type.error);
            }
            if (movingPiece.getType() == Piece.Type.SINGLE) {
                //can move 1 step l or r
                //can't move more than 1 step
                if ((start.getRow() - end.getRow()) > 1){
                    return new Message("Can't move more than 1 step", Message.type.error);
                }
                //can't move backwards
                if ((start.getRow() - end.getRow()) <= -1) {
                    return new Message("Single pieces can't move backwards", Message.type.error);
                }
            }
            if (movingPiece.getType() == Piece.Type.KING) {
                //can move 1 step l or r
                //can't move more than 1 step
                if ((start.getRow() - end.getRow()) > 1){
                    return new Message("Can't move more than 1 step", Message.type.error);
                }
                //can't move more than 1 step backwards
                if ((start.getRow() - end.getRow()) <= -2) {
                    return new Message("King pieces can only move 1 step backwards", Message.type.error);
                }
            }
        }
        else if (captures.size() > 0) { //processes jumps
            boolean madeCapture = false;
            if (movingPiece.getType() == Piece.Type.SINGLE) {
                for (Position jumpTo : captures) {
                    //Added absolute value for case of king jumps backwards
                    if (Math.abs(start.getRow() - jumpTo.getRow()) == 2 && jumpTo.equals(end)) {
                        madeCapture = true;
                        break;
                    }
                }
            }
            else if (movingPiece.getType() == Piece.Type.KING) {
                for (Position jumpTo : captures) {
                    if (jumpTo.equals(end)) {
                        madeCapture = true;
                        break;
                    }
                }
            }
            if (!madeCapture) {
                return new Message("There is capture to be made", Message.type.error);
            }
        }
        return new Message("Valid Move", Message.type.info);
    }


    /**
     * A function to change the current turn to the other player.
     * Switches activeColor
     * Switches currentPlayer
     * Increments turn value
     * Resets previousBoards Stack
     *      - So that the player cannot undo moves past the start
     *          of his turn
     */
    public void switchTurn() {
        if (activeColor == Piece.Color.RED) {
            activeColor = Piece.Color.WHITE;
        } else {
            activeColor = Piece.Color.RED;
        }

        if (currentPlayer.equals(redPlayer)) {
            currentPlayer = whitePlayer;
        } else {
            currentPlayer = redPlayer;
        }

        turn++;
        previousBoards = new Stack<>();
        previousMoves = new Stack<>();
        hasMoveLeft = true;


    }





}
