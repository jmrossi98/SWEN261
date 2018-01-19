package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author: Jakob M Rossi - jmr6558@rit.edu
 * Date: 10/12/17
 */
public class GetGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    static final String VIEW_NAME = "game.ftl";
    static final String CURRENT_PLAYER = "currentPlayer";
    static final String VIEW_MODE = "viewMode";
    static final String RED_PLAYER = "redPlayer";
    static final String WHITE_PLAYER = "whitePlayer";
    static final String CURR_COLOR = "activeColor";
    static final String BOARD = "board";
    static final String MESSAGE = "message";

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    private enum gameTypes {PLAY, SPECTATOR, REPLAY}
    private enum colors {RED, WHITE}

    GetGameRoute(final TemplateEngine templateEngine, final PlayerLobby playerLobby) {
        if (templateEngine == null) {
            throw new NullPointerException("Template Engine is null");
        }
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        LOG.config("GetGameRoute is initialized.");
    }

    public String handle(Request request, Response response) {
        // retrieve the game object and start one if no game is in progress
        final Session session = request.session();
        final Map<String, Object> vm = new HashMap<>();

        GetGameRoute.gameTypes type = GetGameRoute.gameTypes.PLAY;
        GetGameRoute.colors activeColor = GetGameRoute.colors.RED;
        Game game = session.attribute(WebServer.GAME_KEY);
        Message message = new Message("Turn " + (game.getTurn() + 1), Message.type.info);

        Player thisPlayer = session.attribute("playerKey");
        Player redPlayer = game.getRedPlayer();
        BoardView boardToPut;

        //Send other player back to home page if game is over
        if (game.gameIsOver()) {
            Game.WinMode winMode = game.getWinMode();
            Player winner = game.getWinner();
            if(thisPlayer.equals(winner)) {
                winner.playerWon();
            }
            if (winMode == Game.WinMode.MOVES) {
                if(thisPlayer.equals(winner)) {
                    session.attribute(WebServer.GAME_WINNER, "You Win!");
                    session.attribute(WebServer.GAME_WIN_MODE, "Other player had no valid moves!");
                } else {
                    session.attribute(WebServer.GAME_WINNER, "You Lose.");
                    session.attribute(WebServer.GAME_WIN_MODE, "You had no valid moves.");
                }
            } else if (winMode == Game.WinMode.PIECES) {
                if(thisPlayer.equals(winner)) {
                    session.attribute(WebServer.GAME_WINNER, "You Win!");
                    session.attribute(WebServer.GAME_WIN_MODE, "Other player had no pieces left!");
                } else {
                    session.attribute(WebServer.GAME_WINNER, "You Lose.");
                    session.attribute(WebServer.GAME_WIN_MODE, "You had no pieces left.");
                }
            } else if (winMode == Game.WinMode.RESIGN){
                if(thisPlayer.equals(winner)) {
                    session.attribute(WebServer.GAME_WINNER, "You Win!");
                    session.attribute(WebServer.GAME_WIN_MODE, "Other player resigned!");
                } else {
                    session.attribute(WebServer.GAME_WINNER, "You Lose.");
                    session.attribute(WebServer.GAME_WIN_MODE, "You resigned. Boo.");
                }
            } else {
                if(thisPlayer.equals(winner)) {
                    session.attribute(WebServer.GAME_WINNER, "You Win!");
                    session.attribute(WebServer.GAME_WIN_MODE, "Other player signed out!");
                } else {
                    session.attribute(WebServer.GAME_WINNER, "You Lose.");
                    session.attribute(WebServer.GAME_WIN_MODE, "You resigned. Boo.");
                }
            }

            game.removePlayer(thisPlayer);
            playerLobby.gameDone(thisPlayer);
            session.attribute(WebServer.GAME_KEY, null);
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        //Checks which player is in this session, and adds the
        //correct board to the vm
        if (thisPlayer.equals(redPlayer)) {
            boardToPut = game.getGameBoard();
        } else {
            boardToPut = new BoardView(game.getGameBoard());
            if (boardToPut.equals(game.getGameBoard())) {
                System.out.println("Turn: " + game.getTurn() + ": Equal boards before flip");
            } else {
                System.out.println("Turn: " + game.getTurn() + ": ERROR: INNEQUAL BOARDS BEFORE FLIP");
            }

            boardToPut.flipBoard();
            System.out.println("Turn: " + game.getTurn() + ": Board was flipped for Player 2");
        }


        vm.put("currentPlayer", session.attribute("playerKey"));
        vm.put("viewMode", game.getViewMode());
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());
        vm.put("activeColor", game.getActiveColor());
        vm.put("board", boardToPut);
        vm.put("message", message);
        vm.put("title", "Game");

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }


}
