package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.Objects;

/**
 * @author Cole Hornbeck - cdh6380
 *         Date: 11/15/2017.
 */
public class PostCheckTurnRoute implements Route {

    Gson gson;
    PlayerLobby playerLobby;

    public PostCheckTurnRoute(Gson gson, PlayerLobby playerLobby) {
        Objects.requireNonNull(gson, "GSON must be non-null");
        this.gson = gson;
        this.playerLobby = playerLobby;
    }

    public Object handle(Request request, Response response) {
        Session session = request.session();
        Game game = session.attribute(WebServer.GAME_KEY);
        Player thisPlayer = session.attribute("playerKey");
        Message message;

        //Send other player back to home page if either player signed out
        if (game.getOtherPlayer(thisPlayer) == null) {
            return gson.toJson(new Message("true", Message.type.info));
        }

        if (thisPlayer.equals(game.getCurrentPlayer())) {
            message = new Message("true", Message.type.info);
        } else {
            message = new Message("false", Message.type.info);
        }

        return gson.toJson(message);
    }
}
