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
public class PostSubmitTurnRoute implements Route {

    Gson gson;
    PlayerLobby playerLobby;

    public PostSubmitTurnRoute(Gson gson, PlayerLobby playerLobby) {
        Objects.requireNonNull(gson, "GSON must be non-null");
        this.gson = gson;
        this.playerLobby = playerLobby;
    }

    public Object handle(Request request, Response response) {
        Session session = request.session();
        Game game = session.attribute(WebServer.GAME_KEY);
        Player thisPlayer = session.attribute("playerKey");

        //Send other player back to home page if either player signed out
        if (game.getOtherPlayer(thisPlayer) == null) {
            return gson.toJson(new Message("Game Over", Message.type.info));
        }

        game.switchTurn();
        Message message = new Message("Wubba Lubba Dub Dub", Message.type.info);

        return gson.toJson(message);
    }

}
