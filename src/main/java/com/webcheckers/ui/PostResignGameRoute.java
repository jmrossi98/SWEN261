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
 * @author: Jakob M Rossi - jmr6558@rit.edu
 */
public class PostResignGameRoute implements Route {

    Gson gson;
    PlayerLobby playerLobby;

    public PostResignGameRoute(Gson gson, PlayerLobby playerLobby) {
        Objects.requireNonNull(gson, "GSON must be non-null");
        this.gson = gson;
        this.playerLobby = playerLobby;
    }

    public Object handle(Request request, Response response) {
        Session session = request.session();
        Game game = session.attribute(WebServer.GAME_KEY);
        Player player = session.attribute(WebServer.PLAYER_KEY);

        playerLobby.gameDone(player);
        game.removePlayer(player);
        player.gameDone();
        game.playerHasResigned();
        session.attribute(WebServer.GAME_WINNER, "You Lose.");
        session.attribute(WebServer.GAME_WIN_MODE, "You resigned. Boo.");


        Message message = new Message(player.getName() + " has left the game ", Message.type.info);
        return gson.toJson(message);
    }



}
