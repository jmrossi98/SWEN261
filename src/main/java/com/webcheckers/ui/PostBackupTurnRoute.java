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
 * @author Jenna MacBeth
 *
 * This class handles the backing up of a turn during the game
 * If a player moves a piece and decides to chose to backup rather than submit,
 * the board should be put back to its previous position
 */
public class PostBackupTurnRoute implements Route {

    Gson gson;
    PlayerLobby playerLobby;

    public PostBackupTurnRoute(Gson gson, PlayerLobby playerLobby) {
        Objects.requireNonNull(gson, "GSON must be non-null");
        this.gson = gson;
        this.playerLobby = playerLobby;
    }

    public Object handle(Request request, Response response) {
        Session session = request.session();
        Game game = session.attribute(WebServer.GAME_KEY);
        Player thisPlayer = session.attribute("playerKey");

        //Call the Game method to return the board back to before the piece was moved
        game.removeMove();

        Message message = new Message("Undo Move was Successful", Message.type.info);

        return gson.toJson(message);
    }


}
