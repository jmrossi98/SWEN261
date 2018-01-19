package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author Cole Hornbeck - cdh6380
 *         Date: 10/13/2017.
 *
 *         This class handles the selection of opponents.
 *         When tje signed in player clicks on oen of the buttons that
 *         represent the other players, the game posts the clicked name to the model
 *         and redirects to a game view with the player clicked.
 */
public class PostGamestartRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    TemplateEngine templateEngine;
    PlayerLobby playerLobby;
    static final String OPPONENT_PARAM = "chosenOpponent";

    public PostGamestartRoute(TemplateEngine templateEngine, PlayerLobby playerLobby) {
        if (templateEngine == null) {
            throw new NullPointerException("Template Engine is null");
        }
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //

        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        LOG.config("PostGamestartRoute is initialized.");
    }

    /**
     * @param request
     * @param response
     * @return
     */
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player startingPlayer = session.attribute(WebServer.PLAYER_KEY);

        String chosenPlayer = request.queryParams(OPPONENT_PARAM);
        //Makes sure the chosen opponent still exists or isn't in a game yet
        if (!playerLobby.getInLobbyList().containsKey(chosenPlayer)) {
            if (playerLobby.getGameList().containsKey(chosenPlayer)) {
                session.attribute("INVALID_OPPONENT", "Chosen Opponent Already In A Game. Choose Again.");
            } else {
                session.attribute("INVALID_OPPONENT", "Chosen Opponent Signed Out. Choose Again.");
            }
            response.redirect(WebServer.HOME_URL);
            return null;
        } else if (session.attributes().contains("INVALID_OPPONENT")) {
            session.attribute("INVALID_OPPONENT", null);
        }
        //Makes sure that the signed in player hasn't already been selected as an
        //opponent. Otherwise would start a three-way chain of games
        if (playerLobby.getGameList().containsKey(startingPlayer.getName())) {
            response.redirect(WebServer.HOME_URL);
            return null;
        }
        Player opponent = playerLobby.getPlayer(chosenPlayer);
        startingPlayer.newGame();
        opponent.newGame();

        //BoardView board = new BoardView();
        BoardView board = new BoardView(true);

        Game game = new Game(startingPlayer, opponent, board, startingPlayer);
        playerLobby.putInGame(startingPlayer, opponent);

        session.attribute(WebServer.OPPONENT_KEY, opponent);
        session.attribute(WebServer.GAME_KEY, game);

        //Initialize all variables for the start of a game

        response.redirect(WebServer.GAME_URL);
        return null;
    }
}
