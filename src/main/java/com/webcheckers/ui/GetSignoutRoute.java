package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetSignoutRoute implements Route {

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerlobby;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /signout} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSignoutRoute(final PlayerLobby playerlobby, final TemplateEngine templateEngine) {
        if (templateEngine == null) {
            throw new NullPointerException("Template Engine is null");
        }
        if (playerlobby == null) {
            throw new NullPointerException("Player Lobby is null");
        }
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        Objects.requireNonNull(playerlobby, "playerlobby must not be null");
        //
        this.templateEngine = templateEngine;
        this.playerlobby = playerlobby;
        //
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        if (request == null) {
            throw new NullPointerException("Request parameter is null");
        }
        if (response == null) {
            throw new NullPointerException("Response parameter is null");
        }

        Session session = request.session();

        //Fringe case for signing out with error message displaying
        if (session.attributes().contains("INVALID_OPPONENT")) {
            session.attribute("INVALID_OPPONENT", null);
        }

        Player signingOut = session.attribute("playerKey");
        if (playerlobby.playerInGame(signingOut)) {
            Game game = session.attribute(WebServer.GAME_KEY);
            game.removePlayer(signingOut);
        }
        //
        //Removes name from playerLobby list, so others can log in with that name.
        playerlobby.removePlayer(signingOut);

        //Removes the session attribute for the player, so the pages load like signed out
        session.attribute("playerKey", null);

        //Redirects to the homepage
        response.redirect(WebServer.HOME_URL);
        return null;
    }

}