package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Move;
import spark.*;

import java.util.Objects;

/**
 * @author Cole Hornbeck - cdh6380
 *         Date: 11/13/2017.
 */
public class PostValidateMoveRoute implements Route {

    Gson gson;
    PlayerLobby playerLobby;

    public PostValidateMoveRoute(Gson gson, PlayerLobby playerLobby) {
        Objects.requireNonNull(gson, "GSON must be non-null");
        this.gson = gson;
        this.playerLobby = playerLobby;
    }

    public Object handle(Request request, Response response) {
        Session session = request.session();
        Game game = session.attribute(WebServer.GAME_KEY);


        String moveMessage = request.body();
        Move move = gson.fromJson(moveMessage, Move.class);

        Message responseMessage = game.processMove(move);

        return gson.toJson(responseMessage);
    }
}
